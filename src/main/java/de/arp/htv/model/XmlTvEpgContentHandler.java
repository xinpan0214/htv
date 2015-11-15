/**
 * 
 */
package de.arp.htv.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author arp
 *
 */
public class XmlTvEpgContentHandler extends DefaultHandler implements EntityResolver {
	
	public static final String EL_PROGRAM = "programme";
	public static final String EL_TITLE = "title";
	public static final String EL_SUBTITLE = "sub-title";
	public static final String EL_DESCRIPTION = "desc";
	
	public static final String AT_CHANNEL = "channel";
	public static final String AT_START = "start";
	public static final String AT_END = "stop";
	
	protected static Calendar toCalendar(String s) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(s.substring(0,4)));
		cal.set(Calendar.MONTH, Integer.parseInt(s.substring(4,6)) - 1);
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s.substring(6,8)));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s.substring(8,10)));
		cal.set(Calendar.MINUTE, Integer.parseInt(s.substring(10,12)));
		return cal;
	}

	private Program program = null;
	private StringBuffer buffer = null;
	
	private Map<String, ChannelInfo> channelMap;
	
	public XmlTvEpgContentHandler(Map<String,ChannelInfo> channelMap) {
		this.channelMap = channelMap;
	}
	
	protected boolean processChannel(String channel) {
		return ( channelMap != null ) && channelMap.keySet().contains(channel);
	}
	
	protected void addProgram(Program program) {
		ChannelInfo info = channelMap.get(program.getChannelId());
		if (info != null) {
			info.addProgram(program);
		}
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (EL_PROGRAM.equals(qName)) {
			String channel = attributes.getValue(AT_CHANNEL);
			if (processChannel(channel)) {
				program = new Program();
				program.setChannelId(channel);
				String atStart = attributes.getValue(AT_START);
				String atEnd = attributes.getValue(AT_END);
				if (atStart != null) {
					program.setStartDate(toCalendar(atStart));
				}
				if (atEnd != null) {
					program.setEndDate(toCalendar(atEnd));
				}
			}
		} else if (EL_TITLE.equals(qName)) {
			buffer = new StringBuffer();
		} else if (EL_SUBTITLE.equals(qName)) {
			
		} else if (EL_DESCRIPTION.equals(qName)) {
			
			
		}
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)	throws SAXException {
		if (EL_PROGRAM.equals(qName)) {
			if (program != null) {
				addProgram(program);
				program = null;
			}
		} else if (EL_TITLE.equals(qName)) {
			if (program != null) {
				program.setTitle(buffer.toString());
			}
			buffer = null;
		}
		super.endElement(uri, localName, qName);
	}

	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (buffer != null) {
			buffer.append(new String(ch, start, length));
		}
		super.characters(ch, start, length);
	}

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
		if (systemId.endsWith("xmltv.dtd")) {
			InputSource src = new InputSource(getClass().getResourceAsStream("/static/xmltv.dtd"));
			src.setSystemId("classpath:/static/xmltv.dtd");
			return src;
		}
		return super.resolveEntity(publicId, systemId);
	}

	
}
