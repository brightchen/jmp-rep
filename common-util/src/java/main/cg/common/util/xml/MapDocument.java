package cg.common.util.xml;

import org.w3c.dom.Element;

public class MapDocument extends XMLDocument
{
  Element pageElement;

  public MapDocument( int pageIndex )
  {
    Element mapRoot = root.createElement( "pageMap" );
    root.appendChild( mapRoot );
    pageElement = root.createElement( "page" );
    pageElement.setAttribute( "index", "" + pageIndex );
    mapRoot.appendChild( pageElement );
  }

  public void addArea( String type, String title, String coords, String refId, String chunk )
  {
    Element area = root.createElement( "area" );
    area.setAttribute( "type", type );
    area.setAttribute( "title", title );
    area.setAttribute( "coords", coords );
    area.setAttribute( "refId", refId );
    area.setAttribute( "chunk", chunk );
    pageElement.appendChild( area );
  }

  public void addAdArea( String type, String title, String coords, String imageName, String sms, String url,
                         String phone, String email )
  {
    Element area = root.createElement( "area" );
    area.setAttribute( "type", type );
    area.setAttribute( "title", title );
    area.setAttribute( "coords", coords );
    if ( imageName != null )
      area.setAttribute( "imageName", imageName );
    if ( sms != null )
      area.setAttribute( "sms", sms );
    if ( url != null )
      area.setAttribute( "url", url );
    if ( phone != null )
      area.setAttribute( "phone", phone );
    if ( email != null )
      area.setAttribute( "email", email );
    pageElement.appendChild( area );
  }
}
