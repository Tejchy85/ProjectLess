package vmesnik;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;
import igra.Plosca;
import igra.Polje;
import igra.Poteza;
import igra.Stanje;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	//ta class bo narisal plos�o in figurice, ograjice, poslu�al kam bo kdo kliknil, 
	//TODO polmer, debelina in stranica se prilagajajo glede na velikost vmesnika, ki jo je izbral uporabnik (sprotno racunanje)
	
	protected GlavnoOkno master;
	protected Color barvaFiguric;
	protected int polmer; //polmer figuric
	protected int stranica; //dolzina kvadratka, ki predstavlja polje
	protected Lokacija izbrana; //v tem razredu potrebujemo izbrano lokacijo, saj jo bomo obarvali in narisali njene mozne premike
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
		debelina = stranica / 8;
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

		
		// crte
		g2.setColor(Color.BLACK);
		for (int i = 0; i < dim + 1; i++) {
			g2.drawLine(0, i * stranica, stranica * dim, i * stranica);
			g2.drawLine(i * stranica, 0, i * stranica, stranica * dim);
		}
			
		// krozci in krozci
		Plosca plosca = master.getPlosca();
		if (plosca != null) {
			for (int i = 0; i < Igra.DIM; i++) {
				for (int j = 0; j < Igra.DIM; j++) {
					switch(plosca.getVsa_polja()[i][j]) {
					case BELO: narisiFigurico(g2, Igralec.BELI, i, j); break;
					case CRNO: narisiFigurico(g2, Igralec.CRNI, i, j); break;
					default: break;
					}
				}
			}
		}
		
		//drugace obarvaj izbrano:
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
		int polmer = stranica / 2;
		
		if (igralec == Igralec.CRNI) {
			barvaFiguric = new Color(50,50,50);
		} else if (igralec == Igralec.BELI) {
			barvaFiguric = new Color(250,250,250);
		} else{ 										// to je ko je igralec null - za izbrano
			barvaFiguric = Color.BLUE;
		}
		g.setColor(barvaFiguric);
		g.fillOval(stolpec * stranica + polmer /2, vrstica * stranica + polmer/2, polmer, polmer);
		if(igralec == Igralec.BELI) {
			g.setColor(Color.BLACK);
			g.drawOval(stolpec * stranica + polmer /2, vrstica * stranica + polmer/2, polmer, polmer);
		}
	}
	
	private void pobarvajMozne(Graphics g, Lokacija p){
		java.util.List<Poteza> mozne = GlavnoOkno.getMozne(p);
		g.setColor(Color.cyan);
		for (Poteza l : mozne){
			g.fillRect(l.getKoncna().getX()*stranica, l.getKoncna().getY()*stranica, stranica, stranica);
		}
	}
	
	private void narisiOgrajice(Graphics g, int[][] vodoravne, int [][] navpicne) {
		for (int i = 0; i < navpicne.length;i++) {
			 int[] vrstica = navpicne[i];
			 for (int j = 0; j < vrstica.length; j++) {
				 int stOgrajic = vrstica[j];
				 if(stOgrajic != 0) {
					 if(stOgrajic == 1) {
						 g.setColor(Color.BLUE); 
						 g.fillRect(j*stranica - debelina / 2, i*stranica, debelina, stranica);
					 } if(stOgrajic == 2) {
						 g.setColor(Color.BLUE); 
						 g.fillRect(j*stranica - debelina / 2, i*stranica, debelina, stranica);
						 g.setColor(Color.RED);
						 g.fillRect(j*stranica - debelina / 4, i*stranica, debelina / 2, stranica);
					 }
				 }
			 }
		}
		
		for (int i = 0; i < vodoravne.length;i++) {
			 int[] vrstica = vodoravne[i];
			 for (int j = 0; j < vrstica.length; j++) {
				 int stOgrajic = vrstica[j];
				 if(stOgrajic == 1) {
					 g.setColor(Color.BLUE); 
					 g.fillRect(j*stranica, i*stranica - debelina / 2, stranica, debelina);
				 } else if(stOgrajic == 2) {
					 g.setColor(Color.BLUE); 
					 g.fillRect(j*stranica, i*stranica - debelina / 2, stranica, debelina);	 
					 g.setColor(Color.RED);
					 g.fillRect(j*stranica, i*stranica - debelina / 4, stranica, debelina / 2
							 );
				 }
			 }
		 }
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (master.getStrateg().uporabljaGUI()) {
			Lokacija lokacija = null;
			
			int x = e.getX();
			int y = e.getY();
			int i = x / stranica;
			int j = y /stranica;
			System.out.println("kliknil si " + x + " " + y);
			
			if (!(x % stranica < debelina || stranica - x % stranica < debelina) &&
				!(y% stranica < debelina || stranica - y % stranica < debelina)	){									//pogoj1: nisi kliknil na ograjico	
				lokacija = new Lokacija(i,j);
			}
			if (lokacija != null){
				System.out.println("izbrana je" + izbrana);
				if (izbrana == null && ((master.getStanje() == Stanje.BELI_NA_POTEZI && master.getPlosca().getVsa_polja()[j][i] == Polje.BELO) //pogoj2: kliknil si na svojo figuro
						|| (master.getStanje() == Stanje.CRNI_NA_POTEZI && master.getPlosca().getVsa_polja()[j][i] == Polje.CRNO))) {
						izbrana = lokacija;
						System.out.println("izbral sem jo");
				} else if (izbrana != null) {
					if (master.veljavna(new Poteza(izbrana, lokacija))){
						System.out.println("zelim naredit potezo, pa ne gre :(");
						master.klikniPolje(new Poteza(izbrana, lokacija));
						}
					izbrana = null;
				}
			}
			repaint();
		}
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
