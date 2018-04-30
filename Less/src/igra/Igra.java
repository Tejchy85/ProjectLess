package igra;

import java.util.LinkedList;
import java.util.List;

/*TODO
 * veljavne poteze in kaj se zgodi
 * premikanje igralca - premakni gor,dol. ce poteza velja, potem premakni, ce ne pa vrni da to ne gre oz ne naredi nic
 * stevci potez oz koliko potez je se ostalo
 * zasedena polja so lahko 
 * stevcev ne rabimo (todo delete) - ko je skorajkonec (konecBeli) se zacne stetje: ce crni konca v 3 potezah je izenaceno, ce v manj je zmagal, ce ne izgubil
 * 				ce je konec==true, potem se igra takoj zakljuci - ce belega ni na koncu, je izgubil
 * 
 */

public class Igra {
	//bo povezala vse skupaj, tukaj bo konstruktor za zaèetek igre, raèunanje potez
	//doloci final dimenzijo plosce, recimo 6
	
	/**
	 * Velikost igralne plošèe
	 */
	public static final int dim = 6;
	
	//ATRIBUTI: 
	protected Igralec naPotezi;
	private Plosca igralnaPlosca;
	private int stevecPotezBeli; // na zaèetku so števci potez v skupnem enaki 0, ob vsakem koraku se poveèajo za 1,2,3. 
	private int stevecPotezCrni; // zmagovalec je tisti ki bo postavil v nasprotni kot svoje figure v manj potezah. Èe postavita v enako potezah, je rezultat neodoèen
	private int kvotaPremikov; // vsak igralec ima na voljo po 3 poteze vsakiè ko je na vrsti
	
	public Igra() {
		naPotezi = Igralec.BELI;
		stevecPotezBeli = 0; 
		stevecPotezCrni = 0; 
		kvotaPremikov = 3; 
		igralnaPlosca = new Plosca(dim);
		
		
	}
	
	public Stanje stanje() {
		// Ali imamo zmagovalca ali ne ?
		if (igralnaPlosca.konec() == true) { //ne morm pogruntat zkej to ne dela____ ce je beli ze konec potem je lahko izenaceno
			return Stanje.ZMAGA_CRNI;
		} else if (igralnaPlosca.konecBeli() == true) {
			kvotaPremikov = 3;
			return Stanje.CRNI_NA_POTEZI;
		} else {
			//kdo je na vrsti ? Èe je igralcu zmankalo kvote se zamenjata, v nasprotnem primeru je zopet na vrsti. 
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
	
	
	//ta funkcija ni še preverjena.
	/**
	 * 
	 * @param x - x koordinata figurice za katero preverjamo možne poteze.
	 * @param y - y koordinata figurice za katero preverjamo možne poteze.
	 * @param kvota - kvota potez igralca, ne moremo poklicati, èe ima igralec kvoto 0.
	 * @return vse možne poteze izbrane figurice
	 */
	public List<Poteza> moznePoteze(int x, int y, int kvota) {  //gledamo mozne poteze za eno figurico
		LinkedList<Poteza> poteze = new LinkedList<Poteza>();
		//Tekmovalec se lahko premakne za eno mesto dol, gor, levo, desno, 
		//za dve mesti dol, gor, levo, desno (le v primeru, ko preskoèi oviro). 
		//Preveriti je potrebno še èe igralec poskusi preskoèit. 
		
		//ta del se izvede, tudi v primeru ko imamo na voljo še 3 kvote, saj lahko porabimo le eno. 
		if (kvota >= 1) {
			//premik levo za eno mesto: x koordinata se zmanjša za 1, pogledamo v tabelo navpiènih ograjic
			//na mesto x. 
			
			//preverimo, da ne pademo iz plošèe.
			if ( x-1 >= 0) {
				//preverimo, èe je polje prazno
				if (igralnaPlosca.vsa_polja[y][x-1] == Polje.PRAZNO) {
					//preverimo, èe nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceNavp[y][x] == 0) {
						//èe vse to velja, je to veljavna poteza.
						poteze.add(new Poteza(x-1,y));
					}
				}
			}
			//premik desno za eno mesto: x koordinata se poveèa za 1, pogledamo v tabelo navpiènih ograjic
			//na mesto x + 1. 
			
			//preverimo, da ne pademo iz plošèe.
			if ( x+1 < dim) {
				//preverimo, èe je polje prazno
				if (igralnaPlosca.vsa_polja[y][x+1] == Polje.PRAZNO) {
					//preverimo, èe nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceNavp[y][x+1] == 0) {
						//èe vse to velja, je to veljavna poteza.
						poteze.add(new Poteza(x+1,y));
					}
				}
			}
			//premik gor za eno mesto: y koordinata se pomanjša za 1, pogledamo v tabelo vodoravnih ograjic
			//na mesto y.
			
			//preverimo, da ne pademo iz plošèe.
			if ( y-1 >= 0) {
				//preverimo, èe je polje prazno
				if (igralnaPlosca.vsa_polja[y-1][x] == Polje.PRAZNO) {
					//preverimo, èe nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceVod[y][x] == 0) {
						//èe vse to velja, je to veljavna poteza.
						poteze.add(new Poteza(x,y-1));
					}
				}	
			}
			
			//premik dol za eno mesto: y koordinata se poveèa za 1, pogledamo v tabelo vodoravnih ograjic
			//na mesto y+1.
			
			//preverimo, da ne pademo iz plošèe.
			if ( y+1 < dim) {
				//preverimo, èe je polje prazno
				if (igralnaPlosca.vsa_polja[y+1][x] == Polje.PRAZNO) {
					//preverimo, èe nimamo nobene ograjice, saj imamo samo eno kvoto.
					if (igralnaPlosca.ograjiceVod[y+1][x] == 0) {
						//èe vse to velja, je to veljavna poteza.
						poteze.add(new Poteza(x,y+1));
					}
				}	
			}
		
			//Premik za 2 mesti - preskok èez drugo figurico. 
			
			//Premik levo za dve mesti: x koordinata se bo zmanjšala za 2,
			//Ko preskakujemo figurice, ne smemo imeti nikjer ograjice, zato pogledamo v tabelo 
			//navpiènih ograjc, na mesto x-1 in x
			 
			if (x-2 >= 0) {
				if (igralnaPlosca.vsa_polja[y][x-2] == Polje.PRAZNO) {
					//dejansko preskoèimo nekoga
					if(igralnaPlosca.vsa_polja[y][x-1] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceNavp[y][x-1] == 0 && igralnaPlosca.ograjiceNavp[y][x]==0) {
							poteze.add(new Poteza(x-2,y));
						}
					}
				}
			}
			
