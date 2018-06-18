package igra;

//import java.util.LinkedList;
//import java.util.List;

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


	@Override
	public boolean equals(Object arg0) {
		if (this == arg0) {
			return true;
		}
		if (arg0 == null) {
			return false;
		}
		if (getClass() != arg0.getClass()) {
			return false;
		}
		Poteza other = (Poteza) arg0;
		if (this.zacetna.equals(other.zacetna) && this.koncna.equals(other.koncna)) {
			return true;
		}
		return false;
	}


	@Override
	public String toString() {
		return "Poteza [zacetna=" + zacetna + ", koncna=" + koncna + "]";
	}
	
	
	
}