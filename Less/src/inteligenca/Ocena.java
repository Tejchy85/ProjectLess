
package inteligenca;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;

/**
 * Ocena trenutne pozicije
 *
 */
public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, vec kot vsaka druga ocena pozicije
	public static final int ZGUBA = -ZMAGA;  // vrednost izgube, mora biti -ZMAGA
	public static final int NEODLOCENO = 0;  // vrednost neodlocene igre


	
	/**
	 * @param jaz - igralec, ki zeli oceno
	 * @param igra - trentno stanje igre (ne spreminjaj tega objekta!)
	 * @return ocena - vrednosti pozicije (ce je igre konec, je ocena zagotovo pravilna)
	 */
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		// Ovrednotimo glede na to, koliko kvote potrebuje vsak igralec do koncne pozicije
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
		
		if (jaz == Igralec.BELI){
			return 2*vrednostBeli - vrednostCrni;		//malo pomnozimo, da ne igra defenzivno
		}
			return 2*vrednostCrni - vrednostBeli;
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
			
			if((x+1)!=dim-1 || (y+1)!=dim-1){
				if(x+1 < dim){
					vrednostDesno =  optimalnaBeli(new Lokacija(x + 1, y), igra, ocena + 1 + ograjiceNavp[y][x+1]);
				}else {
					vrednostDesno = 42;
				}
				if(y+1 < dim){
					vrednostDol = optimalnaBeli(new Lokacija(x, y + 1), igra, ocena + 1 + ograjiceVod[y+1][x]);			
				} else{
					vrednostDol = 42;
				}
				return Math.min(vrednostDesno, vrednostDol);
			}
			return 12 - ocena;
	}
	
	public static int optimalnaCrni (Lokacija l, Igra igra, int ocena){ 
		int[][] ograjiceVod = igra.getIgralnaPlosca().getOgrajiceVod();
		int[][] ograjiceNavp = igra.getIgralnaPlosca().getOgrajiceNavp();
		int x = l.getX();
		int y = l.getY();
		int vrednostGor = 0;
		int vrednostLevo = 0;
		
		if((x-1)!=0 || (y-1)!=0){
			if(x-1 > 0){
				vrednostLevo = optimalnaCrni(new Lokacija(x - 1, y), igra, ocena + 1 + ograjiceNavp[y][x]);
			}else {
				vrednostLevo = 42;
			}
			if(y-1 > 0){
				vrednostGor = optimalnaCrni(new Lokacija(x, y - 1), igra, ocena + 1 + ograjiceVod[y][x]);			
			} else{
				vrednostGor = 42;
			}
			return Math.min(vrednostLevo, vrednostGor);
		}
		return 12 - ocena;
		
	}
}