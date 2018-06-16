package inteligenca;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

public class Ocena2 {
	public static final int ZMAGA = 1000000; 
	public static final int ZGUBA = -ZMAGA;
	public static final int NEODLOCENO = 0;
	
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
				vrednostBeli = vrednostBeli + (Igra.DIM -1 - l.getX()) + (Igra.DIM -1 - l.getY());
			}
			
			for (Lokacija l: figuriceCrni) {
				vrednostCrni = vrednostCrni + l.getX() + l.getY();
			}
			
			if (jaz == Igralec.BELI) {
				return vrednostCrni - vrednostBeli;
			} else {
				return vrednostBeli - vrednostCrni;
			}
		}
	}
	
	
}
