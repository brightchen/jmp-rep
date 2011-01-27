
package cg.publish.web.util;

/**
 * @author Bright Chen
 */

public final class PhoneUtil
{
    private static final char[] IGNORE_CHARS = { '-', '+', '.', ' ' };
    public static final String makeupPhoneNumber( String phoneNumber )
    {
        if( phoneNumber == null || phoneNumber.length() == 0 )
            return phoneNumber;
        
        //for current implementation, we just ignore the seperate chars
        //the possible change is dealing with the country code
        final int phoneNumberLen = phoneNumber.length();
        StringBuffer newPhoneNumber = new StringBuffer( phoneNumberLen );
        
        for( int index = 0; index < phoneNumberLen; ++index )
        {
            char checkChar = phoneNumber.charAt( index );
            for( char theChar : IGNORE_CHARS )
            {
                if( checkChar == theChar )
                {
                    //ignore this char;
                    continue;
                }
            }
            newPhoneNumber.append( checkChar );
        }
        
        return newPhoneNumber.toString();
    }
}