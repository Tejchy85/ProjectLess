package inteligenca;

import igra.Igra;
import igra.Igralec;
import igra.Plosca;
import igra.Polje;

/**
 * Ocena trenutne pozicije
 *
 */
public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, veè kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int NEODLOCENO = 0;  // vrednost neodloèene igre


	
	/**
	 * @param jaz igralec, ki želi oceno
	 * @param igra trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena vrednosti pozicije (èe je igre konec, je ocena zagotovo pravilna)
	 */
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		//TODO: prestudirat kako toèno bo on ocenu pozicijo.
		switch (igra.getTrenutnoStanje()) {
		case ZMAGA_BELI:
			return (jaz == Igralec.BELI ? ZMAGA : ZGUBA);
		case ZMAGA_CRNI:
			return (jaz == Igralec.CRNI ? ZMAGA : ZGUBA);
		case NEODLOCENO:
			return NEODLOCENO;
		case BELI_NA_POTEZI:
		case CRNI_NA_POTEZI:
			// Preštejemo, koliko teric ima vsak igralec
		//	Plosca plosca = igra.getIgralnaPlosca();
		//	int vrednostX = 0;
		//	int vrednostO = 0;
		//	for (Terica t : Igra.terice) {
		//		int poljaX = 0;
		//		int poljaO = 0;
		//		for (int k = 0; k < Igra.N && (poljaX == 0 || poljaO == 0); k++) {
		//			switch (plosca[t.x[k]][t.y[k]]) {
		//			case O: poljaO += 1; break;
		//			case X: poljaX += 1; break;
		//			case PRAZNO: break;
		//			}
		//		}
		//		if (poljaX == 0 && poljaO > 0) { vrednostO += vrednostTerice(poljaX); }
		//		if (poljaO == 0 && poljaX > 0) { vrednostX += vrednostTerice(poljaO); }
		//	}
		//	// To deljenje z 2 je verjetno brez veze ali celo narobe
		//	return (jaz == Igralec.CRNI ? (vrednostX - vrednostO/2) : (vrednostO - vrednostX/2));
		}
		assert false;
		return 42; // Java je blesava
	}
	
}