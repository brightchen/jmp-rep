package cg.common.util.xml;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class LayoutSerializer extends FileXMLSerializer
{
  public LayoutSerializer( File file ) throws IOException
  {
    super( file );
  }

  public LayoutSerializer( OutputStream os )
  {
    super( os );
  }

  public void initLayout() throws SAXException
  {
    initLayout( null );
  }

  public void initLayout( String dirAttr ) throws SAXException
  {
    startDocument();
    startElement( "", "", "layout", null );

    AttributesImpl attr = null;
    if ( dirAttr != null )
    {
      attr = new AttributesImpl();
      attr.addAttribute( "", "", "dir", "DIRATTR", dirAttr );
    }
    startElement( "", "", "page", attr );

    startElement( "", "", "articles", null );
    endElement( "", "", "articles" );
    startElement( "", "", "ads", null );
    endElement( "", "", "ads" );
    startElement( "", "", "unassignedContent", null );
  }

  public void startUnassignedTextElement( Rectangle2D area, boolean newParagraph ) throws SAXException
  {
    AttributesImpl attr = new AttributesImpl();
    attr.addAttribute( "", "", "area", "CDATA", rectToString( area ) );
    attr.addAttribute( "", "", "newParagraph", "CDATA", Boolean.toString( newParagraph ) );
    startElement( "", "", "text", attr );
    startElement( "", "", "content", null );
  }

  public void endUnassignedTextElement() throws SAXException
  {
    endElement( "", "", "content" );
    endElement( "", "", "text" );
  }

  public void writeUnassignedFigureElement( Rectangle2D area, Rectangle2D captionArea, String captionString )
      throws SAXException
  {
    AttributesImpl attr = new AttributesImpl();
    attr.addAttribute( "", "", "area", "CDATA", rectToString( area ) );
    if ( captionArea != null )
    {
      attr.addAttribute( "", "", "captionArea", "CDATA", rectToString( area ) );
    }
    else if ( captionString != null )
    {
      attr.addAttribute( "", "", "captionString", "CDATA", captionString );
    }
    startElement( "", "", "figure", attr );
    endElement( "", "", "figure" );
  }

  public static String rectToString( Rectangle2D rect )
  {
    StringBuilder rectString = new StringBuilder();
    rectString.append( floorAreaCoordinate( rect.getX() ) );
    rectString.append( ',' );
    rectString.append( floorAreaCoordinate( rect.getY() ) );
    rectString.append( ',' );
    rectString.append( ceilAreaCoordinate( rect.getX() + rect.getWidth() ) );
    rectString.append( ',' );
    rectString.append( ceilAreaCoordinate( rect.getY() + rect.getHeight() ) );
    return rectString.toString();
  }

  public void endLayout() throws SAXException
  {
    endElement( "", "", "unassignedContent" );
    endElement( "", "", "page" );
    endElement( "", "", "layout" );
    endDocument();
  }

}
