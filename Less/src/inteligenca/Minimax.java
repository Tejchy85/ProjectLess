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
 * Inteligenca, ki uporabi algoritem minimax.
 *
 */
public class Minimax extends SwingWorker<Poteza, Object> {

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
	
	private static final int OTEZENPRESKOK = 10;
	
	//private static final int OTEZENA = 10;
	
	/**
	 * @param master glavno okno, v katerem vlecemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		//Thread.sleep(50);
		OcenjenaPoteza p = minimax(0, igra);
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
	 * Z metodo minimax poisce najboljso potezo v dani igri.
	 * 
	 * @param k stevec globine, do kje smo ze preiskali 
	 * @param igra
	 * @return najboljsa poteza (ali null, ce bi igra bila zakljucena), skupaj z oceno najbolj�e poteze
	 */
	private OcenjenaPoteza minimax(int k, Igra igra) {
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
		
		List<Poteza> poteze = new LinkedList<Poteza>();
		if (!GlavnoOkno.vseDobre().isEmpty()){
			poteze = GlavnoOkno.vseDobre();
		} else {
			poteze = GlavnoOkno.vseMoznePoteze();
		}
		for (Poteza p : poteze) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.narediPotezo(p);
			
			// Izracunamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// ce je p boljsa poteza, si jo zabelezimo
			if (najboljsa == null // ce nimamo kandidata za najboljso potezo
				|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
				|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
				) {
				najboljsa = p;
				ocenaNajboljse = ocenaP;
			}
			
			
			if (Math.abs(p.getKoncna().getX() - p.getZacetna().getX()) == 2 || Math.abs(p.getKoncna().getY()- p.getZacetna().getY()) == 2){
				ocenaNajboljse *= OTEZENPRESKOK;
			}
			
			/* ta del kode doloci vecjo vrednost potezam v pravo smer
			if (naPotezi == Igralec.BELI) {
				if (p.getKoncna().getX() - p.getZacetna().getX() > 0 || p.getKoncna().getY() - p.getZacetna().getY() > 0 ) {
					ocenaNajboljse *= OTEZENA;
				}
			} else {
				if(p.getKoncna().getX() - p.getZacetna().getX() < 0 || p.getZacetna().getY() - p.getKoncna().getY() > 0 ){
					ocenaNajboljse *= OTEZENA;
				}
			}
			*/				
		}
		
		/*if (najboljsa == null){					//seznam dobrih je bil prazen, izberemo nakljucno, ki se premika v nepravo smer
			Random generator = new Random();
			List<Poteza> poteze = GlavnoOkno.vseMoznePoteze();
			int q = generator.nextInt(poteze.size());		
			najboljsa = poteze.get(q);
		}*/

		// Vrnemo najboljso najdeno potezo in njeno oceno
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
	
}