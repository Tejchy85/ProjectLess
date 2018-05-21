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
	
	/**TODO
	*preverjanje, da logika igre deluje pravilno
	*kaj se zgodi, ko nekdo zmaga, kaj se zgodi, ko je neodloceno
	*
	**/
	
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//vpršamo kako igro želi: ali bo igral z raèunalnikom ali z drugim igralcem in kdo bo beli in kdo èrni
		
	}
	
	public void novaIgra(Strateg beli, Strateg crni) {
		if (strategB != null) { strategB.prekini(); }
		if (strategC != null) { strategC.prekini(); }
		this.igra = new Igra();
		strategB = new Clovek(this, Igralec.BELI);
		strategC = new Racunalnik(this, Igralec.CRNI);
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
