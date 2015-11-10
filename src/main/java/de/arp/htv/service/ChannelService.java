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
import de.arp.htv.model.ChannelInfo;
import de.arp.htv.model.ChannelInfoProvider;
import de.arp.htv.model.XmlTvContentHandler;
import de.arp.htv.repo.ChannelRepo;
import de.arp.htv.service.repo.DataRepositoryService;

/**
 * @author arp
 *
 */
@Service
public class ChannelService {

	private static final String CHANNEL_URL = "http://xmltv.xmltv.se/channels-Germany.xml.gz";
	
	private static final Logger log = LoggerFactory.getLogger(ChannelService.class);

	@Autowired
	private ChannelInfoProvider iconProvider; 
	@Autowired
	private DataRepositoryService dataRepo;
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
		dataRepo.getLocation("lastUpdate");
		channelRepo.save(new Channel("hd.daserste.de").setDisplayName("Das Erste HD").setVlcUrl("239.35.10.1:100000"));
		channelRepo.save(new Channel("daserste.de").setDisplayName("Das Erste").setVlcUrl("239.35.10.4:100000"));
		channelRepo.save(new Channel("hd.zdf.de").setDisplayName("ZDF HD").setVlcUrl("239.35.10.2:10000"));
		channelRepo.save(new Channel("sat1.de").setDisplayName("SAT.1").setVlcUrl("239.35.20.20:10000"));
		channelRepo.save(new Channel("gold.sat1.de").setDisplayName("SAT.1 Gold").setVlcUrl("239.35.20.24:10000"));
		channelRepo.save(new Channel("rtl.de").setDisplayName("RTL").setVlcUrl("239.35.20.10:10000"));
		channelRepo.save(new Channel("rtl2.de").setDisplayName("RTL2").setVlcUrl("239.35.20.10:10000"));

		channelRepo.save(new Channel("prosieben.de").setDisplayName("Pro 7").setVlcUrl("239.35.20.21:10000"));
		channelRepo.save(new Channel("kabel1.de").setDisplayName("Kabel 1").setVlcUrl("239.35.20.38:10000"));
		channelRepo.save(new Channel("vox.de").setDisplayName("VOX").setVlcUrl("239.35.20.11:10000"));
		channelRepo.save(new Channel("super.rtl.de").setDisplayName("Super RTL").setVlcUrl("239.35.20.39:10000"));
		channelRepo.save(new Channel("n-tv.de").setDisplayName("ntv").setVlcUrl("239.35.20.47:10000"));
		channelRepo.save(new Channel("sixx.de").setDisplayName("SIXX").setVlcUrl("239.35.20.9:10000"));
		channelRepo.save(new Channel("maxx.prosieben.de").setDisplayName("Pro7max").setVlcUrl("239.35.20.33:10000"));
		channelRepo.save(new Channel("nitro.rtl.de").setDisplayName("RTLNITRO").setVlcUrl("239.35.20.59:10000"));

//		channelRepo.save(new Channel("arte.de").setDisplayName("arte").setVlcUrl("239.35.10.20:10000"));
		channelRepo.save(new Channel("phoenix.daserste.de").setDisplayName("Phoenix").setVlcUrl("239.35.10.22:10000"));
		channelRepo.save(new Channel("3sat.de").setDisplayName("3sat").setVlcUrl("239.35.10.6:10000"));

		channelRepo.save(new Channel("kika.daserste.de").setDisplayName("Ki.Ka").setVlcUrl("239.35.10.6:10000"));
		channelRepo.save(new Channel("einsextra.daserste.de").setDisplayName("Tagesschau 24").setVlcUrl("239.35.10.25:10000"));
		channelRepo.save(new Channel("einsfestival.daserste.de").setDisplayName("Einsfestival").setVlcUrl("239.35.10.21:10000"));
		channelRepo.save(new Channel("einsplus.daserste.de").setDisplayName("EinsPlus").setVlcUrl("39.35.10.26:10000"));

		
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
		for (Channel channel : findAllChannels()) {
			ChannelInfo ch = this.iconProvider.getChannelInfo(channel.getId());
			channel.setIconUrl(ch.getIconUrl());
			channelRepo.save(channel);
		}		
	}
	
}
