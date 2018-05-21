package inteligenca;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import vmesnik.GlavnoOkno;
import igra.Igra;
import igra.Igralec;
import igra.Lokacija;


public class Nakljucno extends SwingWorker<Lokacija, Object> {

		private GlavnoOkno master;
		private Lokacija izbrana;
		
		public Nakljucno(GlavnoOkno master) {
			this.master = master;
			izbrana = null;
		}
		
		@Override
		protected Lokacija doInBackground() throws Exception {
			Igra igra = master.copyIgra();
			Thread.sleep(500);
			Random generator = new Random();
			Lokacija[] figurice = new Lokacija[4];
			if (igra.getNaPotezi() == Igralec.BELI){
				figurice = igra.getIgralnaPlosca().belaPolja();
			} else{
				figurice = igra.getIgralnaPlosca().crnaPolja();
			}
			int r = generator.nextInt(4);
			izbrana = figurice[r];
			List<Lokacija> poteze = igra.moznePoteze(izbrana, igra.getKvotaPremikov());
			int q = generator.nextInt(poteze.size());
			return poteze.get(q);
		}
		@Override
		public void done() {
			try {
				Lokacija q = this.get();
				if (q != null) { master.odigraj(izbrana, q); }
			} catch (Exception e) {
			}
		}

	}
