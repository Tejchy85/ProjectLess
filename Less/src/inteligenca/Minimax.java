package inteligenca;

import javax.swing.SwingWorker;
import vmesnik.GlavnoOkno;
import igra.Igra;
import igra.Igralec;
import igra.Poteza;

//TODO poiskat napako v minimax;
//poteza se mora izvest ena naenkrat
// preverit pravilnost racunanja z globino
//ce bo igral defenzivno, moramo mimimax dopolnit, da gre naprej

/**
 * Inteligenca, ki uporabi algoritem minimax.
 *
 */
public class Minimax extends SwingWorker<Poteza, Object> {

	/**
	 * Glavno okno, v katerem poteka ta igra
	 */
	private GlavnoOkno master;

	//TODO: kaj to�no bo globina pri nas: kolikokrat bo na vrsti nek igralec?
	/**
	 * Globina, do katere pregleduje minimax
	 */
	private int globina;

	/**
	 * Ali ra�ualnik igra Belega ali Crnega?
	 */
	private Igralec jaz; // koga igramo
	
	private static final int OTEZENPRESKOK = 8;
	
	private static final int OTEZENA = 10;
	
	/**
	 * @param master glavno okno, v katerem vle�emo poteze
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
		// Nekdo je na potezi, ugotovimo, kaj se spla�a igrati
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
		
		for (Poteza p : GlavnoOkno.vseMoznePoteze(naPotezi)) {
			// V kopiji igre odigramo potezo p
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.narediPotezo(p);
			//System.out.println("juhu, naredil sem potezo v kopiji!");
			
			// Izracunamo vrednost pozicije po odigrani potezi p
			int ocenaP = minimax(k+1, kopijaIgre).vrednost;
			// ce je p boljsa poteza, si jo zabelezimo
			if (najboljsa == null // �e nimamo kandidata za najbolj�o potezo
				|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
				|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
				) {
				najboljsa = p;
				ocenaNajboljse = ocenaP;
			}
			if (Math.abs(p.getKoncna().getX() - p.getZacetna().getX()) == 2 || Math.abs(p.getKoncna().getY()- p.getZacetna().getY()) == 2){
				ocenaNajboljse *= OTEZENPRESKOK;
			}
			if (naPotezi == Igralec.BELI) {
				if (p.getKoncna().getX() - p.getZacetna().getX() > 0 || p.getKoncna().getY() - p.getZacetna().getY() > 0 ) {
					ocenaNajboljse *= OTEZENA;
				}
			} else {
				if(p.getKoncna().getX() - p.getZacetna().getX() > 0 || p.getZacetna().getY() - p.getKoncna().getY() > 0 ){
					ocenaNajboljse *= OTEZENA;
				}
			}
					
		}

		// Vrnemo najboljso najdeno potezo in njeno oceno
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
	
}