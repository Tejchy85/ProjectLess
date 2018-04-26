package Test;

import igra.Polje;
import junit.framework.TestCase;

public class TestPlosca extends TestCase {
	
	public void testPlosca () {
	
		Polje[] pol = new Polje[5];
		for (Polje i : pol){
			System.out.println(i.toString());
			}	
	}

}
