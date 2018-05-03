package igra;

public enum Igralec {
	// beli, èrni
	
	BELI, CRNI;
	
	public Igralec nasprotnik() {
		if (this == BELI) {
			return CRNI;
		} else {
			return BELI;
		}
	}

	

	
}
