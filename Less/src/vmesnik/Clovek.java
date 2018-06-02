package vmesnik;

import igra.Igralec;
//import igra.Lokacija;
import igra.Poteza;

public class Clovek extends Strateg {
	private GlavnoOkno master;
	private Igralec jaz;	//nisem preprican, zakaj se tukaj pritozuje. V drugih razredih namrec uporabljamo podatek o igralcu.
	
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
	public void klik(Poteza poteza) {
		master.odigraj(poteza);
	}
	
	@Override
	public boolean uporabljaGUI() {
		return true;
	}

}