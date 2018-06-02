package test;


import java.util.LinkedList;

import igra.Igra;
import igra.Lokacija;
import igra.Poteza;
import junit.framework.TestCase;

public class TestIgra extends TestCase {

	public void testIgre() {
		Igra igra = new Igra();
		//assertEquals(igra.veljavnaPoteza(zacetna,koncna), true);
		//Igra igra2 = new Igra(igra);
		//igra2.narediPotezo(new Poteza(new Lokacija(1,1), new Lokacija(1,2)));
		//assertEquals(igra.getNaPotezi(),igra2.getNaPotezi() );
		//assertEquals(igra.getIgralnaPlosca(), igra2.getIgralnaPlosca());
		Poteza poteza = new Poteza(new Lokacija(1,1), new Lokacija(1,2));
		LinkedList<Poteza> mozne = igra.moznePoteze(poteza.getZacetna(), igra.getKvotaPremikov());
		for (Poteza p : mozne) {
			System.out.println("Zacetna: " );
			p.getZacetna().print();
			System.out.print("Koncna: ");
			p.getKoncna().print();
		}
		assertEquals(igra.veljavnaPoteza(poteza), true);
		igra.narediPotezo(poteza);
		System.out.println(igra.getIgralnaPlosca().getVsa_polja()[2][1]);
		System.out.println(igra.getIgralnaPlosca().getVsa_polja()[1][1]);
	}
	
}
