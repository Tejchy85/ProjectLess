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

	//TODO: kaj to�no bo globina pri nas: kolikokrat bo na vrsti nek igralec?
	/**
	 * Globina, do katere pregleduje minimax
	 */
	private int globina;

	/**
	 * Ali ra�ualnik igra X ali O?
	 */
	private Igralec jaz; // koga igramo
	
	/**
	 * Pove katera figurica se bo premikala
	 */
	private Lokacija zacetna; 
	
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
	 * Z metodo minimax poi��i najbolj�o potezo v dani igri.
	 * 
	 * @param k �tevec globine, do kje smo �e preiskali
	 * @param igra
	 * @return najbolj�a poteza (ali null, �e ji ni), skupaj z oceno najbolj�e poteze
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
		// Nekdo je na potezi, ugotovimo, kaj se spla�a igrati
		if (k >= globina) {
			// dosegli smo najve�jo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(
					null,
					null,
					Ocena.oceniPozicijo(jaz, igra));
		}
		// Hranimo najbolj�o do sedaj videno potezo in njeno oceno.
		// Tu bi bilo bolje imeti seznam do sedaj videnih najbolj�ih potez, ker je lahko
		// v neki poziciji ve� enakovrednih najbolj�ih potez. Te bi lahko zbrali
		// v seznam, potem pa vrnili naklju�no izbrano izmed najbolj�ih potez, kar bi
		// popestrilo igro ra�unalnika.
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
				// Izra�unamo vrednost pozicije po odigrani potezi p
				int ocenaP = minimax(k+1, kopijaIgre).vrednost;
				// �e je p bolj�a poteza, si jo zabele�imo
				if (najboljsa == null // �e nimamo kandidata za najbolj�o potezo
					|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
					|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
					) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
			}
			
		}

		// Vrnemo najbolj�o najdeno potezo in njeno oceno
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}
	
}