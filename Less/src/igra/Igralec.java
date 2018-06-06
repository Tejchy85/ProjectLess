package igra;

public enum Igralec {
	// beli, crni
	
	BELI, CRNI;
	
	public Igralec nasprotnik() {
		if (this == BELI) {
			return CRNI;
		} else {
			return BELI;
		}
	}

	

	
}
