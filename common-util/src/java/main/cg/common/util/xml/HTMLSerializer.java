package cg.common.util.xml;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class HTMLSerializer extends FileXMLSerializer {
	private int contentLength;

	public HTMLSerializer(File file) throws IOException {
		super(file);
		contentLength = 0;
		// TODO Auto-generated constructor stub
	}

	public void writeImageRef(String path, String id, Dimension size) throws SAXException {
		startElement("", "", "p", null);
		contentLength += 3; // length of <p>
		
		if (id != null)
			writeTarget(id);
		
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("", "", "src", "CDATA", "image?name="+path);
		contentLength += 1 + "src".length() + 1 + ("image?name="+path).length() + 2; // length of:' src="image?name=path"'
		
		if (size != null) {
			String width = Integer.toString(size.width);
			atts.addAttribute("", "", "width", "NMTOKEN", width);
			contentLength += 1 + "width".length() + 1 + width.length() + 2; // length of:' width="200"'
			String height = Integer.toString(size.height);
			atts.addAttribute("", "", "height", "NMTOKEN", height);
			contentLength += 1 + "height".length() + 1 + height.length() + 2; // length of:' height="200"'
		}
		startElement("", "", "img", atts);
		contentLength += 4; // length of <img
		endElement("", "", "img");
		contentLength += 2; // length of />
		
		endElement("", "", "p");
		contentLength += 4; // length of </p>
	}
	
	public void writeTarget(String id) throws SAXException {
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "id", "IDREF", id);
		contentLength += 1 + "id".length() + 2 + id.length(); // length of' id="id"'
		startElement("", "", "a", attr);
		contentLength += 2; // length of <a
		endElement("", "", "a");
		contentLength += 2; // length of />
	}
	
	public void writeTextLink(String text, String href) throws SAXException, IOException {
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "href", "CDATA", href);
		contentLength += 1 + "href".length() + 2 + href.length(); // length of' href="href"'
		startElement("", "", "a", attr);
		contentLength += 2; // length of <a
		characters(text.toCharArray(), 0, text.length());
		endElement("", "", "a");
		contentLength += 2; // length of />
	}

	public void writeBreak() throws SAXException {
		AttributesImpl attr = new AttributesImpl();
		startElement("", "", "br", attr);
		endElement("", "", "br");
		contentLength += 4; // length of <br/>
	}
	
	public void writeHorizontalRule() throws SAXException {
		startElement("", "", "hr", null);
		endElement("", "", "hr");
		contentLength += 4; // length of <hr/>
	}
	
	public void startCenter() throws SAXException {
		startElement("", "", "center", null);
		contentLength += "center".length() + 2; // length of <center>
	}
	
	public void endCenter() throws SAXException {
		endElement("", "", "center");
		contentLength += "center".length() + 3; // length of </center>
	}
	
	public void initHTML() throws SAXException {
		startDocument();
		startElement("", "", "html", null);
		contentLength += "html".length() + 2; // length of <html>
		startElement("", "", "body", null);
		contentLength += "body".length() + 2; // length of <body>
	}
	
	public void initHTML(String dirAttr) throws SAXException {
		AttributesImpl attr = new AttributesImpl();
		attr.addAttribute("", "", "dir", "CDATA", dirAttr);
		contentLength += "dir".length() + 4 + dirAttr.length(); // length of' dir="dirAttr"'
		
		startDocument();
		startElement("", "", "html", attr);
		contentLength += "html".length() + 2; // length of <html>
		startElement("", "", "body", null);
		contentLength += "body".length() + 2; // length of <body>
	}

	public void endHTML() throws SAXException {
		endElement("", "", "body");
		contentLength += "body".length() + 3; // length of </body>
		endElement("", "", "html");
		contentLength += "html".length() + 3; // length of </html>
		endDocument();
	}
	
	public int getContentLength(){
		return contentLength;
	}
	
	public void addContentLength(int lengthOfFragment){
		contentLength += lengthOfFragment;
	}	
}
