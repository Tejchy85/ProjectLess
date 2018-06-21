package inteligenca;

import javax.swing.SwingWorker;
import vmesnik.GlavnoOkno;
import igra.Igra;
import igra.Igralec;
import igra.Poteza;

public class MinimaxAlfaBeta extends SwingWorker<Poteza, Object> {

		/**
		 * Glavno okno, v katerem poteka ta igra
		 */
		private GlavnoOkno master;

		/**
		 * Globina, do katere pregleduje minimax
		 */
		private int globina;

		/**
		 * Ali racualnik igra Belega ali Crnega?
		 */
		private Igralec jaz; // koga igramo
		
		/**
		 * @param master glavno okno, v katerem vlecemo poteze
		 * @param globina koliko potez naprej gledamo
		 * @param jaz koga igramo
		 */
		public MinimaxAlfaBeta(GlavnoOkno master, int globina, Igralec jaz) {
			this.master = master;
			this.globina = globina;
			this.jaz = jaz;
		}
		
		@Override
		protected Poteza doInBackground() throws Exception {
			Igra igra = master.copyIgra();
			OcenjenaPoteza p = alfaBeta(0, igra, Integer.MIN_VALUE, Integer.MAX_VALUE);
			System.out.println(p);
			assert (p != null);
			return p.poteza;
		}
		
		@Override
		public void done() {
			try {
				Poteza p = this.get();
				if (p != null) { master.odigraj(p); }
				System.out.println("SteviloKorakovBeli = " + master.getIgra().getSteviloKorakovBeli() + " SteviloKorakoCrni = " + master.getIgra().getSteviloKorakovCrni());
			} catch (Exception e) {
			}
		}

		/**
		 * Z metodo minimax poisce najboljso potezo v dani igri.
		 * 
		 * @param k stevec globine, do kje smo ze preiskali 
		 * @param igra
		 * @return najboljsa poteza (ali null, ce bi igra bila zakljucena), skupaj z oceno najbolj�e poteze
		 */
		private OcenjenaPoteza alfaBeta(int k, Igra igra, int alfa, int beta) {
			Igralec naPotezi = null;
			// Ugotovimo, ali je konec, ali je kdo na potezi?
			switch (igra.getTrenutnoStanje()) {
			case BELI_NA_POTEZI: naPotezi = Igralec.BELI; break;
			case CRNI_NA_POTEZI: naPotezi = Igralec.CRNI; break;
			// Igre je konec, ne moremo vrniti poteze, vrnemo le vrednost pozicije
			case ZMAGA_CRNI:
				return new OcenjenaPoteza(
						null,
						(jaz == Igralec.CRNI ? Ocena2.ZMAGA-k-Ocena2.oceniPozicijo(jaz, igra) : Ocena2.ZGUBA+k+Ocena2.oceniPozicijo(jaz, igra)));
			case ZMAGA_BELI:
				return new OcenjenaPoteza(
						null,
						(jaz == Igralec.BELI ? Ocena2.ZMAGA-k -Ocena2.oceniPozicijo(jaz, igra) : Ocena2.ZGUBA+k+Ocena2.oceniPozicijo(jaz, igra)));
			case NEODLOCENO:

				
				return new OcenjenaPoteza(null, Ocena2.NEODLOCENO);
			}
			assert (naPotezi != null);
			// Nekdo je na potezi, ugotovimo, kaj se splaca igrati
			if (k >= globina) {
				// dosegli smo najvecjo dovoljeno globino, zato ne vrnemo poteze, ampak samo oceno pozicije
				return new OcenjenaPoteza(
						null,
						Ocena2.oceniPozicijo(jaz, igra));
			} 
		
			//Iscemo najboljso potezo
			Poteza najboljsa = null;
			int ocenaNajboljse = 0;
			for (Poteza p : igra.vsePoteze()) {
				// V kopiji igre odigramo potezo p
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.narediPotezo(p);
				
				// Izracunamo vrednost pozicije po odigrani potezi p
				int ocenaP = alfaBeta(
						(igra.getNaPotezi() == kopijaIgre.getNaPotezi() ? k : k+1), 
						kopijaIgre, alfa, beta).vrednost;
				// ce je p boljsa poteza, si jo zabelezimo
				if (najboljsa == null // ce nimamo kandidata za najboljso potezo
					|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
					|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
					) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
					//alfa-beta
					if (naPotezi == jaz) {
						alfa = Math.max(alfa, ocenaNajboljse);
					} else {
						beta = Math.min(beta, ocenaNajboljse);
					}
					if (beta <= alfa) {
						return new OcenjenaPoteza(null, ocenaNajboljse);
					}
				}
			}
			
			// Vrnemo najboljso najdeno potezo in njeno oceno
			assert (najboljsa != null);
			return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
		}
}
