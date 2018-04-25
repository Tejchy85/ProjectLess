package igra;

public class Ploscica {

	//Simuliramo plošèice, ki sestavljajo polje, kot je to v fizièni igri
	protected int[] ograjice;
	
	public int[] getOgrajice() {
		return ograjice;
	}

	public void setOgrajice(int[] ograjice) {
		this.ograjice = ograjice;
	}

	public Ploscica (int[] o){
		ograjice = o;
	}

	public void rotiraj (){
		for (int i=0; i < ograjice.length; i++){
			ograjice[i] = (ograjice[i] + 3) % 12;
		}
	}
	
	public void nakljucno_rotiraj(){
		int stevilo_rotacij = (int)(Math.random()*100) % 4;
		while (stevilo_rotacij > 0){
			rotiraj();
			stevilo_rotacij -= 1;
		}
	}
}
