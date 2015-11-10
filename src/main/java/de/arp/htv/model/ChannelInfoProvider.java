/**
 * 
 */
package de.arp.htv.model;

/**
 * This interface must be implemented by classes that provide access
 * to icon URL's for a given channel
 * @author arp
 *
 */
public interface ChannelInfoProvider {

	/**
	 * Clear any caches used by this provider
	 */
	public void clearCache();
	
	/**
	 * Return the Channel info for the given Channel
	 * @param channelId		the ID of the channel to locate
	 * @return a ChannelInfo 
	 */
	public ChannelInfo getChannelInfo(String channelId);
}
