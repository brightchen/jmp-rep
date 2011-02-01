
/*
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 * @author Bright Chen
 */

package cg.usermodule.util;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class provides consistent logic of document name
 */

public final class DocNameUtil
{
    public static final String SEP = "_";
    public static final String FTP_FILENAME_DATE_FORMAT = "MM" + SEP + "dd" + SEP + "yyyy";
    public static final String INTERNAL_FILENAME_DATE_FORMAT = "MMM d yyyy";
    
    public static String generateEditionDocumentName( String publicationName, Date editionDate )
    {
        return generateEditionDocumentName( publicationName, generateEditionName( editionDate ) );
    }
    
    public static String generateEditionDocumentName( String publicationName, String editionName )
    {
        if( publicationName == null || publicationName.length() == 0 
         || editionName == null || editionName.length() == 0 )
        {
            throw new IllegalArgumentException( "Invalid publicationName = " + publicationName 
                                                + " or editionName = " + editionName );            
        }
        return publicationName + SEP + convertEditionName( editionName );
    }
    
    public static String generateSectionDocumentName( String editionDocumentName, String sectionName )
    {
        if( editionDocumentName == null || editionDocumentName.length() == 0 
         || sectionName == null || sectionName.length() == 0 )
        {
            throw new IllegalArgumentException( "Invalid editionDocumentName = " + editionDocumentName 
                                       + " or sectionName = " + sectionName );            
        }
        return editionDocumentName + SEP + sectionName;
    }
    
    public static String generateEditionName( Date editionDate )
    {
        return formatDate( editionDate, INTERNAL_FILENAME_DATE_FORMAT );
    }
    
    private static String convertEditionName( String editionName )
    {
        return editionName.replace( " ", SEP );
    }
    
    private static String formatDate( Date date, String format )
    {
        if( date == null || format == null )
        {
            throw new IllegalArgumentException( "Invalid date = " + date + "; format = " + format );
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat( format ); 
        FieldPosition pos = new FieldPosition(0);
        StringBuffer empty = new StringBuffer();
        StringBuffer dateString = sdf.format( date, empty, pos);
        return dateString.toString();
    }    
}