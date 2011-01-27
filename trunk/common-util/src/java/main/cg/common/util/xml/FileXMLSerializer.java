package cg.common.util.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;


public class FileXMLSerializer extends XMLSerializer {
	
	protected static DecimalFormat layoutAreaNumberFormat = new DecimalFormat("0.000000"); // we care about six digits after the point
	protected static final int digitsAfterPoint = 10000000; 


	public FileXMLSerializer(File file) throws IOException {
		OutputStream os = new FileOutputStream(file);
		init(os);
	}
	
	public FileXMLSerializer(OutputStream os) {
		init(os);
	}
	protected void init(OutputStream os) {
		OutputFormat outFormat = new OutputFormat();

		// Setup format settings
		outFormat.setOmitXMLDeclaration(true);
		outFormat.setEncoding("utf-8");
		outFormat.setMethod("xml");
		outFormat.setIndenting(false);
		// outFormat.setIndent(4);

		// Define a Writer
		setOutputByteStream(os);

		// Apply the format settings
		setOutputFormat(outFormat);
	}
	
	
	protected static String ceilAreaCoordinate(double c){
		c = c * digitsAfterPoint;
		c = Math.ceil(c);
		c = c / digitsAfterPoint;
		return layoutAreaNumberFormat.format(c);
	}

	protected static String floorAreaCoordinate(double c){
		c = c * digitsAfterPoint;
		c = Math.floor(c);
		c = c / digitsAfterPoint;
		return layoutAreaNumberFormat.format(c);
	}
	
}
