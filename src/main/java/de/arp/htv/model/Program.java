/**
 * 
 */
package de.arp.htv.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author arp
 *
 */
@Entity
public class Program {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name="channelid")
	private String channelId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Calendar startDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	private Calendar endDate;
	@Column(name="title")
	private String title;
	@Column(name="subtitle")
	private String subTitle;
	@Column(name="description")
	private String description;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return String
				.format("Program [id=%s, channelId=%s, startDate=%s, endDate=%s, title=%s, subTitle=%s, description=%s]",
						id, channelId, startDate, endDate, title, subTitle,
						description);
	}
	
	
}
