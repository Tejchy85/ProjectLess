package vmesnik;

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
	//ta class bo narisal plos�o in figurice, ograjice, poslu�al kam bo kdo kliknil, 
	//TODO polmer, debelina in stranica se prilagajajo glede na velikost vmesnika, ki jo je izbral uporabnik (sprotno racunanje)
	
	protected GlavnoOkno master;
	protected Color barvaFiguric;
	protected int polmer; //polmer figuric
	protected int stranica; //dol�ina kvadratka, ki predstavlja polje
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
	 * Velikost panela vsaki� ko odpremo okno.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((master.getDim() + 1) * stranica, (master.getDim() + 1) *stranica);
	}
	
		
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int dim = master.getDim();

		
		// �rte
		g2.setColor(Color.BLACK);
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
		
		//druga�e obarvaj izbrano:
		if (izbrana != null) {
			narisiFigurico(g2, null, izbrana.getY(), izbrana.getX());
			pobarvajMozne(g2, izbrana);
		}
		
		//narisemo ograjice:
		narisiOgrajice(g2, master.getPlosca().getOgrajiceVod(), master.getPlosca().getOgrajiceNavp());
		master.osveziGUI();
	
	}
	
	private void narisiFigurico(Graphics g, Igralec igralec, int vrstica, int stolpec ) {
		//narisemo figurico na doloceno polje dolocene barve
		if (igralec == Igralec.CRNI) {
			barvaFiguric = Color.BLACK;
		} else if (igralec == Igralec.BELI) {
			barvaFiguric = Color.GREEN;
		} else { // to je ko je igralec null - za izbrano
			barvaFiguric = Color.BLUE;
		}
		g.setColor(barvaFiguric);
		//double s = (double) stranica; pomozno za lepse risanje
		g.drawOval(stolpec * stranica , vrstica * stranica, stranica, stranica);
	}
	
	private void pobarvajMozne(Graphics g, Lokacija p){
		LinkedList<Lokacija> mozne = master.getMozne(p);
		g.setColor(Color.cyan);
		for (Lokacija l : mozne){
			g.fillRect(l.getX()*stranica, l.getY()*stranica, stranica, stranica);
		}
	}
	
	private void narisiOgrajice(Graphics g, int[][] vodoravne, int [][] navpicne) {
		for (int i = 0; i < navpicne.length;i++) {
			 int[] vrstica = navpicne[i];
			 for (int j = 0; j < vrstica.length; j++) {
				 int stOgrajic = vrstica[j];
				 if (stOgrajic == 0) {
					 g.setColor(Color.BLACK);
				 } else if(stOgrajic == 1) {
					 g.setColor(Color.RED); 
				 } else {
					 g.setColor(Color.BLUE);
				 }
				 g.drawLine(j*stranica*stOgrajic, i*stranica, j*stranica*stOgrajic, i*stranica+stranica);
			 }
		}
		
		for (int i = 0; i < vodoravne.length;i++) {
			 int[] vrstica = vodoravne[i];
			 for (int j = 0; j < vrstica.length; j++) {
				 int stOgrajic = vrstica[j];
				 if (stOgrajic == 0) {
					 g.setColor(Color.BLACK);
				 } else if(stOgrajic == 1) {
					 g.setColor(Color.RED); 
				 } else if (stOgrajic == 2) {
					 g.setColor(Color.BLUE);
				 }
				 g.drawLine(j*stranica, i*stranica*stOgrajic, j*stranica + stranica, i*stranica*stOgrajic);
			 }
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		//todo racunanje, kje je clovek kliknil
		Lokacija lokacija = null;
		
		int x = e.getX();
		int y = e.getY();
		System.out.println("kliknil si " + x + " " + y);
		if (!(x % stranica < debelina || stranica - x % stranica < debelina)){ //pogoj: nisi kliknil na ograjico		
			int i = x / stranica;
			int j = y /stranica;
			lokacija = new Lokacija(i,j);
		}
		System.out.println("izbrana je" + izbrana);
		System.out.println("Lokacija je " + lokacija.getX() + " " + lokacija.getY());
		
		if (lokacija != null){
			if (izbrana == null){
				izbrana = lokacija;
			} else if (izbrana.equals(lokacija)) {
					izbrana = null;
			} else{
				LinkedList<Lokacija> mozne = master.getMozne(izbrana);
				if (mozne.contains(lokacija)){
					master.klikniPolje(izbrana, lokacija);
					izbrana = null;
				}
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}
	
	
	

}
