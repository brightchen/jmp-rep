package cg.common.util.xml;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class MapSerializer extends FileXMLSerializer {

	public MapSerializer(File file) throws IOException {
		super(file);
	}
	
	public void writeMapEntry(String id, Rectangle2D rect, String type,
			int pageIndex, String title) throws SAXException {
		// set up attributes
		AttributesImpl attr = baseAttributes(rect, type, title);
		attr.addAttribute("", "", "refId", "IDREF", id);
		attr.addAttribute("", "", "chunk", "NMTOKEN", Integer.toString(pageIndex));
		
		// write the area element
		startElement("", "", "area", attr);
		endElement("", "", "area");
	}
	
	public void writeAdMapEntry(Rectangle2D rect, String imageName, String sms, String url,
			String phone, String email, String title) throws SAXException {
		// set up attributes
		AttributesImpl attr = baseAttributes(rect, "ad", title);
		if (imageName != null && imageName.length() > 0)
			attr.addAttribute("", "", "imageName", "CDATA", imageName);
		if (sms != null && sms.length() > 0)
			attr.addAttribute("", "", "sms", "CDATA", sms);
		if (url != null && url.length() > 0)
			attr.addAttribute("", "", "url", "CDATA", url);
		if (phone != null && phone.length() > 0)
			attr.addAttribute("", "", "phone", "CDATA", phone);
		if (email != null && email.length() > 0)
			attr.addAttribute("", "", "email", "CDATA", email);

		// write the area element
		startElement("", "", "area", attr);
		endElement("", "", "area");
	}
	
	AttributesImpl baseAttributes(Rectangle2D rect, String type, String title) {
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "type", "CDATA", type);
		attr.addAttribute("", "", "title", "CDATA", title);
		// format rectangle and set attribute
		StringBuilder coordString = new StringBuilder();
		coordString.append(clipNegativeMapValue(floorAreaCoordinate(rect.getMinX())));
		coordString.append(",");
		coordString.append(clipNegativeMapValue(floorAreaCoordinate(rect.getMinY())));
		coordString.append(",");
		coordString.append(clipBiggerThanOneMapValue(ceilAreaCoordinate(rect.getMaxX())));
		coordString.append(",");
		coordString.append(clipBiggerThanOneMapValue(ceilAreaCoordinate(rect.getMaxY())));
		attr.addAttribute("", "", "coords", "CDATA", coordString.toString());
		return attr;
	}

	public void initMap(int pageIndex) throws SAXException {
		startDocument();
		startElement("", "", "pageMap", null);
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "index", "NMTOKEN", Integer.toString(pageIndex));
		startElement("", "", "page", attr);

	}

	public void endMap() throws SAXException {
		endElement("", "", "page");
		endElement("", "", "pageMap");
		endDocument();
	}
	
	// clipping the boundaries of the map to show hotspot within the page
	private String clipNegativeMapValue(String value){ 
		if (value.startsWith("-"))
			return "0.0";
		else
			return value;
		
	}

	private String clipBiggerThanOneMapValue(String value){ 
		if (value.startsWith("1"))
			return "1.0";
		else
			return value;
		
	}
	
	
}
