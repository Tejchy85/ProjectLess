package Test;

import igra.Igra;
import igra.Lokacija;
import junit.framework.TestCase;

public class TestIgra extends TestCase {

	public void testIgre() {
		Igra igra = new Igra();
		//ta tester je potrebno pognati, ko bodo že definirane pravilno ograjice.
		Lokacija zacetna = new Lokacija(1,2);
		Lokacija koncna = new Lokacija(1,3);
		assertEquals(igra.veljavnaPoteza(zacetna,koncna), true);
	}
}
