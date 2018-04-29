package Test;

import igra.Igra;
import igra.Poteza;
import junit.framework.TestCase;

public class TestIgra extends TestCase {

	public void testIgre() {
		Igra igra = new Igra();
		//ta tester je potrebno pognati, ko bodo že definirane pravilno ograjice.
		Poteza zacetna = new Poteza(1,2);
		Poteza koncna = new Poteza(1,3);
		assertEquals(igra.veljavnaPoteza(zacetna,koncna), true);
	}
}
