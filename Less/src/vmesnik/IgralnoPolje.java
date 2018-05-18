package vmesnik;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;
import igra.Plosca;
import igra.Polje;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	//ta class bo narisal plosèo in figurice, ograjice, poslušal kam bo kdo kliknil, 
	//TODO polmer, debelina in stranica se prilagajajo glede na velikost vmesnika, ki jo je izbral uporabnik (sprotno racunanje)
	
	protected GlavnoOkno master;
	protected Color barvaFiguric;
	protected int polmer; //polmer figuric
	protected int stranica; //dolžina kvadratka, ki predstavlja polje
	protected Lokacija izbrana;
	protected int debelina; // predstavlja debelino crte na eni strani kvadratka
	
	/**
	 * konstruktor
	 * @param master
	 */
	public IgralnoPolje(GlavnoOkno master) {
		super(); //konstruktor od JPanel
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
		stranica = 100;
		debelina = 1;
		polmer = 5;
	}
	
	/**
	 * Velikost panela vsakiè ko odpremo okno.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((master.getDim() + 1) * stranica, (master.getDim() + 1) *stranica);
	}
	
	/*private void narisiPlosco(Graphics g, Plosca plosca) { //to briseva
		//narisemo plosco z ograjicami.
		//narišemo ogrodje iz èrt in èrte po sredini. 
		int stPolj = plosca.getDim();
		
		//imamo kvadratno polje
		int dolzinaCrte = stPolj * stranica;
		
		//potrebno postaviti navpiène in vodoravne èrte in tako dovimo plošèo.
	} */
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int dim = master.getDim();

		double w = (double) stranica;
		
		// èrte
		g2.setColor(Color.magenta);
		for (int i = 0; i < dim + 1; i++) {
			g2.drawLine(0, i * stranica, stranica * dim, i * stranica);
			g2.drawLine(i * stranica, 0, i * stranica, stranica * dim);
		}
			
		// krozci in krozci
		Plosca plosca = master.getPlosca();
		if (plosca != null) {
			for (int i = 0; i < Igra.dim; i++) {
				for (int j = 0; j < Igra.dim; j++) {
					switch(plosca.getVsa_polja()[i][j]) {
					case BELO: narisiFigurico(g2, Igralec.BELI, i, j); break;
					case CRNO: narisiFigurico(g2, Igralec.CRNI, i, j); break;
					default: break;
					}
				}
			}
		}
	
	}
	
	private void narisiFigurico(Graphics g, Igralec igralec, int vrstica, int stolpec ) {
		//narisemo figurico na doloceno polje dolocene barve
		switch (igralec) {
		case CRNI: barvaFiguric = Color.BLACK; break;
		case BELI: barvaFiguric = Color.GREEN; break;
		}
		g.setColor(barvaFiguric);
		//double s = (double) stranica; pomozno za lepse risanje
		g.drawOval(stolpec * stranica , vrstica * stranica, stranica, stranica);
		//Verjetno bo treba kej popravlat, da se bo centriralo.
	}
	
	private void pobarvajMozne(Graphics g, Lokacija p){
		LinkedList<Lokacija> mozne = master.getMozne(p);
		for (Lokacija l : mozne){
			pobarvaj(l);
		}
	}

	private void pobarvaj(Lokacija l) {
		//TODO		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//todo racunanje, kje je clovek kliknil
		Lokacija lokacija = null;
		
		int x = e.getX();
		int y = e.getY();
		System.out.println("kliknil si" + x + y);
		if (!(x % stranica < debelina || stranica - x % stranica < debelina)){ //pogoj: nisi kliknil na ograjico		
			int i = x / stranica;
			int j = y /stranica;
			lokacija = new Lokacija(i,j);
		}
		
		
		if (lokacija != null){
			if (izbrana == null){
				izbrana = lokacija;
			} else{
				LinkedList<Lokacija> mozne = master.getMozne(izbrana);
				if (mozne.contains(lokacija)){
					master.klikniPolje(izbrana, lokacija);
				}
			}
		}		
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
