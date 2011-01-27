package cg.common.util.file;

import java.util.TreeMap;

	public final class Colour
	{
	  private static TreeMap<String, String> ColorSpaceTable = new TreeMap<String, String>();
	  static {
	  final String[][]  COLORSPACE_LOOKUP;

	  COLORSPACE_LOOKUP = new String[][] {
			  new String[] {"black", "000000" },
			  new String[] {"white", "ffffff" },
			  new String[] {"red", "ff0000" },
			  new String[] {"bright green", "00ff00" },
			  new String[] {"blue", "0000ff" },
			  new String[] {"yellow","ffff00" },
			  new String[] {"pink", "ff00ff" },
			  new String[] {"fuchsia", "ff00ff" },
			  new String[] {"turquoise","00ffff"},
			  new String[] {"dark red","800000" },
			  new String[] {"maroon","800000" },
			  new String[] {"green", "008000" },
			  new String[] {"dark blue", "000080" },
			  new String[] {"navy", "000080" },
			  new String[] {"dark yellow", "808000" },
			  new String[] {"olive", "808000" },
			  new String[] {"violet", "800080" },
			  new String[] {"teal", "008080" },
			  new String[] {"grey 25%", "c0c0c0" },
			  new String[] {"grey 50%", "808080" },
			  new String[] {"periwinkle%", "9999ff" },
			  new String[] {"plum", "993366" },
			  new String[] {"ivory", "ffffcc" },
			  new String[] {"light turquoise", "ccffff" },
			  new String[] {"dark purple","660066" },
			  new String[] {"coral", "ff8080" },
			  new String[] {"ocean blue", "0066cc" },
			  new String[] {"ice blue", "ccccff" },
			  new String[] {"dark blue", "000080" },
			  new String[] {"magenta", "ff00ff" },
			  new String[] {"turqoise","00ffff" },
			  new String[] {"dark red","800000" },
			  new String[] {"teal","008080" },
			  new String[] {"sky blue", "00ccff" },
			  new String[] {"light green","ccffcc" },
			  new String[] {"very light yellow","ffff99" },
			  new String[] {"pale blue","99ccff" },
			  new String[] {"rose","ff99cc" },
			  new String[] {"lavender","cc99ff" },
			  new String[] {"tan","ffcc99" },
			  new String[] {"light blue", "3366ff" },
			  new String[] {"aqua","33cccc" },
			  new String[] {"lime","99cc00" },
			  new String[] {"gold","ffcc00" },
			  new String[] {"light orange","ff9900" },
			  new String[] {"orange","ff6600" },
			  new String[] {"blue grey","6666cc" },
			  new String[] {"grey 40%","969696" },
			  new String[] {"silver","969696" },
			  new String[] {"dark teal","003366" },
			  new String[] {"sea green","339966" },
			  new String[] {"dark green","003300" },
			  new String[] {"olive green","333300" },
			  new String[] {"brown","993300" },
       		  new String[] {"indigo","333399" },
			  new String[] {"grey 80%","333333" },
			  new String[] {"gray","333333" },
			  new String[] {"automatic", "ffffff" },
			  new String[] {"auto", "ffffff" }
			  };
		 for (int i = 0; i < COLORSPACE_LOOKUP.length; i++)
			 ColorSpaceTable.put(COLORSPACE_LOOKUP[i][0], COLORSPACE_LOOKUP[i][1]);
	  }
	  public static String getColorSpace(String color) {
		  	return (String)ColorSpaceTable.get(color.toLowerCase() );
	  }
	  
	 
}
