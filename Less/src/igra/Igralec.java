package igra;

public enum Igralec {
	// beli, èrni, rezultat (beli zmaga, èrni zmaga, neodloèeno)
	//vsak igralec bo imel števec svojih potez, kajti rezultat je neodloèen, èe sta oba postavila v nasprotni kot vse v enakem številu potez. 
	BELI, CRNI;
	
	private int StevecPotezBeli; // na zaèetku so števci potez v skupnem enaki 0, ob vsakem koraku se poveèajo za 1,2,3. 
	private int StevecPotezCrni;
	
	public Igralec nasprotnik() {
		if (this == BELI) {
			return CRNI;
		} else {
			return BELI;
		}
	}

	public int getStevecPotezBeli() {
		return StevecPotezBeli;
	}

	public void setStevecPotezBeli(int stevecPotezBeli) {
		StevecPotezBeli = stevecPotezBeli;
	}

	public int getStevecPotezCrni() {
		return StevecPotezCrni;
	}

	public void setStevecPotezCrni(int stevecPotezCrni) {
		StevecPotezCrni = stevecPotezCrni;
	}
	
	
}
