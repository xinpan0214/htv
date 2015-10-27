package de.arp.htv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.arp.htv.model.Channel;
import de.arp.htv.repo.ChannelRepo;

@SpringBootApplication
public class HtvApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(HtvApplication.class);
	
    public static void main(String[] args) {
        SpringApplication.run(HtvApplication.class, args);
    }

    @Autowired
    private ChannelRepo channelRepo;
    
	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... arg0) throws Exception {
		log.info("Initializing channel database");
		channelRepo.save(new Channel("daserste").setDisplayName("Das Erste"));
		channelRepo.save(new Channel("zdf").setDisplayName("ZDF"));
		channelRepo.save(new Channel("arte").setDisplayName("ARTE"));
	}
    
    
}
