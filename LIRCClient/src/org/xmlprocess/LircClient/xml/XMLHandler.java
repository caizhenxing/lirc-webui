package org.xmlprocess.LircClient.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHandler {

	public Document parse(InputStream is) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return doc;
	}

	public Document parse() {
		return parse(this.getInputStream());
	}

	public void parse2() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getInputStream());
			Element root = dom.getDocumentElement();
			NodeList items = root.getElementsByTagName("BOB");
			for (int i = 0; i < items.getLength(); i++) {

				Node item = items.item(i);
				NodeList properties = item.getChildNodes();
				for (int j = 0; j < properties.getLength(); j++) {
					Node property = properties.item(j);
					String name = property.getNodeName();
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
