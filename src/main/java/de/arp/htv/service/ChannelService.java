/**
 * 
 */
package de.arp.htv.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.arp.htv.model.Channel;
import de.arp.htv.model.XmlTvContentHandler;
import de.arp.htv.repo.ChannelRepo;

/**
 * @author arp
 *
 */
@Service
public class ChannelService {

	private static final String CHANNEL_URL = "http://xmltv.xmltv.se/channels-Germany.xml.gz";
	
	private static final Logger log = LoggerFactory.getLogger(ChannelService.class);
	
	@Autowired
	private ChannelRepo channelRepo;
	
	private static XMLReader getXMLReader() throws ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		return reader;
	}
	
	public void initializeDefaults() {
		log.info("Initializing channel database");
		channelRepo.save(new Channel("hd.daserste.de").setDisplayName("Das Erste HD").setVlcUrl("239.35.10.1:100000"));
		channelRepo.save(new Channel("daserste.de").setDisplayName("Das Erste").setVlcUrl("239.35.10.4:100000"));
		channelRepo.save(new Channel("hd.zdf.de").setDisplayName("ZDF HD").setVlcUrl("239.35.10.2:10000"));
		channelRepo.save(new Channel("sat1.de").setDisplayName("SAT.1").setVlcUrl("239.35.20.20:10000"));
		channelRepo.save(new Channel("gold.sat1.de").setDisplayName("SAT.1 Gold").setVlcUrl("239.35.20.24:10000"));
		channelRepo.save(new Channel("rtl.de").setDisplayName("RTL").setVlcUrl("239.35.20.10:10000"));
		channelRepo.save(new Channel("rtl2.de").setDisplayName("RTL2").setVlcUrl("239.35.20.10:10000"));
		channelRepo.save(new Channel("arte.de").setDisplayName("ARTE HD").setVlcUrl("239.35.10.3:10000"));
	}
	
	public Iterable<Channel> findAllChannels() {
		return channelRepo.findAll();
	}
	
	public String getEpg(Channel channel) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://xmltv.xmltv.se/3sat.de_2015-10-27.xml.gz");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			HttpEntity ent = response.getEntity();
			if (ent != null) {
				InputStream in = ent.getContent();
				IOUtils.copy(in, System.out);
				in.close();
			}
		} catch (Exception ex) {
			return "failure";
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return "success";
	}
	
	public void loadIcons() throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();	
		HttpGet httpGet = new HttpGet(CHANNEL_URL);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpGet);
			XMLReader reader = getXMLReader();
			XmlTvContentHandler handler = new XmlTvContentHandler(channelRepo);
			reader.setContentHandler(handler);
			log.info("Parsing XmlTV channel info");
			reader.parse(new InputSource(response.getEntity().getContent()));
		} catch (ClientProtocolException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} finally {
			if (response != null) response.close();
		}
		
	}
	
}
