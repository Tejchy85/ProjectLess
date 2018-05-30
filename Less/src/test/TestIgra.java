package test;


import igra.Igra;
import igra.Igralec;
import igra.Lokacija;
import junit.framework.TestCase;

public class TestIgra extends TestCase {

	public void testIgre() {
		Igra igra = new Igra();
		//ta tester je potrebno pognati, ko bodo že definirane pravilno ograjice.
		Lokacija zacetna = new Lokacija(1,1);
		Lokacija koncna = new Lokacija(1,2);
		for (Lokacija l : igra.moznePoteze(zacetna, 3)){
			l.print();
		}
		System.out.println("nova");
		//assertEquals(igra.veljavnaPoteza(zacetna,koncna), true);
		Igra igra2 = new Igra(igra);
		igra2.narediPotezo(new Lokacija(1,1), new Lokacija(1,2));
		//assertEquals(igra.getNaPotezi(),igra2.getNaPotezi() );
		assertEquals(igra.getIgralnaPlosca(), igra2.getIgralnaPlosca());

		
	}
}
