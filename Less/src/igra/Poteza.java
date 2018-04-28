package igra;

public class Poteza {

	// Vedela bo, kje si, kje so vsi ostali,
	//imela bo funkcijo, ki bo preverila, èe je izbrana poteza veljavna, neveljavna. 
	//Ena poteza bo dejansko samo en premik, ki pa je lahko vreden 1,2,3. Recimo da je beli prviè na potezi - ima še 3 korake.
	//Poteza bo bila premik ene figurice. Recimo da preskoèi eno svojo figurico. s tem je porabil 1 korak, zato je zopet na potezi, le da ima samo 2 koraka.
	
	protected int x; 
	protected int y; 
	
	public Poteza(int x, int y) {
		this.x = x; 
		this.y = y; 
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
