package igra;

public enum Igralec {
	// beli, �rni, rezultat (beli zmaga, �rni zmaga, neodlo�eno)
	//vsak igralec bo imel �tevec svojih potez, kajti rezultat je neodlo�en, �e sta oba postavila v nasprotni kot vse v enakem �tevilu potez. 
	BELI, CRNI;
	
	private int StevecPotezBeli; // na za�etku so �tevci potez v skupnem enaki 0, ob vsakem koraku se pove�ajo za 1,2,3. 
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
