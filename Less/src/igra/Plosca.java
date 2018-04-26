package igra;

//Vedela kje so katere bele figurice in kje èrne. 
//Vedela bo, èe je igra že napol konèana(èe beli postavil vse na konèno mesto), èe je konèana (èrni postavil na konène mesto).


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
	 * @return ali je èrni postavil vse figure na ciljna polja in je igra konèana 
	 */
	public boolean konec() {
		//TODO
		return false;
	}
	/**
	 * 
	 * @return ali je beli postavil vse figure na ciljna polja in èe èrni v treh potezah ne konèa je zmagal beli
	 */
	public boolean skorajkonec() {
		//TODO
		return false;
	}
}


