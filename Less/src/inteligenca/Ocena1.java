package inteligenca;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

public class Ocena1 {
	//ocenjevalna kot smo jo naredili na govorilni uri 
	
	public static final int ZMAGA = (1 << 20); // vrednost zmage, vec kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int NEODLOCENO = 0;  // vrednost neodlocene igre

	/**
	 * @param jaz - igralec, ki zeli oceno
	 * @param igra - trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena - vrednosti pozicije (ce je igre konec, je ocena zagotovo pravilna)
	 */
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.getTrenutnoStanje()) {
		case ZMAGA_BELI:
			return (jaz == Igralec.BELI ? ZMAGA : ZGUBA);
		case ZMAGA_CRNI:
			return (jaz == Igralec.CRNI ? ZMAGA : ZGUBA);
		case NEODLOCENO:
			return NEODLOCENO;
		default: 
			int vrednostBeli = 0;
			int vrednostCrni = 0;
			Lokacija[] figuriceBeli = igra.getIgralnaPlosca().belaPolja();
			Lokacija[] figuriceCrni = igra.getIgralnaPlosca().crnaPolja();
			
			for (Lokacija l : figuriceBeli) {
				vrednostBeli = vrednostBeli - (Igra.DIM -1 - l.getX()) + (Igra.DIM -1 - l.getY());
			}
			vrednostBeli += narazen(figuriceBeli);
			
			for (Lokacija l: figuriceCrni) {
				vrednostCrni = vrednostCrni + l.getX() + l.getY();
			}
			vrednostCrni += narazen(figuriceCrni);
			
			if (jaz == Igralec.BELI) {
				return -vrednostBeli;
			} else {
				return -vrednostCrni;
			}
		}
	}

public static int narazen (Lokacija[] figurice){
	int max = 0;
	int x1 = 0;
	int x2 = 0;
	int y1 = 0;
	int y2 = 0;
	for (Lokacija l1 : figurice){
		for (Lokacija l2 : figurice){
			x1 = l1.getX();
			x2 = l2.getX();
			y1 = l1.getY();
			y2 = l2.getY();
			int mmax = Math.max(Math.abs(x1-x2),Math.abs(y1-y2));
		if (mmax > max){
			max = mmax;
		}
		}
	}
	return 5*(6 - max);
}
}