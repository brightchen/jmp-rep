package cg.common.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XMLDocument {
	protected Document root;

	public XMLDocument() {
		initEmpty();
	}
	
	public XMLDocument(File file) throws SAXException, IOException {
		 FileInputStream is = new FileInputStream(file);
		 root = loadFromStream(is);
	}
	
	public XMLDocument(InputStream is) throws SAXException, IOException {
		root = loadFromStream(is);
	}
	
	public Document getDocument(){
		return root;
	}
	
	protected void initEmpty() {
		try {
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			root = docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected static Document loadFromStream(InputStream is) throws SAXException, IOException {
		Document doc = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setValidating(false);
		docFactory.setIgnoringComments(true);
		docFactory.setNamespaceAware(false);
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(is);
			return doc;
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void serializeToStream(OutputStream outputStream) throws IOException {
		
		// create serializer
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setOutputByteStream(outputStream);
		
		// Setup format settings
		OutputFormat outFormat = new OutputFormat();
		outFormat.setOmitXMLDeclaration(true);
		outFormat.setIndenting(false);
		//outFormat.setIndent(4);
		outFormat.setEncoding("utf-8");
		xmlSerializer.setOutputFormat(outFormat);
		
		// Serialize XML Document
		xmlSerializer.serialize(root);
	}
	
	public void serializeToFile(File file) throws IOException {
		// create file outputstream and write the document
		FileOutputStream oStream = null;
		oStream = new FileOutputStream(file);
		serializeToStream(oStream);
		oStream.close();
	}

}
