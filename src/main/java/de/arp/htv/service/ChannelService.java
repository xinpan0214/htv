/**
 * 
 */
package de.arp.htv.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.arp.htv.model.Channel;
import de.arp.htv.repo.ChannelRepo;

/**
 * @author arp
 *
 */
@Service
public class ChannelService {

	private static final Logger log = LoggerFactory.getLogger(ChannelService.class);
	
	@Autowired
	private ChannelRepo channelRepo;
	
	public void initializeDefaults() {
		log.info("Initializing channel database");
		channelRepo.save(new Channel("daserste_hd").setDisplayName("Das Erste HD").setVlcUrl("239.35.10.1:100000"));
		channelRepo.save(new Channel("daserste").setDisplayName("Das Erste").setVlcUrl("239.35.10.4:100000"));
		channelRepo.save(new Channel("zdf_hd").setDisplayName("ZDF HD").setVlcUrl("239.35.10.2:10000"));
		channelRepo.save(new Channel("arte_hd").setDisplayName("ARTE HD").setVlcUrl("239.35.10.3:10000"));
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
}
