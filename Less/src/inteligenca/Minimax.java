package inteligenca;

import javax.swing.SwingWorker;

import vmesnik.GlavnoOkno;
import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

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
	 * Ali raèualnik igra X ali O?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * Pove katera figurica se bo premikala
	 */
	private Lokacija zacetna; 
	
	/**
	 * @param master glavno okno, v katerem vleèemo poteze
	 * @param globina koliko potez naprej gledamo
	 * @param jaz koga igramo
	 */
	public Minimax(GlavnoOkno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Lokacija doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = minimax(globina, igra);
		this.zacetna = p.zacetna;
		assert (this.zacetna != null);
		assert (p.koncna != null);
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
	 * @return najboljša poteza (ali null, èe ji ni), skupaj z oceno najboljše poteze
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
			// dosegli smo najveèjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najboljšo do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najboljših potez, ker je lahko
		// v neki poziciji veè enakovrednih najboljših potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili nakljuèno izbrano izmed najboljših potez, kar bi
		// popestrilo igro raèunalnika.
		Lokacija najboljsa = null;
		int ocenaNajboljse = 0;
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
			}
			
		}

		// Vrnemo najboljšo najdeno potezo in njeno oceno
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
	
}