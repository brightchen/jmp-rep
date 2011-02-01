package cg.publish.task.ftpTask;

import java.util.Date;
import java.util.Calendar;

import cg.publish.util.DocNameUtil;
import cg.common.logging.Logger;

public class FtpFileNameInfo
{
    private static final Logger log = Logger.getLogger( FtpFileNameInfo.class );
    
    public static enum  FILE_TYPES { PDF, CSV };
    public static final String SEP = DocNameUtil.SEP;
    //public static final String DATE_FORMAT = "MM" + SEP + "dd" + SEP + "yyyy";
    public static final int    MIN_FILE_NAME_LEN = 3;
    public static final String DEFAULT_SECTION_NAME = "defaultSection";
    
    public static FtpFileNameInfo parseFtpFileName( String ftpFileName )
    {
        if( ftpFileName == null || ftpFileName.length() < MIN_FILE_NAME_LEN )
        {
            log.warn( "parseFtpFileName(): Invalid input param: " + ftpFileName );
            return null;
        }
        
        String[] items = ftpFileName.split( SEP );
        
        if( items == null || ( items.length != 5 && items.length != 6 ) )
        {
            log.warn( "parseFtpFileName(): Invalid input param: " + ftpFileName + "; -- invalid item number." );
            return null;
        }
            
        
        // the last one has the type information, 
        // the quote is required as '.' has special meaning in regular expression
        String[] subItems = items[ items.length - 1 ].split( "\\." );
        if( subItems == null || subItems.length != 2 )
        {
            log.warn( "parseFtpFileName(): Invalid input param: " + ftpFileName + "; -- invalid sub-item number." );
            return null;
        }
        final String sYear = subItems[0];
        final String fileType = subItems[1];
        
        //we only support pdf file
        if( fileType == null 
         || ( !fileType.equalsIgnoreCase( "pdf" ) && !fileType.equalsIgnoreCase( "csv" ) ) )
        {
            log.warn( "parseFtpFileName(): Invalid file type: " + fileType );
            return null;
        }
        
        if( items.length == 5 )
        {
            //no section
            //Date theDate = parseDate( sYear, items[2], items[3] );
            //return new FtpFileNameInfo( items[0], items[1], theDate, fileType );
            //have to have section;
            return null;
        }
        if( items.length == 6 )
        {
            //have section
            Date theDate = parseDate( sYear, items[3], items[4] );
            return new FtpFileNameInfo( items[0], items[1], items[2], theDate, fileType );
        }
        
        return null;
    }
    
    /***********************
    public static String composeFtpFileName( String publicationName, String sectionName, String style,
                                             Date editionDate, String fileType )
    {
        FtpFileNameInfo fileNameInfo = new FtpFileNameInfo( publicationName, sectionName, style, 
                                                            editionDate, fileType );
        return fileNameInfo.composeFtpFileName();
    }
    /***********************/
    
    public FtpFileNameInfo( String publicationName, String sectionName, String style,
                            Date editionDate, String fileType )
    {
        if( publicationName == null || publicationName.length() == 0 
         || sectionName == null || sectionName.length() == 0 
         || style == null || style.length() == 0 
         || editionDate == null 
         || fileType == null || fileType.length() == 0 )
        {
            throw new IllegalArgumentException( "FtpFileNameInfo(): null or empty input parameter(s) are NOT allowed." );
        }
        this.publicationName    = publicationName;
        this.sectionName        = sectionName;
        this.style              = style;
        this.editionDate        = editionDate;
        setFileType( fileType );
    }

    //construction without sectionName
    public FtpFileNameInfo( String publicationName, String style,
                            Date editionDate, String fileType )
    {
        this( publicationName, style, DEFAULT_SECTION_NAME, editionDate, fileType );
    }

    /**********
    public String composeFtpFileName()
    {
        return publicationName + SEP +
               ( ( sectionName == null ) ? "" : ( sectionName + SEP ) ) +
               style + SEP + 
               formatDate( editionDate ) + 
               "." + fileType;
    }
    /**********/
    /*****
    public String composeSectionFileName()
    {
        return publicationName + SEP +
            ( ( sectionName == null ) ? "" : ( sectionName + SEP ) ) +
            formatDate( editionDate ) + 
            "." + fileType;
    }
    /*****/
    
    /***********
    //edition file name should NOT contains section name
    //in fact, it's a directory name for repository
    public String composeEditionDocumentName()
    {
        return DocNameUtil.generateEditionDocumentName( publicationName, editionDate );
    }
    /***********/
    
    
    protected static Date parseDate( String sYear, String sMonth, String sDate )
    {
        try
        {
            return parseDate( Integer.parseInt( sYear ), Integer.parseInt( sMonth ), Integer.parseInt( sDate ) );
        }
        catch( Exception e )
        {
            log.warn( "parseDate(): Invalid parameter. ", e );
            return null;
        }
    }
    
    protected static Date parseDate( int year, int month, int date )
    {
        Calendar calendar = Calendar.getInstance();
        
        // the month is 0 based
        calendar.set( year, month-1, date );
        return calendar.getTime();
    }
    
    ////////////////////////////////////////////////////////////////
    // getter/setter methods
    ////////////////////////////////////////////////////////////////
    //publicationName
    public String getPublicationName()
    {
        return publicationName;
    }
    public void setPublicationName( String publicationName )
    {
        this.publicationName = publicationName;
    }

    //sectionName
    public String getSectionName()
    {
        return sectionName;
    }
    public void setSectionName( String sectionName )
    {
        this.sectionName = sectionName;
    }

    //style
    public String getStyle()
    {
        return style;
    }
    public void setStyle( String style )
    {
        this.style = style;
    }

    //editionDate
    public Date getEditionDate()
    {
        return editionDate;
    }
    public void setEditionDate( Date editionDate )
    {
        this.editionDate = editionDate;
    }

    //editionName
    public String getEditionName()
    {
        if( editionDate == null )
            return null;
        return DocNameUtil.generateEditionName( editionDate );
    }
    
    //fileType
    public String getFileType()
    {
        return fileType;
    }
    public void setFileType( String fileType )
    {
        if( fileType == null || fileType.length() == 0 
         || ( !fileType.equalsIgnoreCase( "pdf" ) && !fileType.equalsIgnoreCase( "csv" ) ) )
        {
            throw new IllegalArgumentException( "Only support pdf and csv file, input fileType = " + fileType );
        }
        this.fileType = fileType;
        if( fileType.equalsIgnoreCase( "pdf" ) )
        {
            enumFileType = FILE_TYPES.PDF;
        }
        else if( fileType.equalsIgnoreCase( "csv" ) )
        {
            enumFileType = FILE_TYPES.CSV;
        }
    }

    //enumFileType
    public FILE_TYPES getEnumFileType()
    {
        return enumFileType;
    }

    private String publicationName;
    private String sectionName;
    private String style;
    private Date   editionDate;
    private String fileType;
    private FILE_TYPES enumFileType;

}