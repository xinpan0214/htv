/**
 * 
 */
package de.arp.htv.service.repo;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author arp
 *
 */
@Service
public class FileRepositoryService implements DataRepositoryService {

	public static final Logger logger = LoggerFactory.getLogger(FileRepositoryService.class);
	
	@Value("${app.basedir:}")
	private String baseDir;
	
	@PostConstruct
	public void init() {
		if (this.baseDir == null || this.baseDir.length() == 0) {
			this.baseDir = System.getProperty("user.home")+"/htv";
		}
		logger.info("FileRepository is using basedir " + getBaseDir().getAbsolutePath());
		if(!getBaseDir().mkdirs()) {
			logger.warn("Failed to create directory " + getBaseDir().getAbsolutePath());
		}
	}
	
	protected File getBaseDir() {
		return new File(this.baseDir);
	}
	
	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataRepository#getLocation(java.lang.String)
	 */
	@Override
	public DataLocation getLocation(String name) {
		return new FileLocation(this, new File(getBaseDir(),name));
	}

}
