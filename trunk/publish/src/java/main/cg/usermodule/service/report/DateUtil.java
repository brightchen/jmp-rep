/******************
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 * @author Bright Chen
 *******************/

package cg.publish.service.report;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.FieldPosition;

public final class DateUtil
{
    public static final String getGeneratedOn()
    {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat( "MMM d, yyyy HH:mm:ss aaa z" ); 
        FieldPosition pos = new FieldPosition(0);
        StringBuffer empty = new StringBuffer();
        StringBuffer dateString = sdf.format( currentTime, empty, pos);
        return dateString.toString();
    }

    public static final String getDateString( Date date )
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" ); 
        FieldPosition pos = new FieldPosition(0);
        StringBuffer empty = new StringBuffer();
        StringBuffer dateString = sdf.format( date, empty, pos);
        return dateString.toString();
    }

}