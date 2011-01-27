/*
 * $Id: IDXDocument.java,v 1.5 2008/05/28 16:16:17 rudyz Exp $
 *
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.common.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class IDXDocument extends XMLDocument {
	
	private Element idxDocumentRoot = null;
	private Element tocElement = null;
	private Element tofElement = null;
	private Element totElement = null;
	private Element topElement = null;
	private Map<String,StringBuilder> sections;
	private Vector<String> pagesDirection;
	private int pageNumber;
	private int chunkOffset;
	
	public IDXDocument() {
		idxDocumentRoot = root.createElement(IDXConstants.ROOT_ELEMENT);
		tocElement = root.createElement(IDXConstants.TOC);
		tofElement = root.createElement(IDXConstants.TOF);
		totElement = root.createElement(IDXConstants.TOT);
		topElement = root.createElement(IDXConstants.TOP);
		// LinkedHashMap to preserve the order in which the sections are added
		sections = new LinkedHashMap<String,StringBuilder>();
		pagesDirection = new Vector<String>();
		pageNumber = 0;
		chunkOffset = 0;
	}
	
	public IDXDocument(InputStream is) throws IOException, SAXException {
		super(is);
		idxDocumentRoot = root.getDocumentElement();
	}
	
	public void setChunkCount(int count) {
		idxDocumentRoot.setAttribute(IDXConstants.CHUNKS, Integer.toString(count));
	}
	
	public int getChunkCount() {
		String chunkValue = idxDocumentRoot.getAttribute(IDXConstants.CHUNKS);
		return Integer.parseInt(chunkValue);
	}
	
	public int getPageCount() {
		String pageValue = idxDocumentRoot.getAttribute(IDXConstants.PAGES);
		return Integer.parseInt(pageValue);
	}
	
	public void setDocumentRoot(String documentRoot) {
		idxDocumentRoot.setAttribute(IDXConstants.DOCUMENT_ROOT, documentRoot);
	}
	
	public void setDocumentType(String documentType) {
		idxDocumentRoot.setAttribute(IDXConstants.DOCUMENT_TYPE, documentType);
	}
	
	public void setDocumentName(String documentName) {
		idxDocumentRoot.setAttribute(IDXConstants.DOCUMENT_NAME, documentName);
	}
	
	public void setNavaigation(String navigation) {
		idxDocumentRoot.setAttribute(IDXConstants.NAVIGATION, navigation);
	}
	
	public void setDocumentDir(String dir) {		
		idxDocumentRoot.setAttribute(IDXConstants.DIR, dir);
	}
	
	public void setDocumentDir() {
		int dirValue = 0;
		for (String dir:pagesDirection){
			if (dir.equals(IDXConstants.RIGHT_TO_LEFT))
				dirValue += -1;
			else
				dirValue += 1;
		}
		
		if (dirValue >=0)
			setDocumentDir(IDXConstants.LEFT_TO_RIGHT);
		else
			setDocumentDir(IDXConstants.RIGHT_TO_LEFT);
	}

	public void addTocEntry(int chunkIndex, int pageIndex, int level, String id, String title) {
		
		Element tocEntryElement = root.createElement(IDXConstants.TOC_ENTRY);
		tocEntryElement.setAttribute(IDXConstants.CHUNK, Integer.toString(chunkIndex));
		if (pageIndex >= 0)
			tocEntryElement.setAttribute(IDXConstants.PAGE, Integer.toString(pageIndex));
		tocEntryElement.setAttribute(IDXConstants.REF_ID, id);
		tocEntryElement.setAttribute(IDXConstants.TITLE, title);
		if (level >= 0)
			tocEntryElement.setAttribute(IDXConstants.LEVEL, Integer.toString(level)); 
		else
			tocEntryElement.setAttribute(IDXConstants.LEVEL, "");
		
		if (getTitleTextDirection(title)<0)
			tocEntryElement.setAttribute(IDXConstants.DIR, IDXConstants.RIGHT_TO_LEFT);
		
		tocElement.appendChild(tocEntryElement);
	}
	
	public void addTofEntry(String name, String title, int width, int height) {
		Element tofEntryElement = root.createElement(IDXConstants.TOF_ENTRY);
		tofEntryElement.setAttribute(IDXConstants.NAME, name);
		tofEntryElement.setAttribute(IDXConstants.TITLE, title);
		tofEntryElement.setAttribute(IDXConstants.WIDTH, Integer.toString(width));
		tofEntryElement.setAttribute(IDXConstants.HEIGHT, Integer.toString(height));
		tofElement.appendChild(tofEntryElement);
	}
	
	public void addTofEntry(String name, String title, int width, int height,
			String sms, String url,String phone, String email) {
		Element tofEntryElement = root.createElement(IDXConstants.TOF_ENTRY);
		tofEntryElement.setAttribute(IDXConstants.NAME, name);
		tofEntryElement.setAttribute(IDXConstants.TITLE, title);
		tofEntryElement.setAttribute(IDXConstants.WIDTH, Integer.toString(width));
		tofEntryElement.setAttribute(IDXConstants.HEIGHT, Integer.toString(height));
		if (sms != null && sms.length() > 0)
			tofEntryElement.setAttribute(IDXConstants.SMS, sms);
		if (url != null && url.length() > 0)
			tofEntryElement.setAttribute(IDXConstants.URL, url);
		if (phone != null && phone.length() > 0)
			tofEntryElement.setAttribute(IDXConstants.PHONE, phone);
		if (email != null && email.length() > 0)
			tofEntryElement.setAttribute(IDXConstants.EMAIL, email);

		tofElement.appendChild(tofEntryElement);
	}

	public void addTableEntry(int chunkIndex, int pageIndex, String id, String title) {
		throw new IllegalArgumentException();
	}
	
	public void addStreamingTableEntry(String name, String title, int width, int height,
			int blockWidth, int blockHeight) {
		throw new IllegalArgumentException();
	}
	
	public void addPageEntry(int chunkIndex) {
		Element topEntryElement = root.createElement(IDXConstants.PAGE_ENTRY);
		topEntryElement.setAttribute(IDXConstants.CHUNK, Integer.toString(chunkIndex));
		topElement.appendChild(topEntryElement);
		pageNumber++;
	}
	
	public void addSectionEntry(String sectionName, int page) {
		addSectionEntryStr(sectionName, Integer.toString(page));
	}
	
	private void addSectionEntryStr(String sectionName, String pageStr) {
		if (!sections.containsKey(sectionName)) {
			StringBuilder pages = new StringBuilder();
			pages.append(pageStr);
			sections.put(sectionName, pages);
		}
		else {
			StringBuilder pages = sections.get(sectionName);
			pages.append(',');
			pages.append(pageStr);
		}
	}
	
	@Override
	public void serializeToStream(OutputStream stream) throws IOException {
		// assemble the document
		idxDocumentRoot.appendChild(tocElement);
		idxDocumentRoot.appendChild(tofElement);
		idxDocumentRoot.setAttribute(IDXConstants.PAGES, ""+pageNumber);
		if (totElement.hasChildNodes())
			idxDocumentRoot.appendChild(totElement);
		if(topElement.hasChildNodes())
			idxDocumentRoot.appendChild(topElement);
		if(sections.size() > 0) {
			Element tosElement = root.createElement(IDXConstants.TOS);
			for (Map.Entry<String,StringBuilder> sectionEntry : sections.entrySet()) {
				Element tosEntry = root.createElement(IDXConstants.TOS_ENTRY);
				tosEntry.setAttribute(IDXConstants.TITLE, sectionEntry.getKey());
				tosEntry.setAttribute(IDXConstants.PAGE, sectionEntry.getValue().toString());
				if (getTitleTextDirection(sectionEntry.getKey())<0)
					tosEntry.setAttribute(IDXConstants.DIR, IDXConstants.RIGHT_TO_LEFT);
				tosElement.appendChild(tosEntry);
			}
			idxDocumentRoot.appendChild(tosElement);
		}
		root.appendChild(idxDocumentRoot);
		
		super.serializeToStream(stream);
		
	}
	
	public void addFromFile(File file) throws IOException, SAXException {
		FileInputStream is = new FileInputStream(file);
		addFromStream(is);
	}
	
	public void addFromStream(InputStream is) throws IOException, SAXException {
		Document srcDoc = loadFromStream(is);
		Element idxRoot = srcDoc.getDocumentElement();
		String dir = idxRoot.getAttribute("dir");
		if (dir != null)
			pagesDirection.add(dir);
		// add tocElements
		copyElements(idxRoot, IDXConstants.TOC, tocElement);
		// add tofElements
		copyElements(idxRoot, IDXConstants.TOF, tofElement);
		// add totElements
		copyElements(idxRoot, IDXConstants.TOT, totElement);
		// add topElements
		copyElements(idxRoot, IDXConstants.TOP, topElement);
		// add section elements
		copySections(idxRoot);
	}
	
	public void addSectionFromStream(IDXDocument sectionIdx, 
				String sectionId, String sectionTitle, int pageOffset, int chunkOffset, 
				boolean updateGlobalAttributes) throws SAXException, IOException {		
		// get the root element
		Element idxRoot = sectionIdx.idxDocumentRoot;
		
		// copy global values to root
		if (updateGlobalAttributes) {
			String docType = idxRoot.getAttribute(IDXConstants.DOCUMENT_TYPE);
			setDocumentType(docType);
			String navigation = idxRoot.getAttribute(IDXConstants.NAVIGATION);
			setNavaigation(navigation);
			String dir = idxRoot.getAttribute("dir");
			if (dir != null)
				setDocumentDir(dir);
		}

		// process table of contents for section applying page offset to chunk index 
		NodeList srcList = idxRoot.getElementsByTagName(IDXConstants.TOC);
		if (srcList.getLength() == 1) {
			Node srcNode = srcList.item(0);
			NodeList children = srcNode.getChildNodes();
			for (int i=0; i<children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeName().equals(IDXConstants.TOC_ENTRY)) {
					Element tocElement = (Element)child;
					String chunkIndexStr = tocElement.getAttribute(IDXConstants.CHUNK);
					int chunkIndex = Integer.parseInt(chunkIndexStr) + chunkOffset;
					String pageIndexStr = tocElement.getAttribute(IDXConstants.PAGE);
					int pageIndex = Integer.parseInt(pageIndexStr) + pageOffset;
					String entryTitle = tocElement.getAttribute(IDXConstants.TITLE);
					String entryId = tocElement.getAttribute(IDXConstants.REF_ID);
					addTocEntry(chunkIndex, pageIndex, 0, entryId, entryTitle);
				}
			}
		}

		// process table of figures for section appending the section id to the figure
		srcList = idxRoot.getElementsByTagName(IDXConstants.TOF);
		if (srcList.getLength() == 1) {
			Node srcNode = srcList.item(0);
			NodeList children = srcNode.getChildNodes();
			for (int i=0; i<children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeName().equals(IDXConstants.TOF_ENTRY)) {
					Element tofElement = (Element)child;
					String widthStr = tofElement.getAttribute(IDXConstants.WIDTH);
					int width = Integer.parseInt(widthStr);
					String heightStr = tofElement.getAttribute(IDXConstants.HEIGHT);
					int height = Integer.parseInt(heightStr);
					String entryTitle = tofElement.getAttribute(IDXConstants.TITLE);
					String name = sectionId + "/" + tofElement.getAttribute(IDXConstants.NAME);
					addTofEntry(name, entryTitle, width, height);
				}
			}
		}

		// create section and page entries for each page
		srcList = idxRoot.getElementsByTagName(IDXConstants.TOP);
		if (srcList.getLength() == 1) {
			Node srcNode = srcList.item(0);
			NodeList children = srcNode.getChildNodes();
			int pageIndex = 0;
			for (int i=0; i<children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeName().equals(IDXConstants.PAGE_ENTRY)) {
					Element topElement = (Element)child;
					String chunkIndexStr = topElement.getAttribute(IDXConstants.CHUNK);
					int chunkIndex = Integer.parseInt(chunkIndexStr);
					addPageEntry(chunkIndex+chunkOffset);
					addSectionEntry(sectionTitle, pageIndex+pageOffset);
					pageIndex++;
				}
			}
		}
	}

	public void copyElements(Element srcRoot, String srcTag, Element to) {
		NodeList srcList = srcRoot.getElementsByTagName(srcTag);
		if (srcList.getLength() == 1) {
			Node srcNode = srcList.item(0);
			NodeList children = srcNode.getChildNodes();
			for (int i=0; i<children.getLength(); i++) {
				Node copy = root.importNode(children.item(i), true);
				if (copy.getNodeName().equals(IDXConstants.TOC_ENTRY)){
					if (copy instanceof Element){
						NamedNodeMap attribures = copy.getAttributes();
						Node chunkAttribute = attribures.getNamedItem("chunk");
						int currentChunkValue = Integer.parseInt(chunkAttribute.getNodeValue());					
												
						Element editedCopy = (Element)copy;
						editedCopy.setAttribute("chunk", ""+(chunkOffset+currentChunkValue));
						to.appendChild(editedCopy);
					}
				}	
				else
					to.appendChild(copy);
			}
		}
	}
	
	public void copySections(Element srcRoot) {
		NodeList srcList = srcRoot.getElementsByTagName(IDXConstants.TOS);
		if (srcList.getLength() == 1) {
			Node srcNode = srcList.item(0);
			NodeList children = srcNode.getChildNodes();
			for (int i=0; i<children.getLength(); i++) {
				Node child = children.item(i);
				if (child instanceof Element) {
					Element sectionElement = (Element)child;
					String section = sectionElement.getAttribute(IDXConstants.TITLE);
					String pages = sectionElement.getAttribute(IDXConstants.PAGE);
					addSectionEntryStr(section, pages);
				}
			}
		}
	}
	
    public int getTitleTextDirection(String chunk){  // returns negative if text is in left to right direction
    	int chunkDir = 0;
    	
    	for (int i = 0; i < chunk.length(); i++) 
    		chunkDir += getTextDirection(chunk.charAt(i));

    	return chunkDir;
    } 
    
    public int getTextDirection(char c){  // returns -1 if char is in right to left direction
		
    		byte directionality = Character.getDirectionality(c);
			if (directionality == 1 || directionality == 2 || directionality == 16 || directionality == 17 || directionality == 13 )
				return -1;
			if (directionality == 9 || directionality == 6 || directionality == 3 || directionality == 4
				|| directionality == 5 || directionality == 0 || directionality == 14 || directionality == 15)	
				return +1;						

    	return 0;
    }
    
    public void setChunkOffset(int chunkOffset){
    	this.chunkOffset = chunkOffset;
    }
}
