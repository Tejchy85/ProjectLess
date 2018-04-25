package Test;

import igra.Ploscica;
import junit.framework.TestCase;

public class TestPloscica extends TestCase {
	
	public void testPloscica(){
		Ploscica p = new Ploscica(new int[] {1,2,3});
		p.rotiraj();
		assertArrayEquals(p.getOgrajice(), new int[] {4,5,6}); //error: kaj pa zdej!!:o
		/*for (int i : p.getOgrajice()){
			System.out.println(i);
		*/
	}
}

