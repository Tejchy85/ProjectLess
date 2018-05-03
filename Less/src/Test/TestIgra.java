package Test;



import java.util.List;

import igra.Igra;
import igra.Lokacija;
import junit.framework.TestCase;

public class TestIgra extends TestCase {

	public void testIgre() {
		Igra igra = new Igra();
		//ta tester je potrebno pognati, ko bodo že definirane pravilno ograjice.
		Lokacija zacetna = new Lokacija(0,0);
		Lokacija koncna = new Lokacija(1,2);
		assertEquals(igra.veljavnaPoteza(zacetna,koncna), false);
		for (Lokacija l : igra.moznePoteze(zacetna.getX(), zacetna.getY(), 3)){
			l.print();
		}
		

		
	}
}
