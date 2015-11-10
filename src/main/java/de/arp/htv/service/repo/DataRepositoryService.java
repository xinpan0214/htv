/**
 * 
 */
package de.arp.htv.service.repo;

/**
 * This interface must be implemented by classes that provide
 * directory-like input- and output-streams for storing information
 * @author arp
 *
 */
public interface DataRepositoryService {

	public DataLocation getLocation(String name);
}
