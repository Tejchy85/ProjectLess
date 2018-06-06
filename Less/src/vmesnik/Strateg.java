package vmesnik;

import igra.Poteza;

public abstract class Strateg {
	/**
	 * Glavno okno kli�e to metodo, ko je strateg na potezi.
	 */
	public abstract void na_potezi();
	
	
	/**
	 * Strateg naj neha igrati potezo.
	 */
	public abstract void prekini();
	
	/**
	 * @param poteza
	 * 
	 * Glavno okno klice to metodo, ko uporabnik klikne na polje in doloci potezo.
	 */
	public abstract void klik(Poteza poteza);
	
	/*
	 * ce igra racunalnik, ne bomo mogli klikniti na polje, da bi se obarvalo
	 */
	public abstract boolean uporabljaGUI();
}