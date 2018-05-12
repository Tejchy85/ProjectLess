package vmesnik;

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
import igra.Lokacija;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	
	/**
	 * Okence, kjer bo pisalo, kdo je na vrsti, koliko kvote se ima, ter ali je že kdo zmagal
	 */
	protected JLabel status; 
	
	/**
	 * Izbire v menuju
	 */
	protected JMenuItem novaIgra;
	
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
		JMenu igraMenu = new JMenu("Igra");
		mb.add(igraMenu);
		setJMenuBar(mb);
		
		//izbire v igra: 
		novaIgra = new JMenuItem("Nova igra");
		igraMenu.add(novaIgra);
		novaIgra.addActionListener(this);
		
		
	}

	/**
	 * Kaj se zgodi ko se izbere v meniju novo igro.
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//vpršamo kako igro želi: ali bo igral z raèunalnikom ali z drugim igralcem in kdo bo beli in kdo èrni
		
	}
	
	public void nova_igra() {
		if (strategB != null) { strategB.prekini(); }
		if (strategC != null) { strategC.prekini(); }
		this.igra = new Igra();
		strategB = new Clovek(this);
		strategC = new Racunalnik(this);
		// Tistemu, ki je na potezi, to povemo
		switch (igra.getTrenutnoStanje()) {
		case BELI_NA_POTEZI: strategB.na_potezi(); break;
		case CRNI_NA_POTEZI: strategC.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
}
	

	public void odigraj(Lokacija p) {
		igra.narediPotezo(trenutna, koncna);
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
			case BELI_NA_POTEZI: status.setText("Na potezi je beli"); break;
			case CRNI_NA_POTEZI: status.setText("Na potezi je crni"); break;
			case ZMAGA_BELI: status.setText("Zmagal je beli"); break;
			case ZMAGA_CRNI: status.setText("Zmagal je crni"); break;
			case NEODLOCENO: status.setText("Neodloèeno!"); break;
			}
		}
		polje.repaint();
	}
	
	public void klikniPolje(Lokacija p) {
		if (igra != null) {
			switch (igra.getTrenutnoStanje()) {
			case BELI_NA_POTEZI:
				strategB.klik(p);
				break;
			case CRNI_NA_POTEZI:
				strategC.klik(p);
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
}
