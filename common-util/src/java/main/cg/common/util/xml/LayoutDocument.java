package cg.common.util.xml;

import java.awt.geom.Rectangle2D;

import org.w3c.dom.Element;


public class LayoutDocument extends XMLDocument {
	Element unassignedContent;
	public LayoutDocument() {
		Element layoutRoot = root.createElement("iseePageLayout");
		root.appendChild(layoutRoot);
		Element pageElement = root.createElement("page");
		layoutRoot.appendChild(pageElement);
		Element articles = root.createElement("articles");
		pageElement.appendChild(articles);
		Element ads = root.createElement("ads");
		pageElement.appendChild(ads);
		unassignedContent = root.createElement("unassignedContent");
		pageElement.appendChild(unassignedContent);
	}
	
	public void addUnassignedTextElement(Rectangle2D area, boolean newParagraph, String textContent) {
		Element text = root.createElement("text");
		text.setAttribute("area", rectToString(area));
		text.setAttribute("newParagraph", Boolean.toString(newParagraph));
		if (textContent != null && textContent.length() > 0) {
			Element content = root.createElement("content");
			content.setTextContent(textContent);
			text.appendChild(content);
		}
		unassignedContent.appendChild(text);
	}
	
	public void addUnassignedFigureElement(Rectangle2D area, Rectangle2D captionArea, String captionString) {
		Element figure = root.createElement("figure");
		figure.setAttribute("area", rectToString(area));
		if (captionArea != null)
			figure.setAttribute("captionArea", rectToString(captionArea));
		if (captionString != null)
			figure.setAttribute("captionString", captionString);
		unassignedContent.appendChild(figure);
	} 
	
	public static String rectToString(Rectangle2D rect) {
		StringBuilder rectString = new StringBuilder();
		rectString.append(rect.getX());
		rectString.append(',');
		rectString.append(rect.getY());
		rectString.append(',');
		rectString.append(rect.getX() + rect.getWidth());
		rectString.append(',');
		rectString.append(rect.getY() + rect.getHeight());
		return rectString.toString();
	}
	
	public static Rectangle2D stringToRect(String string) {
		// parse the image rectangle
		String[] strValues = string.split(",");
		float x1 = Float.parseFloat(strValues[0]);
		float y1 = Float.parseFloat(strValues[1]);
		float x2 = Float.parseFloat(strValues[2]);
		float y2 = Float.parseFloat(strValues[3]);
		return new Rectangle2D.Float(x1, y1, x2-x1, y2-y1);

	}
}
