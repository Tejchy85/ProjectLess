package umesnik;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	
	
}
