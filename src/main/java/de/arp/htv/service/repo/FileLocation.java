/**
 * 
 */
package de.arp.htv.service.repo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author arp
 *
 */
public class FileLocation implements DataLocation {

	private DataRepositoryService repository;
	private File file;
	
	protected FileLocation(DataRepositoryService repository, File file) {
		this.repository = repository;
		this.file = file;
	}
	
	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#getName()
	 */
	@Override
	public String getName() {
		return file.getName();
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#getRepository()
	 */
	@Override
	public DataRepositoryService getRepository() {
		return repository;
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#exists()
	 */
	@Override
	public boolean exists() {
		return file.exists();
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#delete()
	 */
	@Override
	public boolean delete() {
		return file.delete();
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#close()
	 */
	@Override
	public void close() throws IOException {
		// nothing to do here
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return new BufferedInputStream(new FileInputStream(this.file));
	}

	/* (non-Javadoc)
	 * @see de.arp.htv.model.repo.DataLocation#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		return new BufferedOutputStream(new FileOutputStream(this.file));
	}

}
