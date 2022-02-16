package igrica1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Basta extends Panel implements Runnable {
	private Rupa rupeUBasti[][];
	private int brPovrca;
	private long intervalCekanja;
	private int brKoraka;
	private int brVrsta;
	private int brKolona;
	private Thread nit;
	private boolean slobodneRupe[][];
	private Label labelaPovrce;
	private boolean radi;
	public Basta(int brVrsta, int brKolona) {
		super();
		if(brVrsta < 0 || brKolona < 0)
			System.exit(2);
		this.brVrsta = brVrsta;
		this.brKolona = brKolona;
		this.brPovrca = 100;
		setBackground(new Color(0, 153, 0));
		setLayout(new GridLayout(brVrsta, brKolona, 40, 40));
		rupeUBasti = new Rupa[brVrsta][brKolona];
		slobodneRupe = new boolean[brVrsta][brKolona];
		this.radi = false;
		inicijalizujRupe();
		labelaPovrce = new Label("Povrce :" + this.brPovrca);
		labelaPovrce.setFont(new Font(null, Font.BOLD, 18));
	}
	
	private void inicijalizujRupe() {
		for(int i=0; i<brVrsta; i++)
			for(int j=0; j<brKolona; j++) {
				rupeUBasti[i][j] = new Rupa(this);
				slobodneRupe[i][j] = true;
				add(rupeUBasti[i][j]);
			}
	}
	
	private void azuriraj() {
		this.brPovrca = 100;
		this.labelaPovrce.setText("Povrce: " + this.brPovrca);
		this.labelaPovrce.repaint();
		this.nit = new Thread(this);
	}
	
	public Label dohvLabeluPovrca() { 
		return labelaPovrce;
	}
	public void setLabelaPovrce(Label labelaPovrce) {
		this.labelaPovrce = labelaPovrce;
	}
	public int dohvBrojPovrca() { 
		return brPovrca;
	}
	public int dohvBrojKoraka() { 
		return brKoraka;
	}
	public void postBrojKoraka(int koraci) {
		this.brKoraka = koraci;
		for(int i=0; i<brVrsta; i++)
			for(int j=0; j<brKolona; j++) {
				rupeUBasti[i][j].postaviKorake(koraci);
			}
	}
	public int dohvBrVrsti() {
		return this.brVrsta;
	}
	public int dohvBrKolona() {
		return brKolona;
	}
	public void postVremeCekanja(long intervalCekanja) {
		this.intervalCekanja = intervalCekanja;
	}
	
	public void pokreniBastu(int vreme, int koraci) {
		this.postVremeCekanja(vreme);
		this.postBrojKoraka(koraci);
		azuriraj();
		this.radi = true;
		nit.start();
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted() && this.brPovrca > 0) {
				synchronized(this) {
					while(!radi)
						wait();
				}
				Thread.sleep(intervalCekanja);
				inicijalizujBastu();
			}
			trajnoZaustavi();
		}catch(InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	public void trajnoZaustavi() {
		for(int i=0; i<brVrsta; i++)
			for(int j=0; j<brKolona; j++)
				if(rupeUBasti[i][j].pokrenutaNit())
					rupeUBasti[i][j].zaustavi();;
		nit.interrupt();
		this.radi = false;
		repaint();
	}
	
	private void inicijalizujBastu() {
		generisiKrticu();
		this.intervalCekanja = (long)(0.99*this.intervalCekanja); 
	}
	
	private void generisiKrticu() {
		int vrstaKrtice = 0;
		int kolonaKrtice = 0;
		while(true) {
			vrstaKrtice = (int)(Math.random()*brVrsta);
			kolonaKrtice = (int)(Math.random()*brKolona);
			if(slobodneRupe[vrstaKrtice][kolonaKrtice])
				break;
		}
		rupeUBasti[vrstaKrtice][kolonaKrtice].postaviZivotinju(new Krtica(rupeUBasti[vrstaKrtice][kolonaKrtice]));
		slobodneRupe[vrstaKrtice][kolonaKrtice] = false;
		rupeUBasti[vrstaKrtice][kolonaKrtice].postaviKorake(brKoraka);
		rupeUBasti[vrstaKrtice][kolonaKrtice].stvoriNit();
		rupeUBasti[vrstaKrtice][kolonaKrtice].pokreni();
	}

	public void slobodnaRupa(Rupa rupa) {
		for(int i=0; i<this.brVrsta; i++)
			for(int j=0; j<this.brKolona; j++) {
				if(rupeUBasti[i][j].equals(rupa))
						this.slobodneRupe[i][j] = true;
		}
	}

	public void ukradiPovrce() {
		if(this.brPovrca == 0) {
			Igra.azurirajDugme();
		}
		else {
			
			this.brPovrca--;
			this.labelaPovrce.setText("Povrce :" + this.brPovrca);
			this.labelaPovrce.repaint();
		}
	}
	public void crtajBastu(Graphics g) {
		setBackground(Color.GREEN);
	}
}
