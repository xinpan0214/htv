/**
 * 
 */
package de.arp.htv.service;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author arp
 *
 */
@Service
public class XMLService {

	public XMLReader getXMLReader() throws ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		return reader;
	}

}
