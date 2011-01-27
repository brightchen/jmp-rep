/**
 * 
 */
package cg.common.util.xml;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Replaces invalid xml characters in the stream with a replacement character
 */
public class XMLInvalidCharacterFilter extends FilterReader {

	final char replaceChar;

	public XMLInvalidCharacterFilter(Reader reader) {
		this(reader, '?');
	}
	
	public XMLInvalidCharacterFilter(Reader reader, char replaceChar) {
		super(reader);
		this.replaceChar = replaceChar;
	}
	
	private final boolean validateChar(int c) {
		// anything >= 0x20 as well as tab, newline and cr are OK
		if (c >= 0x20 || c == 0x9 || c == 0xa || c == 0xd)
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see java.io.FilterReader#read()
	 */
	@Override
	public int read() throws IOException {
		int c =  super.read();
		if (validateChar(c) || c == -1)
			return c;
		else
			return replaceChar;
	}

	/* (non-Javadoc)
	 * @see java.io.FilterReader#read(char[], int, int)
	 */
	@Override
	public int read(char[] buf, int start, int len) throws IOException {
		int n = super.read(buf, start, len);
		int end = start + n;
		for (int i=start; i<end; i++) {
			if (!validateChar(buf[i]))
				buf[i] = replaceChar;
		}
		return n;
	}
}
