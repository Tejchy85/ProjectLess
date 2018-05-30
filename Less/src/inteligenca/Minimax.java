package inteligenca;

import javax.swing.SwingWorker;
import vmesnik.GlavnoOkno;
import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

//TODO poiskat napako v minimax;
//poteza se mora izvest ena naenkrat
// preverit pravilnost racunanja z globino
//ce bo igral defenzivno, moramo mimimax dopolnit, da gre naprej

/**
 * Inteligenca, ki uporabi algoritem minimax.
 *
 */
public class Minimax extends SwingWorker<Lokacija, Object> {

	/**
	 * Glavno okno, v katerem poteka ta igra
	 */
	private GlavnoOkno master;

	//TODO: kaj toèno bo globina pri nas: kolikokrat bo na vrsti nek igralec?
	/**
	 * Globina, do katere pregleduje minimax
	 */
	private int globina;

	/**
	 * Ali raèualnik igra Belega ali Crnega?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * Pove katera figurica se bo premikala
	 */
	private Lokacija zacetna;
	
	private static final int OTEZENPRESKOK = 8;
	
	private static final int OTEZENA = 10;
	
	/**
	 * @param master glavno okno, v katerem vleèemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
		this.zacetna = null;
	}
	
	@Override
	protected Lokacija doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		Thread.sleep(500);
		OcenjenaPoteza p = minimax(0, igra);
		//System.out.println(p.toString());
		
		assert (this.zacetna != null);
		this.zacetna = p.zacetna;
		//System.out.println("izbral sem zacetno");
		assert (p.koncna != null);
		//System.out.println("dolocam se koncno");

		return p.koncna;
	}
	
	@Override
	public void done() {
		try {
			Lokacija p = this.get();
			if (p != null) { master.odigraj(this.zacetna, p); }
		} catch (Exception e) {
		}
	}

	/**
	 * Z metodo minimax poišèi najboljšo potezo v dani igri.
	 * 
	 * @param k števec globine, do kje smo že preiskali 
	 * @param igra
	 * @return najboljša poteza (ali null, èe bi igra bila zakljucena), skupaj z oceno najboljše poteze
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
					null,
					(jaz == Igralec.CRNI ? Ocena.ZMAGA : Ocena.ZGUBA));
		case ZMAGA_BELI:
			return new OcenjenaPoteza(
					null,
					null,
					(jaz == Igralec.BELI ? Ocena.ZMAGA : Ocena.ZGUBA));
		case NEODLOCENO:
			return new OcenjenaPoteza(null, null, Ocena.NEODLOCENO);
		}
		assert (naPotezi != null);
		// Nekdo je na potezi, ugotovimo, kaj se splaèa igrati
		if (k >= globina) {
			//System.out.println("tukaj sem, ocena te pozicije je" + Ocena.oceniPozicijo(jaz, igra));

			// dosegli smo najveèjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					null,
					Ocena.oceniPozicijo(jaz, igra));
		} 
	
		//Išèemo najboljši premik
		Lokacija najboljsa = null;
		int ocenaNajboljse = 0;
		Lokacija najboljsaGledeNaFigurico = null; 
		int ocenaNajboljsaGledeNaFigurico = 0;
		Lokacija najboljsaFigurica = null;
		
		Lokacija[] figurice = new Lokacija[4];
		switch (naPotezi) {
		case BELI: figurice = igra.getIgralnaPlosca().belaPolja(); break;
		case CRNI: figurice = igra.getIgralnaPlosca().crnaPolja(); break;
		}
		
		for (Lokacija z : figurice) {
			for (Lokacija p : igra.moznePoteze(z, igra.getKvotaPremikov())) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.narediPotezo(z,p);
				//System.out.println("juhu, naredil sem potezo v kopiji!");
				
				// Izraèunamo vrednost pozicije po odigrani potezi p
				int ocenaP = minimax(k+1, kopijaIgre).vrednost;
				// Èe je p boljša poteza, si jo zabeležimo
				if (najboljsa == null // še nimamo kandidata za najboljšo potezo
					|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
					|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
					) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
				if (Math.abs(z.getX() - p.getX()) == 2 || Math.abs(z.getY()- p.getY()) == 2){
					ocenaNajboljse *= OTEZENPRESKOK;
				}
				if (naPotezi == Igralec.BELI) {
					if (p.getX() - z.getX() > 0 || p.getY() - z.getY() > 0 ) {
						ocenaNajboljse *= OTEZENA;
					}
				} else {
					if(z.getX() - p.getX() > 0 || z.getY() - p.getY() > 0 ){
						ocenaNajboljse *= OTEZENA;
					}
				}
						
			}
			if (najboljsaGledeNaFigurico == null
					|| (ocenaNajboljse > ocenaNajboljsaGledeNaFigurico)) {
					//System.out.println("shranjujem si");
					najboljsaGledeNaFigurico = najboljsa;
					najboljsaFigurica = z;
					ocenaNajboljsaGledeNaFigurico = ocenaNajboljse;
					}
			
		}

		// Vrnemo najboljšo najdeno potezo in njeno oceno
		assert (najboljsaFigurica != null);
		assert (najboljsaGledeNaFigurico != null);
		return new OcenjenaPoteza(najboljsaFigurica, najboljsaGledeNaFigurico, ocenaNajboljsaGledeNaFigurico);
	}
	
}