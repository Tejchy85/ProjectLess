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
	
	public void print() {
		System.out.print(x);
		System.out.println(y);
		
	}
	
	
}
