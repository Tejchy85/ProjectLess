package vmesnik;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;
import igra.Plosca;
import igra.Poteza;
import igra.Stanje;


@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	
	
	protected JLabel status; 
	
	/**
	 * Izbire v menuju
	 */
	protected JMenuItem clovekClovek;
	protected JMenuItem clovekRacunalnik;
	protected JMenuItem racunalnikClovek;
	protected JMenuItem racunalnikRacunalnik;
	
	
	
	/**
	 * Strateg, ki vlece poteze belega.
	 */
	private Strateg strategB;

	/**
	 * Strateg, ki vlece poteze crnega.
	 */
	private Strateg strategC;

	/**
	 * Logika igre, null ce se igra trenutno ne igra
	 */
	private static Igra igra;
	
	/**
	 * JPanel, v katerega risemo
	 */
	private IgralnoPolje polje;

	
	
	/**
	 * Konstruktor
	 */
	public GlavnoOkno() {
		this.setTitle("Less");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //ko kliknemo krizec se zapre
		this.setLayout(new GridBagLayout());
		
		//menu: igra(nova igra taka kot je, nova igra dva igralca, dva ra�unlnika..), izbira barve(figurice, polja in podobno)
		JMenuBar mb = new JMenuBar();
		JMenu igraMenu = new JMenu("Nova igra");
		mb.add(igraMenu);
		setJMenuBar(mb);
		
		//izbire v igra: 
		clovekClovek = new JMenuItem("Clovek vs Clovek");
		igraMenu.add(clovekClovek);
		clovekClovek.addActionListener(this);
		
		clovekRacunalnik = new JMenuItem("Clvoek vs Racunalnik");
		igraMenu.add(clovekRacunalnik);
		clovekRacunalnik.addActionListener(this);
		
		racunalnikClovek = new JMenuItem("Racunalnik vs Clovek");
		igraMenu.add(racunalnikClovek);
		racunalnikClovek.addActionListener(this);
		
		racunalnikRacunalnik = new JMenuItem("Racunalnik vs Racunalnik");
		igraMenu.add(racunalnikRacunalnik);
		racunalnikRacunalnik.addActionListener(this);
		
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
				
		// statusna vrstica za sporocila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		novaIgra(new Clovek(this, Igralec.BELI), new Clovek(this, Igralec.CRNI)); // za zacetek clovek proti cloveku
	
	}

	/**
	 * Kaj se zgodi ko se izbere v meniju novo igro.
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clovekRacunalnik) {
			novaIgra(new Clovek(this, Igralec.BELI),
					  new Racunalnik(this, Igralec.CRNI));
		}
		else if (e.getSource() == racunalnikClovek) {
			novaIgra(new Racunalnik(this, Igralec.BELI),
					  new Clovek(this, Igralec.CRNI));
		}
		else if (e.getSource() == racunalnikRacunalnik) {
			novaIgra(new Racunalnik(this, Igralec.BELI),
					  new Racunalnik(this, Igralec.CRNI));
		}
		else if (e.getSource() == clovekClovek) {
			novaIgra(new Clovek(this, Igralec.BELI),
			          new Clovek(this, Igralec.CRNI));
		}
		
	}
	
	public void novaIgra(Strateg beli, Strateg crni) {
		if (strategB != null) { strategB.prekini(); }
		if (strategC != null) { strategC.prekini(); }
		GlavnoOkno.igra = new Igra();
		strategB = beli;
		strategC = crni;
		// Tistemu, ki je na potezi, to povemo
		switch (igra.getTrenutnoStanje()) {
		case BELI_NA_POTEZI: strategB.na_potezi(); break;
		case CRNI_NA_POTEZI: strategC.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
}
	

	public void odigraj(Poteza poteza) {
		igra.narediPotezo(poteza); 
		osveziGUI();
		switch (igra.getTrenutnoStanje()) {
		case BELI_NA_POTEZI: strategB.na_potezi(); break;
		case CRNI_NA_POTEZI: strategC.na_potezi(); break;
		default: break;
		}
	}
	
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.getTrenutnoStanje()) {
			case BELI_NA_POTEZI: status.setText("Na potezi je beli. " + "Kvota: " + igra.getKvotaPremikov()); break;
			case CRNI_NA_POTEZI: status.setText("Na potezi je crni. " + "Kvota: " + igra.getKvotaPremikov()); break;
			case ZMAGA_BELI: status.setText("Zmagal je beli"); break;
			case ZMAGA_CRNI: status.setText("Zmagal je crni"); break;
			case NEODLOCENO: status.setText("Neodloceno!"); break;
			}
		}
		polje.repaint();
	}
	
	public void klikniPolje(Poteza poteza) {
		if (igra != null) {
			switch (igra.getTrenutnoStanje()) {
			case BELI_NA_POTEZI:
				strategB.klik(poteza);
				break;
			case CRNI_NA_POTEZI:
				strategC.klik(poteza);
				break;
			default:
				break;
			}
		}		
	}
	

	//izracuna mozne poteze za eno figurico
	public static LinkedList<Poteza> getMozne(Lokacija p){
		LinkedList<Poteza> mozne = igra.moznePoteze(p, igra.getKvotaPremikov());
		return mozne;
	}
	
	
	//mogoce ta metoda spada v igro
	public static List<Poteza> vseMoznePoteze(Igralec igralec) {
		//System.out.println("imamo vse poteze!");
		Lokacija[] figurice = new Lokacija[4];
		
		if (igra.getNaPotezi() == Igralec.BELI){
			figurice = igra.getIgralnaPlosca().belaPolja();
		} else{
			figurice = igra.getIgralnaPlosca().crnaPolja();
		}
			
		List<Poteza> poteze = new LinkedList<Poteza>();
		
		for (Lokacija l : figurice) {
			List<Poteza> pot = getMozne(l);
			for (Poteza p : pot) {
				poteze.add(p);
			}
		}
		return poteze;
	}
	
	public boolean veljavna (Poteza poteza) {
		return igra.veljavnaPoteza(poteza);
	}


	/**
	 * @return kopija trenutne igre
	 */
	public Igra copyIgra() {
		return new Igra(igra);
}
	
	public Plosca getPlosca() {
		return igra.getIgralnaPlosca();
	}
	
	public Stanje getStanje(){
		return igra.getTrenutnoStanje();
	}
	
	public int getDim() {
		return Igra.DIM;
	}
	
	public Strateg getStrateg() {
		if (igra.getTrenutnoStanje() == Stanje.BELI_NA_POTEZI){
			return strategB;
		} else {
			return strategC;
		}
	}

	public Igra getIgra() {
		return igra;
	}
}
