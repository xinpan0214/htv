/**
 * 
 */
package de.arp.htv.service;

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
}
