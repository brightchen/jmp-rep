/*
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 * @author Bright Chen
 */

package cg.usermodule.util;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;

public class FileReaderUtil
{
    public static final int BUFFER_LEN = 4096;
    
    public static String readWholeFileAsString( File file ) throws IOException
    {
        if( file == null )
        {
            throw new IllegalArgumentException( "file should NOT null" );
        }
        
        FileReader fr = new FileReader( file );
        StringBuffer content = new StringBuffer();
        char[] buffer = new char[ BUFFER_LEN ];
        
        while( true )
        {
            int readLen = fr.read( buffer );
            if( readLen <= 0 )
                break;
            content.append( buffer, 0, readLen );
        }
        
        return content.toString();
    }
}