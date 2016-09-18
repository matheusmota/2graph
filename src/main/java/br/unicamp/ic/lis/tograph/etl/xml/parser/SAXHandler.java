package br.unicamp.ic.lis.tograph.etl.xml.parser;

import org.xml.sax.Attributes; 
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler {

	String content = null;

	@Override
	// Triggered when the start of tag is found.
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content = String.copyValueOf(ch, start, length).trim();
	}

	}
