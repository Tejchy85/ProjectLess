package umesnik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import igra.Igralec;
import igra.Plosca;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	//ta class bo narisal plosèo in figurice, ograjice, poslušal kam bo kdo kliknil, 
	
	protected GlavnoOkno master;
	protected Color barvaFiguric;
	protected int polmer; //polmer figuric
	protected int stranica; //dolžina kvadratka, ki predstavlja polje
	
	/**
	 * konstruktor
	 * @param master
	 */
	public IgralnoPolje(GlavnoOkno master) {
		super(); //konstruktor od JPanel
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
		
	}
	
	/**
	 * Velikost panela vsakiè ko odpremo okno.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500,500);
	}
	
	private void narisiPlosco(Graphics g, Plosca plosca) {
		//narisemo plosco z ograjicami.
		//narišemo ogrodje iz èrt in èrte po sredini. 
		int steviloKvadratkovVVrstici = plosca.getVsa_polja().length;
		//imamo kvadratno polje
		int dolzinaCrte = steviloKvadratkovVVrstici * stranica;
		//potrebno postaviti navpiène in vodoravne èrte in tako dovimo plošèo.
		//Mogoèe bi bilo boljše de narišemo vsak kvadratek posebej
	}
	
	private void narisiFigurico(Graphics g, Igralec igralec, int vrstica, int stolpec ) {
		//narisemo figurico na doloceno polje dolocene barve
		switch (igralec) {
		case CRNI: barvaFiguric = Color.BLACK; break;
		case BELI: barvaFiguric = Color.WHITE; break;
		}
		g.setColor(barvaFiguric);
		g.drawOval(stolpec * stranica, vrstica * stranica, polmer, polmer);
		//Verjetno bo treba kej popravlat, da se bo centriralo.
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
