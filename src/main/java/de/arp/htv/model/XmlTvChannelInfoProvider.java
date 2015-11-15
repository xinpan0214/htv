/**
 * 
 */
package de.arp.htv.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import de.arp.htv.service.XMLService;
import de.arp.htv.service.repo.DataLocation;
import de.arp.htv.service.repo.DataRepositoryService;

/**
 * This class implements an icon provider that fetches information from
 * the xmltv.xmltv.se website
 * @author arp
 *
 */
@Component("xmltv")
public class XmlTvChannelInfoProvider implements ChannelInfoProvider {

	private static final Logger logger = LoggerFactory.getLogger(XmlTvChannelInfoProvider.class);
	
	private static final String CHANNEL_URL = "http://xmltv.xmltv.se/channels-Germany.xml.gz";
	private static final String EPG_URL = "http://combined.xmltv.se/germany.xml";
	private static final String CHANNEL_FILE = "channels.xml";
	private static final String EPG_FILE = "epg.xml";
	
	@Autowired
	private DataRepositoryService dataRepo;
	@Autowired
	private XMLService xmlService;

	private Map<String, ChannelInfo> channelMap = null;
	
	protected DataLocation getChannelLocation() {
		return dataRepo.getLocation(CHANNEL_FILE);
	}
	
	protected DataLocation getEpgLocation() {
		return dataRepo.getLocation(EPG_FILE);
	}
	
	protected DataLocation initLocation() throws IOException {
		DataLocation loc = getChannelLocation();
		if (!loc.exists()) {
			logger.info("Loading channel information from web...");
			OutputStream out = loc.getOutputStream();
			loadInfo(out);
			out.flush(); out.close();
			logger.info("done!");
		} else {
			logger.info("Location set to " + loc.getName());
		}
		DataLocation epgLoc = getEpgLocation();
		if (!epgLoc.exists()) {
			logger.info("Loading channel information from web...");
			OutputStream out = epgLoc.getOutputStream();
			loadEpg(out);
			out.flush(); out.close();
			logger.info("done!");
		} else {
			logger.info("Location set to " + epgLoc.getName());
		}
		return loc;
	}
	
	protected void loadEpg(OutputStream out) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();	
		HttpGet httpGet = new HttpGet(EPG_URL);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			logger.info("Writing channel info to cache");
			IOUtils.copy(response.getEntity().getContent(), out);
		} catch (ClientProtocolException ex) {
			ex.printStackTrace();
		} finally {
			if (response != null) response.close();
		}
	}
	
	protected void loadInfo(OutputStream out) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();	
		HttpGet httpGet = new HttpGet(CHANNEL_URL);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			logger.info("Writing channel info to cache");
			IOUtils.copy(response.getEntity().getContent(), out);
		} catch (ClientProtocolException ex) {
			ex.printStackTrace();
		} finally {
			if (response != null) response.close();
		}
	}
	
	public void init() {
		try {
			DataLocation loc = initLocation();
			XMLReader reader = xmlService.getXMLReader();
			XmlTvInfoHandler handler = new XmlTvInfoHandler();
			reader.setContentHandler(handler);			
			reader.parse(new InputSource(loc.getInputStream()));
			this.channelMap = handler.getChannelMap();
			
			XMLReader reader2 = xmlService.getXMLReader();
			XmlTvEpgContentHandler handler2 = new XmlTvEpgContentHandler(this.channelMap);
			reader2.setContentHandler(handler2);		
			reader2.setEntityResolver(handler2);
			reader2.parse(new InputSource(getEpgLocation().getInputStream()));
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			this.channelMap = new HashMap<String, ChannelInfo>();
		}
	}
	
	/* (non-Javadoc)
	 * @see de.arp.htv.model.IconProvider#clearCache()
	 */
	@Override
	public void clearCache() {
		channelMap.clear();
		channelMap = null;
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.ChannelInfoProvider#getChannelInfo(java.lang.String)
	 */
	@Override
	public ChannelInfo getChannelInfo(String channelId) {
		if (channelMap == null) {
			init();
		}
		return channelMap.get(channelId);
	}

	public static class XmlTvInfoHandler extends DefaultHandler {
		private static final String EL_CHANNEL = "channel";
		private static final String EL_DISPLAYNAME = "display-name";
		private static final String EL_ICON = "icon";
		
		private Map<String, ChannelInfo> channelMap = new HashMap<String, ChannelInfo>();
		private ChannelInfo channel = null;
		private StringBuffer buffer = null;
		
		public  Map<String, ChannelInfo> getChannelMap() {
			return channelMap;
		}
		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (EL_CHANNEL.equals(qName)) {
				String id = attributes.getValue("id");
				channel = new ChannelInfo(id);
			} else if (EL_DISPLAYNAME.equals(qName)) {
				buffer = new StringBuffer();
			} else if (EL_ICON.equals(qName)) {
				String src = attributes.getValue("src");
				channel.setIconUrl(src);
			} else {
				super.startElement(uri, localName, qName, attributes);
			}
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (EL_CHANNEL.equals(qName)) {
				logger.debug("Adding channel " + channel.getId());
				channelMap.put(channel.getId(), channel);
				channel = null;
			} else if (EL_DISPLAYNAME.equals(qName)) {
				channel.setDisplayName(buffer.toString());
				buffer = null;
			} else {
				super.endElement(uri, localName, qName);
			}
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (buffer != null) {
				buffer.append(new String(ch, start, length));
			} else {
				super.characters(ch, start, length);
			}
		}
		
	}
}
