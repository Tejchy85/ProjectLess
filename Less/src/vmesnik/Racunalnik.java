package vmesnik;

import javax.swing.SwingWorker;

import igra.Igralec;
import igra.Lokacija;
import inteligenca.Minimax;
import inteligenca.Nakljucno;

public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec rac;
	private SwingWorker<Lokacija,Object> mislec;
	
	public Racunalnik(GlavnoOkno master, Igralec igralec) {
		this.master = master;
		rac = igralec;
	}
	
	@Override
	public void na_potezi() {
		mislec = new Minimax(master, 2, rac);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik(Lokacija zacetna, Lokacija koncna) {
	}

}
