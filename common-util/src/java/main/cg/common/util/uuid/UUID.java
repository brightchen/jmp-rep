package cg.common.util.uuid;

import java.util.*;
import java.net.*;

public class UUID
{
  private static long lastTime = 0;
  private static long thisTime;
  private static long timeOffset;
  private static long nanosPerDay;
  private static short versionNumber;
  private static short variantNumber;
  private static short thisClockSeq;
  private static short lastClockSeq;
  private static short newUuid[] = new short[16];
  private static long timeMask[] = new long[3];

  // Note that this is essentially a "base36", but several letters are omitted
  // because they resemble numeric characters.
  private static final char base36Char[] =
  { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
   'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y' };

  private static Random randVal;
  private static UUID instance = new UUID();

  public static UUID getInstance()
  {
    return instance;
  }

  private UUID()
  {
    // Set constant values:
    versionNumber = (short) 0x0010;
    variantNumber = (short) 0x0080;
    timeMask[0] = (long) 0x00000000FFFFFFFFL;
    timeMask[1] = (long) 0x0000FFFF00000000L;
    timeMask[2] = (long) 0x0FFF000000000000L;
    randVal = new Random( System.currentTimeMillis() );
    // To do: Load real IEEE 802 address.
    for ( int i = 0; i < 10; i++ )
      newUuid[i] = 0;

    // set this based on local IP
    try
    {
      byte[] localIP = InetAddress.getLocalHost().getAddress();
      newUuid[10] = (short) localIP[0];
      newUuid[11] = (short) localIP[1];
      newUuid[12] = (short) localIP[2];
      newUuid[13] = (short) localIP[3];
      newUuid[14] = (short) randVal.nextInt();
      newUuid[15] = (short) randVal.nextInt();
    }
    catch ( UnknownHostException e )
    {
      newUuid[10] = (short) 0x0000;
      newUuid[11] = (short) 0x0080;
      newUuid[12] = (short) 0x00c7;
      newUuid[13] = (short) 0x0044;
      newUuid[14] = (short) 0x0055;
      newUuid[15] = (short) 0x00c6;
    }

    // Algorithm is number of 100-nanosecond intervals since 10/15/1582.
    // Java clocks are based on 1970.
    // Therefore offset is the number of 100-nanosecond intervals between
    // 10/15/1582 00:00:00 and 01/01/1900 00:00:00
    nanosPerDay = ( (long) 86400 * 10000000 );
    timeOffset = nanosPerDay * ( ( 365 * ( 1970 - 1583 ) ) // based on years
                                 + ( 24 * 3 ) // based on leap-year days / century
                                 + ( 5 ) // leap year days 1583-1600, inclusive
                                 + ( 17 ) // leap year days 1900-1970
    + ( 17 + 30 + 31 ) // remaining days in 1582
                 );

    // Initialize elements that can subsequently change:
    thisClockSeq = (short) ( ( randVal.nextInt() ) & 0x00003FFF );
    thisTime = timeOffset + ( 10000 * System.currentTimeMillis() );
    lastClockSeq = thisClockSeq;
  }

  /**
   * Returns a String as a base36 Number with a check sum.
   * 
   * @return String UUID
   */

  public static String getNextUUID()
  {
    // create a base36 Number and add check sum
    return ( instance.getNewBase36Number() );
  }

  /**
   * Returns a String.
   * 
   * @return String Base36Number
   */

  public String getNewBase36Number()
  {
    return toBase36Number( getNewUuid() );
  }

  private byte[] getNewUuid()
  {
    boolean mustContinue;
    long timeBuff;
    int i;
    byte byteUuid[] = new byte[16];
    short tempUuid[] = new short[16];

    // This loop will stall until the time changes if we would
    // issue a duplicate value of timestamp + version.
    do
    {
      // Convert to nanoseconds. To Do: get real 100-nanoseconds.
      thisTime = timeOffset + ( 10000 * System.currentTimeMillis() );
      mustContinue = false;

      if ( thisTime > lastTime ) // Most common case
      {
        lastTime = thisTime;
        lastClockSeq = thisClockSeq;
      }
      else if ( thisTime < lastTime ) // Clock setback
      {
        thisClockSeq++;
        thisClockSeq %= 16384;
        lastClockSeq = thisClockSeq;
        lastTime = thisTime;
      }
      else
      // Multiple ID's in the same interval
      {
        if ( !mustContinue )
        {
          thisClockSeq++;
          thisClockSeq %= 16384;
        }
        if ( thisClockSeq == lastClockSeq )
          mustContinue = true;
      }

      if ( mustContinue )
        System.out.println( "getNewUuid() stalling!" ); // see if this ever happens
    }
    while ( mustContinue == true );

    // Now the fun. We assemble the UUID based on a variety of parts.
    for ( i = 0; i < 16; i++ )
      tempUuid[i] = newUuid[i];

    timeBuff = thisTime & timeMask[0];
    tempUuid[3] |= (short) ( timeBuff % 256 );
    timeBuff >>>= 8;
    tempUuid[2] |= (short) ( timeBuff % 256 );
    timeBuff >>>= 8;
    tempUuid[1] |= (short) ( timeBuff % 256 );
    timeBuff >>>= 8;
    tempUuid[0] |= (short) ( timeBuff % 256 ); // timeBuff >>>= 8;

    timeBuff = ( thisTime & timeMask[1] ) >>> 32;
    tempUuid[5] |= (short) ( timeBuff % 256 );
    timeBuff >>>= 8;
    tempUuid[4] |= (short) ( timeBuff % 256 ); // timeBuff >>>= 8;

    timeBuff = ( thisTime & timeMask[2] ) >>> 48;
    tempUuid[7] |= (short) ( timeBuff % 256 );
    timeBuff >>>= 8;
    tempUuid[6] |= (short) ( timeBuff % 16 ); // timeBuff >>>= 8;
    tempUuid[6] |= versionNumber;

    timeBuff = thisClockSeq;
    tempUuid[9] |= (short) ( timeBuff % 256 );
    timeBuff >>>= 8;
    tempUuid[8] |= (short) ( timeBuff % 64 ); // timeBuff >>>= 8;
    tempUuid[8] |= variantNumber;

    for ( i = 0; i < 16; i++ )
      byteUuid[i] = (byte) tempUuid[i];

    return byteUuid;
  }

