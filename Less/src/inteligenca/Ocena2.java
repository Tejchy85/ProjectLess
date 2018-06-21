package inteligenca;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

public class Ocena2 {
	public static final int ZMAGA = 1000000; 
	public static final int ZGUBA = -ZMAGA;
	public static final int NEODLOCENO = 0;
	
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
			int vrednostBeli = 0;
			int vrednostCrni = 0;
			Lokacija[] figuriceBeli = igra.getIgralnaPlosca().belaPolja();
			Lokacija[] figuriceCrni = igra.getIgralnaPlosca().crnaPolja();
			
			for (Lokacija l : figuriceBeli) {
				vrednostBeli = vrednostBeli + (6 -1 - l.getX()) + (6 -1 - l.getY());
			}
			
			for (Lokacija l: figuriceCrni) {
				vrednostCrni = vrednostCrni + l.getX() + l.getY();
			}
			
			if (jaz == Igralec.BELI) {
				return 2*vrednostCrni - vrednostBeli;
			} else {
				return 2*vrednostBeli - vrednostCrni;
			}
		
	}
	
	
}
