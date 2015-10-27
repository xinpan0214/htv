/**
 * 
 */
package de.arp.htv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.arp.htv.model.Channel;
import de.arp.htv.service.ChannelService;

/**
 * @author arp
 *
 */
@RestController
@RequestMapping("/api/channels")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/init")
	public String init() {
		channelService.initializeDefaults();
		return "ok";
	}
	
	@RequestMapping("/")
	public Iterable<Channel> findChannels() {
		return channelService.findAllChannels();
	}
}
