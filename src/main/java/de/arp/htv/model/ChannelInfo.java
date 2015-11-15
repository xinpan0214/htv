/**
 * 
 */
package de.arp.htv.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.crsh.shell.impl.command.system.help;

/**
 * @author arp
 *
 */
public class ChannelInfo {

	private String id;
	private String displayName;
	private String iconUrl;
	private List<Program> programs = new ArrayList<Program>();
	
	private static final ProgramComparator CMP = new ProgramComparator();
	
	public ChannelInfo() {
		
	}
	
	public ChannelInfo(String id) {
		this(id, null, null);
	}
	
	public ChannelInfo(String id, String displayName, String url) {
		setId(id);
		setDisplayName(displayName);
		setIconUrl(url);
	}
	
	protected String printCal(Calendar cal) {
		return String.format("%s.%s.%s %s:%s", 
				cal.get(Calendar.DAY_OF_MONTH),
				cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.YEAR),
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE));
	}
	
	public List<Program> getProgramsInPeriod(Calendar start, Calendar end) {
		List<Program> ret = new ArrayList<Program>();
		for (Program program : programs) {
			int cmp1 = program.getStartDate().compareTo(start);
			int cmp2 = program.getEndDate().compareTo(end);
			if (program.getStartDate().compareTo(start) >= 0 && program.getEndDate().compareTo(end) < 0) {
				ret.add(program);
				System.out.println(String.format("%s: Start: %s compares as %s,%s against %s and %s", 
						program.getChannelId(),
						printCal(program.getStartDate()),
						cmp1,
						cmp2,
						printCal(start),
						printCal(end)
						)
						);
			} else {
			}
		}
		Collections.sort(ret, CMP);
		return ret;
	}
	
	public static class ProgramComparator implements Comparator<Program> {

		@Override
		public int compare(Program p1, Program p2) {
			return p1.getStartDate().compareTo(p2.getStartDate());
		}
		
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
	public void setId(String id) {
		this.id = id;
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
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void addProgram(Program program) {
		this.programs.add(program);
	}
	
	/**
	 * @return the programs
	 */
	public List<Program> getPrograms() {
		return programs;
	}

	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(List<Program> programs) {
		this.programs = programs;
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
		ChannelInfo other = (ChannelInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
