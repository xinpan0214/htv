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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	private Calendar startDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	private Calendar endDate;
	@Column(name="title")
	private String title;
	@Column(name="description")
	private String description;
}
