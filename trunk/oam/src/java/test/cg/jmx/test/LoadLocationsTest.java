package cg.jmx.test;

import junit.framework.TestCase;

import cg.oam.config.PropertiesManager;

public class LoadLocationsTest extends TestCase {

	

	public void setUp() throws Exception {
		
	}

	public void tearDown() throws Exception {
		
	}

	public void testLoadLocations() {

		
		try {
			String systemPropertyName = "testapp.config.file";
			
			PropertiesManager manager = PropertiesManager.initPropertiesManager(systemPropertyName);
			String logLocation = manager.getLogPropertiesLocation();

			System.out.println("logLocation from '" + logLocation + "'");
			
			String appLocation = manager.getIseedocsPropertiesLocation();

			System.out.println("appLocation from '" + appLocation + "'");

			System.out.println("Test Done!");
			
		} catch (Exception e) {
			System.out.println("Error:");
			e.printStackTrace();
		}

	}

}
