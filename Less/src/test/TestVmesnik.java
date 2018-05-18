package test;

import junit.framework.TestCase;
import vmesnik.GlavnoOkno;

public class TestVmesnik extends TestCase {
	
	public void pozeni() {
		assertEquals(false, true);
		GlavnoOkno okno = new GlavnoOkno();
		okno.pack();
		okno.setVisible(true);
	} 
	
	//pozeni();
	
}
