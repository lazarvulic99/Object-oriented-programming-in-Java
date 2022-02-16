package igrica1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rupa extends Canvas implements Runnable {
	private Basta basta;
	protected Zivotinja zivotinja;
	private boolean udarena;
	private Thread nit;
	private boolean radi;
	private int trenKoraka;
	private int ukKoraka;
	private long vreme = 100;
	private boolean mousePressed = false;
	public Rupa(Basta basta) {
		super();
		this.basta = basta;
		this.zivotinja = null;
		this.udarena = false;
		this.radi = false;
		dodajOsluskivace();
	}
	private void dodajOsluskivace() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getSource() instanceof Rupa)
					((Rupa)e.getSource()).zgaziRupu();
				repaint();
			}
		});
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted() && this.trenKoraka !=this.ukKoraka ) {
				synchronized(this) {
					while(!radi)
						wait();
				}
				repaint();
				this.trenKoraka++;
				Thread.sleep(vreme);
			}
			if(this.trenKoraka == this.ukKoraka) {
				Thread.sleep(2000);
				this.zaustavi();
			}
		}catch(InterruptedException e) {
			//e.printStackTrace();
		}
	}
	public synchronized void stvoriNit() {
		this.nit = new Thread(this);
	}
	public synchronized void pokreni() {
		try {
			while(this.pokrenutaNit())
				wait();
			this.nit.start();
			this.radi = true;
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized void zaustavi() {
		if(this.zivotinja != null && this.udarena == false)
			this.zivotinja.pobeglaZivotinja();
			this.udarena = false;
			this.zivotinja = null;
			this.basta.slobodnaRupa(this);
			repaint();
			radi = false;
			nit.interrupt();
			notify();
	}
	public boolean pokrenutaNit() {
		return this.radi;
	}
	
	public int dohvTrenKoraka() { 
		return trenKoraka;
	}
	public void postaviKorake(int koraka) { 
		this.trenKoraka = 0;
		this.ukKoraka = koraka;
	}
	public int dohvUkKoraka() { 
		return ukKoraka;
	}
	public Zivotinja dohvZivotinju() { 
		return zivotinja;
	}
	public void postaviZivotinju(Zivotinja zivotinja) {
		this.zivotinja = zivotinja; 
	}
	public Basta dohvBastu() { 
		return basta;
	}
	public void zgaziRupu() {
		if(this.zivotinja != null) {
			this.zivotinja.udariZivotinju();
			this.udarena = true;
		}
	}
	
	@Override
	public void paint(Graphics g) { 
		setBackground(new Color(102, 51, 0));
		if(this.zivotinja != null)
			this.zivotinja.crtajZivotinju();
		setVisible(true);
	}
}