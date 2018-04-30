package igra;

import java.util.ArrayList;

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
		
	Ploscica[] seznamOgrajic = {												//nardiva to global al jo samo lepo spodaj uporabiva in je to to? lahko pac dodava argument v spodnjo funkcijo
								new Ploscica(new int[] {2,3,6}),				//pa kdaj se bo ta metoda klicala? v resnici zgolj enkrat
	                            new Ploscica(new int[] {2,3,4}),
	                            new Ploscica(new int[] {2,3,6}),
	                            new Ploscica(new int[] {2,3}),
	                            new Ploscica(new int[] {0,1}),
	                            new Ploscica(new int[] {0,1}),
	                            new Ploscica(new int[] {1}),
	                            new Ploscica(new int[] {2,4}),
	                            new Ploscica(new int[] {0,1}),
	                            new Ploscica(new int[] {2,3}),
	                            new Ploscica(new int[] {0,3}),
	                            new Ploscica(new int[] {0,3})
	                            
	};
	
	private void sestaviPlosco () {
		int i = 0;
		int j = 0;
		int ploscic = 0;
		ArrayList<Integer> izbrane = new ArrayList<Integer>(10);
		while (i < dim && j < dim && ploscic < 10) {
			int st = (int) Math.floor(Math.random()*12);
			while (izbrane.contains(st)) {
				st = (int) Math.floor(Math.random()*12); 		//iz seznama ploscic izberemo eno
			}
			
			Ploscica pl = seznamOgrajic[st];
			pl.nakljucno_rotiraj();
			for (int k : pl.ograjice) { 						//pogledamo vsako ograjico posebej
				
				if (k%2==1) { 									//dodajamo navpicne ograjice
					if (k < 6) { 								//prva vrstica na ploscici
						ograjice_navp[i][j + (5-k)%2]++;
					} else { 									//druga vrstica
						ograjice_navp[i+1][j + (k-7)%2]++;
					}
				
				} else {										 //dodajamo vodoravne ograjice
					if (3 < k && k < 10) {
						ograjice_navp[i + (k-4)%2][j]++;
					} else {
						if (k==10) {
						ograjice_navp[i + 2][j+1]++;
						}
						if (k==2) {
							ograjice_navp[i][j+1]++;
							}
						if (k==0) {
							ograjice_navp[i + 1][j+1]++;
							}
					}
						
				}
			}
			i = ploscic / 3;
			j = (ploscic % 4) * 2;
			ploscic++;

		}
	}
	
	/**
	 * 
	 * @return ali je èrni postavil vse figure na ciljna polja in je igra konèana 
	 */
	public boolean konec() {
		
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


