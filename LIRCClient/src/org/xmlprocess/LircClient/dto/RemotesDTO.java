package org.xmlprocess.LircClient.dto;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlprocess.LircClient.common.CommonStatics;

import android.util.Log;

public class RemotesDTO {

	String uuid;
	String label;
	String type;
	String colNum_s;
	int colNum = 4;

	RemotesDTO parent;
	ArrayList<RemotesDTO> children;

	public RemotesDTO(Element el) {
		super();
		processEl(el);
		// this.uuid = uuid;
	}

	private void processEl(Element el) {
		children = new ArrayList<RemotesDTO>();
		// SetLocalAttribs
		// set type as Element name
		setType(el.getTagName());
		// Log.w(getClass().getSimpleName(),
		// "processEl type set to "+getType());
		// set uuid
		setUuid(el.getAttribute(CommonStatics.A_UUID));
		// set label
		String lbl = el.getAttribute(CommonStatics.A_LABEL);
		if (lbl == null || lbl.length() == 0) {
			lbl = el.getAttribute(CommonStatics.A_NAME);
		}
		setLabel(lbl);
		// set colNum if exist
		setColNum_s(el.getAttribute(CommonStatics.A_COLS));
		// Set Children - don't follow macros as you don't need to
		if (!getType().equalsIgnoreCase(CommonStatics.E_MACRO)) {
			NodeList childNodes = el.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node childN = childNodes.item(i);
				// test to make sure it's an element
				if (childN.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					// if so create new RemotesDTO
					RemotesDTO childRdto = new RemotesDTO((Element) childN);
					childRdto.setParent(this);
					// add to array
					children.add(childRdto);
				}
			}

		}

	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<RemotesDTO> getChildren() {
		return children;
	}

	public ArrayList<RemotesDTO> getChildren(Document doc) {

		// if(children == null){
		children = new ArrayList<RemotesDTO>();
		// }
		Element root = doc.getDocumentElement();
		NodeList childNodes = root.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childN = childNodes.item(i);
			// test to make sure it's an element
			if (childN.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {

			}
		}

		return children;
	}

	public void setChildren(ArrayList<RemotesDTO> children) {
		this.children = children;
	}

	public String getColNum_s() {
		return colNum_s;
	}

	public void setColNum_s(String colNum_s) {

		if (colNum_s != null && colNum_s.length() > 0) {
			try {
				int i = Integer.parseInt(colNum_s);
				setColNum(i);
			} catch (Exception ex) {
				Log.w(getClass().getSimpleName(), "Col num isNot an int " + ex);
			}
		}

		this.colNum_s = colNum_s;
	}

	public int getColNum() {
		if (colNum < 1) {
			colNum = 4;
		}
		return colNum;
	}

	public void setColNum(int colNum) {

		this.colNum = colNum;
	}

	public RemotesDTO getParent() {
		return parent;
	}

	public void setParent(RemotesDTO parent) {
		this.parent = parent;
	}

}
