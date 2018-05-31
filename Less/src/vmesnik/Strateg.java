package vmesnik;

import igra.Lokacija;
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
	 * @param i
	 * @param j
	 * 
	 * Glanvo okno kli�e to metodo, ko uporabnik klikne na polje (i,j).
	 */
	public abstract void klik(Poteza poteza);
	
	
	/*
	 * ce igra racunalnik, ne bomo mogli klikniti na polje, da bi se obarvalo
	 */
	public abstract boolean uporabljaGUI();
}