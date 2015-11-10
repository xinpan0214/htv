/**
 * 
 */
package de.arp.htv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.arp.htv.service.ChannelService;

/**
 * @author arp
 *
 */
@Controller
public class HomeController {

	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("channels",channelService.findAllChannels());
		return "home";
	}
	
	@RequestMapping("/channels")
	public String getChannels() {
		return "home";
	}
	
	@RequestMapping("/channels/{channel}")
	public String getChannel(@PathVariable String channel) {
		return "home";
	}
}
