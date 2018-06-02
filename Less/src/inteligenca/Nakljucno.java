package inteligenca;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import vmesnik.GlavnoOkno;
import igra.Poteza;


public class Nakljucno extends SwingWorker<Poteza, Object> {

		private GlavnoOkno master;
		public Nakljucno(GlavnoOkno master) {
			this.master = master;
		}
		
		@Override
		protected Poteza doInBackground() throws Exception {
			Thread.sleep(500);
			Random generator = new Random();
			List<Poteza> poteze = GlavnoOkno.vseMoznePoteze(master.getIgra().getNaPotezi());
			int q = generator.nextInt(poteze.size());		
			return poteze.get(q);
		}

		@Override
		public void done() {
			try {
				Poteza poteza = this.get();
				if (poteza != null) { master.odigraj(poteza); }
			} catch (Exception e) {
			}
		}

	}
