package vmesnik;

import igra.Lokacija;

public class Clovek extends Strateg {
	private GlavnoOkno master;
	
	public Clovek(GlavnoOkno master) {
		this.master = master;
	}
	
	@Override
	public void na_potezi() {
	}

	@Override
	public void prekini() {
	}

	@Override
	public void klik(Lokacija p) {
		master.odigraj(p);
	}

}