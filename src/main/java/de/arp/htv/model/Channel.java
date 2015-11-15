/**
 * 
 */
package de.arp.htv.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author arp
 *
 */
@Entity
public class Channel {

	@Id
	private String id;
	@Column
	private String displayName;
	@Column
	private String iconUrl;
	@Column
	private String channelUrl;
	@Column
	private String vlcUrl;

	
	public Channel() {
		
	}
	
	public Channel(String id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public Channel setId(String id) {
		this.id = id;
		return this;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public Channel setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}
	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}
	/**
	 * @param iconUrl the iconUrl to set
	 */
	public Channel setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
		return this;
	}
	/**
	 * @return the channelUrl
	 */
	public String getChannelUrl() {
		return channelUrl;
	}
	/**
	 * @param channelUrl the channelUrl to set
	 */
	public Channel setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
		return this;
	}
	/**
	 * @return the vlcUrl
	 */
	public String getVlcUrl() {
		return vlcUrl;
	}
	/**
	 * @param vlcUrl the vlcUrl to set
	 */
	public Channel setVlcUrl(String vlcUrl) {
		this.vlcUrl = vlcUrl;
		return this;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
