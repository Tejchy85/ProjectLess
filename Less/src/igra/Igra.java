package igra;


/*TODO
 * postavljanje ploscic na glavno plosco plus vse realne prepisat
 * veljavne poteze in kaj se zgodi
 * premikanje igralca - premakni gor,dol. ce poteza velja, potem premakni, ce ne pa vrni da to ne gre oz ne naredi nic
 * stevci potez oz koliko potez je se ostalo
 * 
 * 
 */

public class Igra {
	//bo povezala vse skupaj, tukaj bo konstruktor za za�etek igre, ra�unanje potez
	//doloci final dimenzijo plosce, recimo 6
	
	/**
	 * Velikost igralne plo��e
	 */
	public static final int dim = 6;
	
	//ATRIBUTI: 
	protected Igralec naPotezi;
	protected Plosca igralna_plosca;
	private int stevecPotezBeli; // na za�etku so �tevci potez v skupnem enaki 0, ob vsakem koraku se pove�ajo za 1,2,3. 
	private int stevecPotezCrni; // zmagovalec je tisti ki bo postavil v nasprotni kot svoje figure v manj potezah. �e postavita v enako potezah, je rezultat neodo�en
	private int kvotaPremikov; // vsak igralec ima na voljo po 3 poteze vsaki� ko je na vrsti
	
	public Igra() {
		naPotezi = Igralec.BELI;
		stevecPotezBeli = 0; 
		stevecPotezCrni = 0; 
		kvotaPremikov = 3; 
		igralna_plosca = new Plosca(dim);
		
		
	}
	
	public Stanje stanje() {
		// Ali imamo zmagovalca ali ne ?
		if (igralna_plosca.konec() == true) { //ne morm pogruntat zkej to ne dela
			return Stanje.ZMAGA_CRNI;
		} else if (igralna_plosca.skorajkonec() == true) {
			kvotaPremikov = 3;
			return Stanje.CRNI_NA_POTEZI;
		} else {
			//kdo je na vrsti ? �e je igralcu zmankalo kvote se zamenjata, v nasprotnem primeru je zopet na vrsti. 
			if (kvotaPremikov == 0) {
				kvotaPremikov = 3; 
				if (naPotezi == Igralec.BELI) {
					return Stanje.CRNI_NA_POTEZI;
				} else {
					return Stanje.BELI_NA_POTEZI;
				}
			} else {
				if (naPotezi == Igralec.CRNI) {
					return Stanje.CRNI_NA_POTEZI;
				} else {
					return Stanje.BELI_NA_POTEZI;
				}
			}
		}
	}

	
}
