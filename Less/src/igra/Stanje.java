package igra;

public enum Stanje {

	// Je konec (zmaga beli, zmaga �rbi, neodlo�eno), ni konec (kdo je na vrsti)
	// vsak igralec bo imel �tevec svojih potez, kajti rezultat je neodlo�en, �e sta oba postavila v nasprotni kot vse v enakem �tevilu potez.
	// koliko premikov ima posamezna figurica (1,2,3) - ali bi bilo bolj�e imeti objekt figurica? 
	
	//Kako bo plo��a povedala ali so vse figurice na kon�nih mestih? (Vpra�anje za Kristjana)
	
	BELI_NA_POTEZI, CRNI_NA_POTEZI, ZMAGA_CRNI, ZMAGA_BELI, NEODLOCENO;
	
	private int stevecPotezBeli; // na za�etku so �tevci potez v skupnem enaki 0, ob vsakem koraku se pove�ajo za 1,2,3. 
	private int stevecPotezCrni;
	private int kvotaPremikov;
	
	
	//public static Stanje 
	
}
