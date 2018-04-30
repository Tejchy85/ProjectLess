package Test;

import igra.Igra;
import igra.Ploscica;
import igra.Polje;

import static org.junit.Assert.*;
import junit.framework.TestCase;

public class TestPloscica extends TestCase {
	
	public void testPloscica() {
		Ploscica p = new Ploscica(new int[] {1,2,3});
		p.nakljucno_rotiraj();;
		//assertEquals([1],[1]); //error: kaj pa zdej!!:o
		for (int i : p.getOgrajice()){
			System.out.println(i); //rotira pravilno. assertArrayEquals ne dela...
		}		
		
		//ugotovila sva, kako (priblizno) deluje enum
		Polje[] pol = new Polje[5];
		for (Polje i : pol){
			i = Polje.CRNO;
			System.out.println(i);
			}
		
		Igra igra = new Igra();								//ograjice se postavijo pravilno
		int[][] ogr = igra.getIgralnaPlosca().getOgrajiceVod();
		for (int[] vre : ogr) {
			for (int vr : vre) {
				System.out.print(vr);
			}
		}
		System.out.println();
		int[][] ogra = igra.getIgralnaPlosca().getOgrajiceNavp();
		for (int[] vre : ogra) {
			for (int vr : vre) {
				System.out.print(vr);
			}
		}
		
	}
	
			
}
