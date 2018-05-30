package vmesnik;

import igra.Igralec;
import igra.Lokacija;

public class Clovek extends Strateg {
	private GlavnoOkno master;
	private Igralec jaz;
	
	public Clovek(GlavnoOkno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	
	@Override
	public void na_potezi() {
	}

	@Override
	public void prekini() {
	}

	@Override
	public void klik(Lokacija zacetna, Lokacija koncna) {
		master.odigraj(zacetna, koncna);
	}
	
	@Override
	public boolean uporabljaGUI() {
		return true;
	}

}