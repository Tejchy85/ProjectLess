package igra;

public enum Stanje {
	
	BELI_NA_POTEZI, CRNI_NA_POTEZI, ZMAGA_CRNI, ZMAGA_BELI, NEODLOCENO;
	
	
	public Stanje zamenjaj() {
		if(this == BELI_NA_POTEZI) {
			return CRNI_NA_POTEZI;
		} else { 
			return BELI_NA_POTEZI;			
		}
	}
	
}
