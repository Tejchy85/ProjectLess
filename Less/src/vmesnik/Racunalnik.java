package vmesnik;

import javax.swing.SwingWorker;

import igra.Igralec;
import igra.Poteza;
import inteligenca.Minimax;
import inteligenca.MinimaxAlfaBeta;
import inteligenca.Nakljucno;
import inteligenca.AlfaBeta;

public class Racunalnik extends Strateg {
	private GlavnoOkno master;
	private Igralec rac;
	private SwingWorker<Poteza,Object> mislec;
	
	public Racunalnik(GlavnoOkno master, Igralec igralec) {
		this.master = master;
		rac = igralec;
	}
	
	@Override
	public void na_potezi() {
		//mislec = new Nakljucno(master);
		//mislec = new Minimax(master,2, rac);
		//mislec = new AlfaBeta(master, 6, rac);
		mislec = new MinimaxAlfaBeta(master, 2, rac);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	public void klik (Poteza poteza) {
	}
	
	@Override
	public boolean uporabljaGUI() {
		return false;
	}

}
