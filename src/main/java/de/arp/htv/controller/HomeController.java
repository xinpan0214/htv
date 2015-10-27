/**
 * 
 */
package de.arp.htv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author arp
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "home";
	}
}
