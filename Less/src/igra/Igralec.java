package igra;

public enum Igralec {
	// beli, �rni
	
	BELI, CRNI;
	
	public Igralec nasprotnik() {
		if (this == BELI) {
			return CRNI;
		} else {
			return BELI;
		}
	}

	

	
}
