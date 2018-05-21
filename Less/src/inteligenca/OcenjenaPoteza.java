package inteligenca;

import igra.Lokacija;

/**
 * Poteza z oceno, kako dobra je ocena.
 * Poteza je lahko tudi {@null} (èe je npr. konec igre),
 * v tem primeru ocena pove, kako dobra je pozicija.
 * 
 *
 */
public class OcenjenaPoteza {
	protected Lokacija zacetna;
	protected Lokacija koncna;
	protected int vrednost;
	
	public OcenjenaPoteza(Lokacija zacetna, Lokacija koncna, int vrednost) {
		super();
		this.zacetna = zacetna;
		this.koncna = koncna;
		this.vrednost = vrednost;
	}

	@Override
	public String toString() {
		return "OcenjenaPoteza [zacetna = " + zacetna +  ", koncna = " + koncna +", vrednost = " + vrednost + " ]";
	}
	
}