  /**
   * Translates a "native" UUID to base 36 number. Relies exclusively on the temporal aspects of the identifier. In
   * addition, it produces a modified base-36 representation of the actual value
   */
  public String toBase36Number( byte[] rawUUID )
  {
    StringBuffer returnedValue = new StringBuffer( 16 );
    int mask = 0x0000001F; // Lowest-order 5 bits
    int wrkBuffer, tmpBuffer;
    int i, j, loBit, shiftBits;
    int loByte = 0x000000FF;

    for ( i = 0; i < 16; i++ )
    {
      // Figure out the byte offset from which we are going to grab stuff
      // Goal is to grab 5 bits at a time.
      j = ( i * 5 ) / 8; // Which byte has the high-order bit?
      loBit = ( ( i * 5 ) % 8 ); // Where is the lowest order bit in the byte?
      wrkBuffer = rawUUID[j]; // Grab the first byte
      wrkBuffer <<= 8; // Shift to high order
      if ( j < 15 ) // Just safety. Should never be false
      {
        tmpBuffer = loByte & ( rawUUID[j + 1] );
        wrkBuffer |= tmpBuffer; // Put in low-order byte
      }
      // Now get rid of the extraneous bits, high- and low-order.
      shiftBits = 11 - loBit; // How many bits to shift so that the hi-order bit is bit 4?
      wrkBuffer >>>= shiftBits; // Roll off what we don't need
      wrkBuffer &= mask; // Mask off extraneous hi bits.
      returnedValue.append( base36Char[wrkBuffer] );
    }

    return returnedValue.toString();
  }

  private static String getDigitsOnly( String s )
  {
    StringBuffer digitsOnly = new StringBuffer();
    char c;
    for ( int i = 0; i < s.length(); i++ )
    {
      c = s.charAt( i );
      if ( Character.isDigit( c ) )
      {
        digitsOnly.append( c );
      }
    }
    return digitsOnly.toString();
  }

  /**
   * Returns a String that added a a check sum to a key.
   * 
   * @return String with check sum
   */
  public static String addCheckSum( String uuidNumber )
  {
    String digitsOnly = getDigitsOnly( uuidNumber );
    // System.out.println("Has-digits = " + digitsOnly);
    int sum = 0;
    int digit = 0;
    int addend = 0;
    // start with the first most right digits
    // at the validation time this becomes the second one
    boolean timesTwo = true;
    /*
     * 4 4 1 7 1 2 3 4 5 6 7 8 9 1 1 4 x 2 = 8 4 1 x 2 = 2 7 1 x 2 = 2 2 3 x 2 = 6 4 5 x 2 = 10 6 7 x 2 = 14 8 9 x 2 =
     * 18 1 1 x 2 = 2 8 4 2 7 2 2 6 4 10 - 9 = 1 6 14 - 9 = 5 8 18 - 9 = 9 1 2 2 // finally add (10 - modulus) -the
     * check sum 4 4 1 7 1 2 3 4 5 6 7 8 9 1 1 2
     */
    for ( int i = digitsOnly.length() - 1; i >= 0; i-- )
    {
      digit = Integer.parseInt( digitsOnly.substring( i, i + 1 ) );
      if ( timesTwo )
      {
        addend = digit * 2;
        if ( addend > 9 )
        {
          addend -= 9;
        }
      }
      else
      {
        addend = digit;
      }
      sum += addend;
      timesTwo = !timesTwo;
    }

    int modulus = sum % 10;
    if ( modulus != 0 )
      modulus = 10 - modulus;

    // well, add this one at the end of the number
    String s = uuidNumber + modulus;
    return s;

  }

  /**
   * Returns true if the UUID Number is valid - pass the check sum test.
   * 
   * @return boolean isValid
   */
  public static boolean isValid( String uuidNumber )
  {
    // extract only the digits from uuid
    String digitsOnly = getDigitsOnly( uuidNumber );

    int sum = 0;
    int digit = 0;
    int addend = 0;
    boolean timesTwo = false;
    /*
     * 4 4 1 7 1 2 3 4 5 6 7 8 9 1 1 2 4 x 2 = 8 4 1 x 2 = 2 7 1 x 2 = 2 2 3 x 2 = 6 4 5 x 2 = 10 6 7 x 2 = 14 8 9 x 2 =
     * 18 1 1 x 2 = 2 2 8 4 2 7 2 2 6 4 10 - 9 = 1 6 14 - 9 = 5 8 18 - 9 = 9 1 2 2 8 4 2 7 2 2 6 4 1 6 5 8 9 1 2 2
     */

    for ( int i = digitsOnly.length() - 1; i >= 0; i-- )
    {
      digit = Integer.parseInt( digitsOnly.substring( i, i + 1 ) );
      if ( timesTwo )
      {
        addend = digit * 2;
        if ( addend > 9 )
        {
          addend -= 9;
        }
      }
      else
      {
        addend = digit;
      }
      sum += addend;
      timesTwo = !timesTwo;
    }
    // check if the modulus is 0 (the sum should be a number ending with 0)
    int modulus = sum % 10;
    return modulus == 0;

  }

}