			//Preskok desno
			if (x+2 < dim) {
				if (igralnaPlosca.vsa_polja[y][x+2] == Polje.PRAZNO) {
					//dejansko preskoèimo nekoga
					if(igralnaPlosca.vsa_polja[y][x+1] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceNavp[y][x+1] == 0 && igralnaPlosca.ograjiceNavp[y][x+2]==0) {
							poteze.add(new Poteza(x+2,y));
						}
					}
				}
			}
			
			//Preskok gor 
			if (y-2 >= 0) {
				if (igralnaPlosca.vsa_polja[y-2][x] == Polje.PRAZNO) {
					//dejansko preskoèimo nekoga
					if(igralnaPlosca.vsa_polja[y-1][x] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceVod[y-1][x] == 0 && igralnaPlosca.ograjiceVod[y][x]==0) {
							poteze.add(new Poteza(x,y-2));
						}
					}
				}
			}
			
			//Preskok dol
			if (y+2 < dim) {
				if (igralnaPlosca.vsa_polja[y+2][x] == Polje.PRAZNO) {
					//dejansko preskoèimo nekoga
					if(igralnaPlosca.vsa_polja[y][x-1] != Polje.PRAZNO) {
						if (igralnaPlosca.ograjiceVod[y+1][x] == 0 && igralnaPlosca.ograjiceNavp[y+2][x]==0) {
							poteze.add(new Poteza(x,y+2));
						}
					}
				}
			}
			
		}
		
		
		if (kvota >= 2) {
			//preveriti moramo samo premikanje za eno mesto levo, desno, gor, dol z eno ograjo. 
			
			//premik levo: 
			if ( x-1 >= 0) {
				if (igralnaPlosca.vsa_polja[y][x-1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x] == 1) {
						poteze.add(new Poteza(x-1,y));
					}
				}
			}
			
			//premik desno: 
			if ( x+1 < dim) {
				if (igralnaPlosca.vsa_polja[y][x+1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x+1] == 1) {
						poteze.add(new Poteza(x+1,y));
					}
				}
			}
			
			//premik gor: 
			if ( y-1 >= 0) {
				if (igralnaPlosca.vsa_polja[y-1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y][x] == 1) {
						poteze.add(new Poteza(x,y-1));
					}
				}	
			}
			
			//premik dol: 
			if ( y+1 < dim) {
				if (igralnaPlosca.vsa_polja[y+1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y+1][x] == 1) {
						poteze.add(new Poteza(x,y+1));
					}
				}	
			}
						
		}
		if (kvota == 3) {
			//preveriti je potrebno premikanje za eno mesto levo, desno, gor, dol z dvema ograjama. 
			//premik levo: 
			if ( x-1 >= 0) {
				if (igralnaPlosca.vsa_polja[y][x-1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x] == 2) {
						poteze.add(new Poteza(x-1,y));
					}
				}
			}
			
			//premik desno: 
			if ( x+1 < dim) {
				if (igralnaPlosca.vsa_polja[y][x+1] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceNavp[y][x+1] == 2) {
						poteze.add(new Poteza(x+1,y));
					}
				}
			}
			
			//premik gor: 
			if ( y-1 >= 0) {
				if (igralnaPlosca.vsa_polja[y-1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y][x] == 2) {
						poteze.add(new Poteza(x,y-1));
					}
				}	
			}
			
			//premik dol: 
			if ( y+1 < dim) {
				if (igralnaPlosca.vsa_polja[y+1][x] == Polje.PRAZNO) {
					if (igralnaPlosca.ograjiceVod[y+1][x] == 2) {
						poteze.add(new Poteza(x,y+1));
					}
				}	
			}
					
		}
				
		return poteze;
	}
	/**
	 * 
	 * @param trenutna - trenutna pozicija
	 * @param zeljena - izbrano polje za premik figurice
	 * @return ali lahko ibrano figurico igralec premakne na željeno polje
	 */
	public boolean veljavnaPoteza(Poteza trenutna, Poteza zeljena) {
		List<Poteza> mozne = moznePoteze(trenutna.getX(), trenutna.getY(), kvotaPremikov);
		return mozne.contains(zeljena);
	}

	public Plosca getIgralnaPlosca() {
		return igralnaPlosca;
	}

}
