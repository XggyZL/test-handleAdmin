package com.queue.demo.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PaseXmlHandler extends DefaultHandler {
	
	
	public PaseXmlHandler(List<String> key) {
		super();
		this.key = key;
	}

	List<String> key;
	
	Map<String,String> xmlValue = new HashMap<String,String>();

	
 	public Map<String, String> getXmlValue() {
		return xmlValue;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 清空历史数据
		sb.setLength(0);

	}

	BufferedReader br = null;
	FileReader reader = null;

	@Override
	public void startDocument() throws SAXException {
 
		super.startDocument();
	}

	// 标签中的内容，临时变量，重新开始时需清空
	private StringBuilder sb = new StringBuilder();

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String content = sb.toString().trim();
		for (int i = 0; i < key.size(); i++) {
			if (qName.equals(key.get(i))) {
				xmlValue.put(qName, content);
			}
		}
 
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, length);
	}

	@Override
	public void endDocument() throws SAXException {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e1) {
			}
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e1) {
			}
		}
		super.fatalError(e);
	}

}
