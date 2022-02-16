package igra2;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Polje extends Canvas {
	private Mreza mreza;
	public Polje(Mreza mreza) {
		super();
		this.mreza = mreza;
		dodajOsluskivac();
	}
	public Mreza dohvMrezuPolja() {
		return mreza;
	}
	public int[] odrediPozicijuPolja() {
		int[] povratniNiz = new int[2];
		for (int i=0; i < mreza.dohvPoljaMreze().length; i++)
			for (int j = 0; j < mreza.dohvPoljaMreze()[0].length; j++) {
				if ((mreza.dohvPoljaMreze())[i][j].equals(this)) {
					povratniNiz[0] = i;
					povratniNiz[1] = j;
				}
			}
		return povratniNiz;
	}
	public Polje dohvPoljeZaZadatiPomeraj(int pomerajVrsta, int pomerajKolona) {
			int trenVrsta = this.odrediPozicijuPolja()[0];
			int trenKolona = this.odrediPozicijuPolja()[1];
			int novaVrsta = trenVrsta + pomerajVrsta;
			int novaKolona = trenKolona + pomerajKolona;
			Polje trazenoPolje = null;
			if(novaVrsta >= 0 && novaVrsta < mreza.dohvPoljaMreze().length && novaKolona >= 0 && novaKolona < mreza.dohvPoljaMreze()[0].length)
					trazenoPolje = (mreza.dohvPoljaMreze())[novaVrsta][novaKolona];
			return trazenoPolje;
	}
	private void dodajOsluskivac() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!mreza.dohvIgru().dohvRezimRada()) {
					int[] staraPozicija = odrediPozicijuPolja();
					int vrsta = staraPozicija[0];
					int kolona = staraPozicija[1];
					Polje novoPolje = null;
					if(mreza.dohvIgru().dohvRezimIzmene() == "ZID")
						novoPolje = new Zid(mreza);
					else
						novoPolje = new Trava(mreza);
					mreza.zameniPolja(vrsta, kolona, novoPolje, Polje.this);
				}
			}
		});
	}
	public abstract boolean dozvoljenaFigura(Figura figura);
}
