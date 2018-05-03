package Test;

import junit.framework.Test;
//import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests{
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestPloscica.class);
		return suite;		
	}
}
