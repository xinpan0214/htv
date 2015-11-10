/**
 * 
 */
package de.arp.htv.service.repo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface describes a location in a data repository
 * @author arp
 *
 */
public interface DataLocation {

	/**
	 * Return the name of the location
	 * @return a String
	 */
	public String getName();
	
	public DataRepositoryService getRepository();
	
	public boolean exists();
	public boolean delete();
	public void close() throws IOException;
	public InputStream getInputStream() throws IOException;
	public OutputStream getOutputStream() throws IOException;
}
