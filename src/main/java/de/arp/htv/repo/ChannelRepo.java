/**
 * 
 */
package de.arp.htv.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.arp.htv.model.Channel;

/**
 * @author arp
 *
 */
public interface ChannelRepo extends PagingAndSortingRepository<Channel, String>{

}
