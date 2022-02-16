package igra2;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Mreza extends Panel implements Runnable {
	private Polje[][] polja;
	private List<Tenk> tenkici = new ArrayList<>();
	private List<Novcic> novcici = new ArrayList<>();
	private Igrac igrac;
	private Igra igra;
	private Thread nit;
	private boolean radi = false;
	private int brParica;
	private int brPoena = 0;
	private Label poeni;
	private long vreme = 40;
	private int dimenzija;
	public Mreza(int dimenzija, Igra igra) {
		super();
		this.dimenzija = dimenzija;
		this.setLayout(new GridLayout(dimenzija, dimenzija, 0, 0));
		this.polja = new Polje[dimenzija][dimenzija];
		this.igra = igra;
		konfigurisiPolja();
		dodajPomerace();
		setVisible(true);
		requestFocus();
		setFocusable(true);
	}
	public Mreza(Igra igra) {
		this(17, igra);
	}
	private void konfigurisiPolja() {
		for(int i=0; i<polja.length; i++)
			for(int j=0; j<polja[0].length; j++) {
				double s = Math.random();
				if(s < 0.8) {
					polja[i][j] = new Trava(this);
				}
				else {
					polja[i][j] = new Zid(this);
				}
				add(polja[i][j]);
			}
	}
	public Polje[][] dohvPoljaMreze() {
		return polja;
	}
	public List<Tenk> dohvTenkice() {
		return tenkici;
	}
	public List<Novcic> dohvNovcice() {
		return novcici;
	}
	public Igra dohvIgru() {
		return igra;
	}
	public Igrac dohvIgraca() {
		return igrac;
	}
	public Label dohvLabelu() {
		return poeni;
	}
	public void postaviLabelu(Label poeni) {
		this.poeni = poeni;
	}
	public synchronized void azurirajMrezu() {
		for(int i=0; i<this.novcici.size(); i++) {
			Novcic coin = novcici.get(i);
			if(coin.dohvPoljeFigure().equals(this.igrac.dohvPoljeFigure())) {
				Polje polje = coin.dohvPoljeFigure();
				this.brPoena++;
				this.brParica--;
				this.poeni.setText("Poeni :" + this.brPoena);
				this.poeni.repaint();
				novcici.remove(coin);
				polje.repaint();
				break;
			}
		}
		for(int j = 0; j<this.tenkici.size(); j++) {
			Tenk gusenica = tenkici.get(j);
			if(gusenica.dohvPoljeFigure().equals(this.igrac.dohvPoljeFigure())) {
				this.trajnoZaustavi();
				Igrac stariIgrac = this.igrac;
				this.igrac = null;
				stariIgrac.dohvPoljeFigure().repaint();
				break;
			}
		}
	}
	
	public synchronized void inicijalizujMrezu(int brojNovcica) {
		if(this.dohvIgru().dohvRezimRada()==true) {
			this.brParica = brojNovcica;
			int brojTenkica = brojNovcica/3;
			while(brojNovcica > 0) {
				int vrsta = (int)(Math.random()*this.polja.length);
				int kolona = (int)(Math.random()*this.polja[0].length);
				boolean moze = true;
				if(polja[vrsta][kolona].dozvoljenaFigura(new Novcic(polja[vrsta][kolona]))) {
					for(int i=0; i<novcici.size(); i++) {
						Novcic coin = novcici.get(i);
						if(coin.dohvPoljeFigure().equals(polja[vrsta][kolona])) {
							moze = false;
							break;
						}
					}
					if(moze) {
						novcici.add(new Novcic(polja[vrsta][kolona]));
						brojNovcica--;
					}
				}
			}
			while(brojTenkica > 0) {
				int vrsta = (int)(Math.random()*this.polja.length);
				int kolona = (int)(Math.random()*this.polja[0].length);
				boolean moze = true;
				if(polja[vrsta][kolona].dozvoljenaFigura(new Tenk(polja[vrsta][kolona]))) {
					for(int j=0; j<tenkici.size(); j++) {
						Tenk gusenica = tenkici.get(j);
						if(gusenica.dohvPoljeFigure().equals(polja[vrsta][kolona])) {
							moze = false;
							break;
						}
					}
					if(moze) {
						Tenk novi = new Tenk(polja[vrsta][kolona]);
						novi.pokreni();
						tenkici.add(novi);
						brojTenkica--;
					}
				}
			}
			while(true) {
				int vrsta = (int)(Math.random()*this.polja.length);
				int kolona = (int)(Math.random()*this.polja[0].length);
				boolean moze = true;
				if(polja[vrsta][kolona].dozvoljenaFigura(new Igrac(polja[vrsta][kolona]))) {
					for(int k=0; k<novcici.size(); k++) {
						Novcic coin = novcici.get(k);
						if(coin.dohvPoljeFigure().equals(polja[vrsta][kolona])) {
							moze = false;
							break;
						}
					}
					if(moze) {
						for(int l=0; l<tenkici.size(); l++) {
							Tenk gusenica = tenkici.get(l);
							if(gusenica.dohvPoljeFigure().equals(polja[vrsta][kolona])) {
								moze = false;
								break;
							}
						}
					}
					if(moze) {
						Igrac player = new Igrac(polja[vrsta][kolona]);
						this.igrac = player;
						break;
					}
				}
			}
			this.poeni.setText("Poeni :" + this.brPoena);
			this.poeni.repaint();
			
		}
	}
	
	public void zameniPolja(int vrsta, int kolona, Polje polje, Polje staroPolje) {
		/*removeAll();
		polja[vrsta][kolona] = polje;
		for (int i = 0; i < polja.length; i++)
			for (int j = 0; j < polja[0].length; j++)
				add(polja[i][j]);
		revalidate();*/
		remove(staroPolje);
		polja[vrsta][kolona] = polje;
		add(polje, vrsta*dimenzija + kolona);
		revalidate();
		repaint();
	}

	private synchronized void pomeri(String smer) {
		if(smer.equals("LEVO")) {
			Polje levo = this.igrac.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(0, -1);
			if((levo instanceof Trava)) {
				Polje novoPolje = levo;
				this.igrac.pomeriFiguru(novoPolje);
			}
		}
		if(smer.equals("DESNO")) {
			Polje desno = this.igrac.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(0, 1);
			if((desno instanceof Trava)) {
				Polje novoPolje = desno;
				this.igrac.pomeriFiguru(novoPolje);
			}
		}
		if(smer.equals("DOLE")) {
			Polje dole = this.igrac.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(1, 0);
			if((dole instanceof Trava)) {
				Polje novoPolje = dole;
				this.igrac.pomeriFiguru(novoPolje);
			}
		}
		if(smer.equals("GORE")) {
			Polje gore = this.igrac.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(-1, 0);
			if((gore instanceof Trava)) {
				Polje novoPolje = gore;
				this.igrac.pomeriFiguru(novoPolje);
			}
		}
	}
	
	public void dodajPomerace() {
	
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_A:{
					String pomeraj = "LEVO";
					pomeri(pomeraj);
					break;
					}
				case KeyEvent.VK_S:{
					String pomeraj = "DOLE";
					pomeri(pomeraj);
					break;
					}
				case KeyEvent.VK_D:{
					String pomeraj = "DESNO";
					pomeri(pomeraj);
					break;
					}
				case KeyEvent.VK_W:{
					String pomeraj = "GORE";
					pomeri(pomeraj);
					break;
					}
				}
			}
		});
	}
	
	public void iscrtajMrezu() {
		for(int i=0; i<this.polja.length; i++)
			for(int j=0; j<this.polja[0].length; j++)
				polja[i][j].repaint();
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					if(!radi)
						wait();
				}
				Thread.sleep(vreme);
				//pokreniMrezu();
				azurirajMrezu();
				iscrtajMrezu();
				iscrtajFigure();
				if(this.brParica == 0) this.trajnoZaustavi();
			}
		}catch(InterruptedException e) {}
	}
	
	private synchronized void iscrtajFigure() {
		for(int i = 0; i<tenkici.size(); i++)
			tenkici.get(i).iscrtajFiguru(tenkici.get(i).dohvPoljeFigure());
		for(int i = 0; i<novcici.size(); i++)
			novcici.get(i).iscrtajFiguru(novcici.get(i).dohvPoljeFigure());
		igrac.iscrtajFiguru(igrac.dohvPoljeFigure());
		
	}
	public synchronized void pokreniMrezu() {
		this.radi = true;
		this.nit = new Thread(this);
		this.nit.start();
		this.iscrtajMrezu();
		System.out.println(tenkici.size());
		System.out.println(novcici.size());
	}
	
	public void trajnoZaustavi() {
		this.nit.interrupt();
		this.radi = false;
		for(int i = 0; i < novcici.size(); i++)
			novcici.remove(i);
		for(int j = 0; j < tenkici.size(); j++)
			tenkici.get(j).zaustavi();
		for(int k = 0; k < tenkici.size(); k++)
			tenkici.remove(k);
		this.brParica = this.brPoena = 0;
		repaint();
	}

}
