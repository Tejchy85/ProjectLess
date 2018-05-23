
package inteligenca;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

/**
 * Ocena trenutne pozicije
 *
 */
public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, veè kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int NEODLOCENO = 0;  // vrednost neodloèene igre


	
	/**
	 * @param jaz - igralec, ki želi oceno
	 * @param igra - trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena - vrednosti pozicije (èe je igre konec, je ocena zagotovo pravilna)
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
		
		// Preštejemo, koliko kvote potrebuje vsak igralec do koncne pozicije
		int vrednostBeli = 0;
		int vrednostCrni = 0;
		Lokacija[] figuriceBeli = igra.getIgralnaPlosca().belaPolja();
		Lokacija[] figuriceCrni = igra.getIgralnaPlosca().crnaPolja();
		
		for (Lokacija bFig : figuriceBeli){
			vrednostBeli += optimalnaBeli(bFig,igra,0);
		}
		for (Lokacija cFig : figuriceCrni){
			vrednostCrni += optimalnaCrni(cFig,igra,0);
		}
		
		if (igra.getNaPotezi() == Igralec.BELI){
			return vrednostBeli - vrednostCrni;
		}
			return vrednostCrni - vrednostBeli;
		}
		assert false;
		return 42; // Java je blesava
	}
	
	
	//racunamo optimalno pot za dano figurico dane barve
	//mozne izboljsave: zdruzitev obeh sledecih metod z dodatnim parametrom "igralec"
	public static int optimalnaBeli (Lokacija l, Igra igra, int ocena){			 
			int[][] ograjiceVod = igra.getIgralnaPlosca().getOgrajiceVod();
			int[][] ograjiceNavp = igra.getIgralnaPlosca().getOgrajiceNavp();
			int x = l.getX();
			int y = l.getY();
			int dim = igra.getIgralnaPlosca().getDim();
			int vrednostDol = 0;
			int vrednostDesno = 0;
			
			if(x!=dim-1 || y!=dim-1){
				if(x+1 < dim){
					vrednostDesno = optimalnaBeli(new Lokacija(x + 1, y), igra, ocena + 1 + ograjiceNavp[y][x+1]);
				}else {
					vrednostDesno = 300;
				}
				if(y+1 < dim){
					vrednostDol = optimalnaBeli(new Lokacija(x, y + 1), igra, ocena + 1 + ograjiceVod[y+1][x]);			
				} else{
					vrednostDol = 300;
				}
				return Math.min(vrednostDesno, vrednostDol);
			}
			
			
			return ocena;
	}
	
	public static int optimalnaCrni (Lokacija l, Igra igra, int ocena){ 
		int[][] ograjiceVod = igra.getIgralnaPlosca().getOgrajiceVod();
		int[][] ograjiceNavp = igra.getIgralnaPlosca().getOgrajiceNavp();
		int x = l.getX();
		int y = l.getY();
		int vrednostGor = 0;
		int vrednostLevo = 0;
		
		if(x!=0 || y!=0){
			if(x-1 > 0){
				vrednostLevo = optimalnaCrni(new Lokacija(x - 1, y), igra, ocena + 1 + ograjiceNavp[y][x]);
			}else {
				vrednostLevo = 300;
			}
			if(y-1 > 0){
				vrednostGor = optimalnaCrni(new Lokacija(x, y - 1), igra, ocena + 1 + ograjiceVod[y][x]);			
			} else{
				vrednostGor = 300;
			}
			return Math.min(vrednostLevo, vrednostGor);
		}
		
		
		return ocena;
}
	
}


//test: System.out.println(inteligenca.Ocena.optimalna(new Lokacija(1,1), this,0));