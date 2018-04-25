package igra;

public enum Igralec {
	// beli, èrni
	// 
	//odigraj potezo
	BELI, CRNI;
	
	
	public Igralec nasprotnik() {
		if (this == BELI) {
			return CRNI;
		} else {
			return BELI;
		}
	}
	

	
}
