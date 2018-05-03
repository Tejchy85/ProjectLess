package igra;

public class Lokacija {
	
	protected int x; //predstavlja stolpec 
	protected int y; //predstavlja vrstico

	public Lokacija(int x, int y) {
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
	
	//najin print, da lahko lazje sproti preverjava lokacijo v testih
	public void print() {
		System.out.print(x);
		System.out.println(y);
		
	}
	
	//metoda pravilno preveri, ali sta objekta enaka
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lokacija other = (Lokacija) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
