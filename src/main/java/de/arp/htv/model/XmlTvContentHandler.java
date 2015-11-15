/**
 * 
 */
package de.arp.htv.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.arp.htv.repo.ChannelRepo;

/**
 * @author arp
 *
 */
public class XmlTvContentHandler extends DefaultHandler {

	private static final String EL_CHANNEL = "channel";
	private static final String EL_DISPLAYNAME = "display-name";
	private static final String EL_BASEURL = "base-url";
	private static final String EL_ICON = "icon";
	
	private ChannelRepo repo = null;
	private Channel channel = null;
	private StringBuffer buffer;
	
	public XmlTvContentHandler(ChannelRepo repo) {
		this.repo = repo;
	}
	
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (EL_CHANNEL.equals(qName)) {
			String id = attributes.getValue("id");
			channel = new Channel(id);
		} else if (EL_BASEURL.equals(qName)) {
			buffer = new StringBuffer();
		} else if (EL_ICON.equals(qName)) {
			String src = attributes.getValue("src");
			channel.setIconUrl(src);
		} else {
			super.startElement(uri, localName, qName, attributes);
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (EL_CHANNEL.equals(qName)) {
			if (repo.exists(channel.getId())) {
				Channel ch = repo.findOne(channel.getId());
				ch.setChannelUrl("");
				ch.setIconUrl(channel.getIconUrl());
				repo.save(ch);
			}
			channel = null;
		} else if (EL_BASEURL.equals(qName)) {
			buffer = null;
		} else {
			super.endElement(uri, localName, qName);
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
	}

}
