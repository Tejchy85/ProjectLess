package igra;

//Vedela kje so katere bele figurice in kje �rne. 
//Vedela bo, �e je igra �e napol kon�ana(�e beli postavil vse na kon�no mesto), �e je kon�ana (�rni postavil na kon�ne mesto).


public class Plosca {
	protected int dim; //plosca je vselej kvadratna, zato potrebujemo le eno stevilo
	protected Polje[][] vsa_polja;	
	protected int[][] ograjice_vod;
	protected int[][] ograjice_navp;
	
	public Plosca(int dimenzija) {
		dim = dimenzija;
		vsa_polja = new Polje[dim][dim];
		ograjice_vod = new int[dim][dim+1]; //mozne napake: ravno obratno
		ograjice_navp = new int[dim+1][dim];
				
		//poklicemo zacetno postavitev figuric na plosco - beli v zgornji levi kot, crni v spodnji desni
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i < 2 && j < 2) {
					vsa_polja[i][j] = Polje.BELO;
				} else if (i > dim-3 && j > dim-3) {
					vsa_polja[i][j] = Polje.CRNO;
				} else {
					vsa_polja[i][j] = Polje.PRAZNO;
				}
			}
		}

	//s pomocjo malih ploscic sestavimo veliko plosco - predvsem nakljucno postavimo ograjice
	
	}
	
	/**
	 * 
	 * @return ali je �rni postavil vse figure na ciljna polja in je igra kon�ana 
	 */
	public boolean konec() {
		//TODO
		return false;
	}
	/**
	 * 
	 * @return ali je beli postavil vse figure na ciljna polja in �e �rni v treh potezah ne kon�a je zmagal beli
	 */
	public boolean skorajkonec() {
		//TODO
		return false;
	}
}


