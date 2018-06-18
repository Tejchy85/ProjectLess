package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;
import vmesnik.GlavnoOkno;
import igra.Igra;
import igra.Igralec;
import igra.Poteza;

/**
 * Inteligenca, ki uporabi algoritem alfa beta rezanje.
 *
 */
public class AlfaBeta extends SwingWorker<Poteza, Object> {

	/**
	 * Glavno okno, v katerem poteka ta igra
	 */
	private GlavnoOkno master;

	/**
	 * Globina, do katere pregleduje minimax
	 */
	private int globina;

	/**
	 * Ali racualnik igra Belega ali Crnega?
	 */
	private Igralec jaz; // koga igramo
	
	//private static final int OTEZENPRESKOK = 8;
	
	//private static final int OTEZENA = 10;
	
	/**
	 * @param master glavno okno, v katerem vlecemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public AlfaBeta (GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		//Thread.sleep(50);
		OcenjenaPoteza p = alfabeta(0, igra, Ocena.ZGUBA, Ocena.ZMAGA, true);
		assert (p != null);
		return p.poteza;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { master.odigraj(p); }
		} catch (Exception e) {
		}
	}

	/**
	 * Z metodo alfabeta hitreje poisce najboljso potezo v dani igri.
	 * 
	 * @param k stevec globine, do kje smo ze preiskali 
	 * @param igra
	 * @return najboljsa poteza (ali null, ce bi igra bila zakljucena), skupaj z oceno najboljse poteze
	 */
	private OcenjenaPoteza alfabeta(int k, Igra igra, int alfa, int beta, boolean maksi) {
		Igralec naPotezi = null;
		// Ugotovimo, ali je konec, ali je kdo na potezi?
		switch (igra.getTrenutnoStanje()) {
		case BELI_NA_POTEZI: naPotezi = Igralec.BELI; break;
		case CRNI_NA_POTEZI: naPotezi = Igralec.CRNI; break;
		// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
		case ZMAGA_CRNI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.CRNI ? Ocena.ZMAGA : Ocena.ZGUBA));
		case ZMAGA_BELI:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.BELI ? Ocena.ZMAGA : Ocena.ZGUBA));
		case NEODLOCENO:
			return new OcenjenaPoteza(null, Ocena.NEODLOCENO);
		}
		assert (naPotezi != null);
		// Nekdo je na potezi, ugotovimo, kaj se splaca igrati
		if (k >= globina) {
			//System.out.println("tukaj sem, ocena te pozicije je" + Ocena.oceniPozicijo(jaz, igra));

			// dosegli smo najvecjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(jaz, igra));
		} 
	
		//Iscemo najboljso potezo
		Poteza najboljsa = null;
		int ocenaNajboljse = 0;
		int ocenaP = 0;
		
		List<Poteza> poteze = new LinkedList<Poteza>();
		poteze = GlavnoOkno.vseMoznePoteze();
		
		if (maksi){
			ocenaP = Ocena.ZGUBA;
			for (Poteza p : poteze) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.narediPotezo(p);
				
				if (kopijaIgre.getNaPotezi() == igra.getNaPotezi()){
					maksi = !maksi;
				}
				ocenaP = Math.max(ocenaP, alfabeta(k+1, kopijaIgre, alfa, beta, maksi).vrednost);

				if (najboljsa == null || ocenaP > ocenaNajboljse) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
				alfa = Math.max(ocenaP, alfa);
				if (alfa >= beta){
					break;
				}
			}
		} else{
			ocenaP = Ocena.ZMAGA;
			for (Poteza p : poteze) {
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.narediPotezo(p);
				
				if (kopijaIgre.getNaPotezi() == igra.getNaPotezi()){
					maksi = !maksi;
				}
				ocenaP = Math.min(ocenaP, alfabeta(k+1, kopijaIgre, alfa, beta, maksi).vrednost);

				if (najboljsa == null || ocenaP < ocenaNajboljse) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
				beta = Math.min(ocenaP, alfa);
				if (alfa <= beta){
					break;
				}
			}
		}
				
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
}