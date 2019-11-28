package com.queue.demo.util;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class PaseXml {

	public static Map<String, String> getXmlValue(String xml, List<String> key)
			throws ParserConfigurationException, SAXException, IOException {
		PaseXmlHandler paseXmlHandler = new PaseXmlHandler(key);
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		InputStream is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		parser.parse(is, paseXmlHandler);
		return paseXmlHandler.getXmlValue();
	}
}
