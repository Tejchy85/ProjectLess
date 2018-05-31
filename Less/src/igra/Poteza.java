package igra;

public class Poteza {
	
	Lokacija zacetna;
	Lokacija koncna;
	
	public Poteza (Lokacija zacetna, Lokacija koncna) {
		this.zacetna = zacetna;
		this.koncna = koncna;
		}
	
	public Poteza[] moznePoteze() {
		return new Poteza[1];
	}
	
}