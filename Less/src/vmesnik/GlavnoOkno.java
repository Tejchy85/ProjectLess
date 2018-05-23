package vmesnik;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import igra.Igra;
import igra.Igralec;
import igra.Lokacija;
import igra.Plosca;
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
	 * Strateg, ki vleèe poteze belega.
	 */
	private Strateg strategB;

	/**
	 * Strateg, ki vleèe poteze crnega.
	 */
	private Strateg strategC;

	/**
	 * Logika igre, null èe se igra trenutno ne igra
	 */
	private Igra igra;
	
	/**
	 * JPanel, v katerega rišemo
	 */
	private IgralnoPolje polje;

	
	
	/**
	 * Konstruktor
	 */
	public GlavnoOkno() {
		this.setTitle("Less");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //ko kliknemo križec se zapre
		this.setLayout(new GridBagLayout());
		
		//menu: igra(nova igra taka kot je, nova igra dva igralca, dva raèunlnika..), izbira barve(figurice, polja in podobno)
		JMenuBar mb = new JMenuBar();
		JMenu igraMenu = new JMenu("Nova igra");
		mb.add(igraMenu);
		setJMenuBar(mb);
		
		//izbire v igra: 
		clovekClovek = new JMenuItem("Èlovek vs Èlovek");
		igraMenu.add(clovekClovek);
		clovekClovek.addActionListener(this);
		
		clovekRacunalnik = new JMenuItem("Èlovek vs Raèunalnik");
		igraMenu.add(clovekRacunalnik);
		clovekRacunalnik.addActionListener(this);
		
		racunalnikClovek = new JMenuItem("Raèunalnik vs Èlovek");
		igraMenu.add(racunalnikClovek);
		racunalnikClovek.addActionListener(this);
		
		racunalnikRacunalnik = new JMenuItem("Raèunalnik vs Raèunalnik");
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
				
		// statusna vrstica za sporoèila
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
		this.igra = new Igra();
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
	

	public void odigraj(Lokacija zacetna, Lokacija koncna) {
		igra.narediPotezo(zacetna, koncna);
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
			case NEODLOCENO: status.setText("Neodloèeno!"); break;
			}
		}
		polje.repaint();
	}
	
	public void klikniPolje(Lokacija zacetna, Lokacija koncna) {
		if (igra != null) {
			switch (igra.getTrenutnoStanje()) {
			case BELI_NA_POTEZI:
				strategB.klik(zacetna, koncna);
				break;
			case CRNI_NA_POTEZI:
				strategC.klik(zacetna, koncna);
				break;
			default:
				break;
			}
		}		
	}
	
	public LinkedList<Lokacija> getMozne(Lokacija p){
		int kvota = igra.getKvotaPremikov();
		return igra.moznePoteze(p, kvota);
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
	
	public int getDim() {
		return igra.dim;
	}
	
	public Stanje getStanje(){
		return igra.getTrenutnoStanje();
	}
}
