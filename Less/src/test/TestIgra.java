package test;


import igra.Igra;
import igra.Lokacija;
import igra.Poteza;
import junit.framework.TestCase;

public class TestIgra extends TestCase {

	public void testIgre() {
		Igra igra = new Igra();
		//assertEquals(igra.veljavnaPoteza(zacetna,koncna), true);
		Igra igra2 = new Igra(igra);
		igra2.narediPotezo(new Poteza(new Lokacija(1,1), new Lokacija(1,2)));
		//assertEquals(igra.getNaPotezi(),igra2.getNaPotezi() );
		assertEquals(igra.getIgralnaPlosca(), igra2.getIgralnaPlosca());
	}
}
