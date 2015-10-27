package de.arp.htv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.arp.htv.service.ChannelService;

@SpringBootApplication
public class HtvApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(HtvApplication.class);
	
    public static void main(String[] args) {
        SpringApplication.run(HtvApplication.class, args);
    }

    @Autowired
    private ChannelService channelService;
    
	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... arg0) throws Exception {
		log.info("Initializing application");
		channelService.initializeDefaults();
	}
    
    
}
