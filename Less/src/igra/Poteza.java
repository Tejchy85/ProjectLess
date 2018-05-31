package igra;

import java.util.LinkedList;
import java.util.List;

public class Poteza {
	
	private Lokacija zacetna;
	private Lokacija koncna;
	
	public Poteza (Lokacija zacetna, Lokacija koncna) {
		this.zacetna = zacetna;
		this.koncna = koncna;
		}
	
	
	public Lokacija getZacetna() {
		return zacetna;
	}

	public Lokacija getKoncna() {
		return koncna;
	}
	
}