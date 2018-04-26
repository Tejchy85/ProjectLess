package igra;

public enum Stanje {

	// Je konec (zmaga beli, zmaga èrbi, neodloèeno), ni konec (kdo je na vrsti)
	// vsak igralec bo imel števec svojih potez, kajti rezultat je neodloèen, èe sta oba postavila v nasprotni kot vse v enakem številu potez.
	// koliko premikov ima posamezna figurica (1,2,3) - ali bi bilo boljše imeti objekt figurica? 
	
	//Kako bo plošèa povedala ali so vse figurice na konènih mestih? (Vprašanje za Kristjana)
	
	BELI_NA_POTEZI, CRNI_NA_POTEZI, ZMAGA_CRNI, ZMAGA_BELI, NEODLOCENO;
	
	private int stevecPotezBeli; // na zaèetku so števci potez v skupnem enaki 0, ob vsakem koraku se poveèajo za 1,2,3. 
	private int stevecPotezCrni;
	private int kvotaPremikov;
	
	
	//public static Stanje 
	
}
