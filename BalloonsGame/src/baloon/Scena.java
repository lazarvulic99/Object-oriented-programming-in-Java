package baloon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scena extends Canvas implements Runnable {
	private Igra igra;
	private Thread nit = new Thread(this);
	private Igrac igrac;
	private int dt = 60;
	private boolean radi = false;
	private List<KruznaFigura> figure = new ArrayList<>();
	public Scena(Igra igra) {
		super();
		this.igra = igra;
		pokreni();
	}
	private void pokreni() {
		this.nit.start();
		this.radi = true;
		//this.iscrtajScenu();
	}
	public void paint(Graphics g) {
		setBackground(new Color(153, 204, 204));
		if(igrac!=null)
		igrac.paint(this);
		for(KruznaFigura f : figure)
			f.paint(this);
		/*for(KruznaFigura f: figure) {
			if(f instanceof Igrac && f!=null) {
				Igrac ovaj = (Igrac)f;
				ovaj.iscrtajIgraca(this);
			}
			if(f instanceof Balon && f!=null) {
				Balon ovaj = (Balon)f;
				ovaj.iscrtajBalon(this);
			}
		}*/
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					while(!radi)
						wait();
				}
				Thread.sleep(dt);
				azurirajScenu();
				dodajOsluskivace();
				repaint();
			}
		}catch(InterruptedException e){
			//e.printStackTrace();
		}

	}
	public void dodajOsluskivace() {
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				try {
					switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:{
						igrac.pomeri(new Vektor(-1, 0));
						break;
						}
					case KeyEvent.VK_RIGHT:{
						igrac.pomeri(new Vektor(1, 0));
						break;
						}
					}
				}catch(GPomeraj g) {
					g.printStackTrace();
				}
			}
		});
	}
	public void zavrsi() { //trajno zavrsavanje scene
		this.nit.interrupt();
	}
	private synchronized void azurirajScenu() {
		if(igrac == null) {
			igrac = new Igrac(new Vektor(this.getWidth()/2.0, this.getHeight()-31), Color.GREEN, 30.0, new Vektor(1, 0), this);
			figure.add(igrac);
			repaint();
		}
		double p = Math.random();
		if(p < 0.1)
			generisiBalon();
		for (int i=0; i<figure.size(); i++) {
			KruznaFigura f = figure.get(i);
			if(f instanceof Balon) {
				Balon balonce = (Balon)f;
				balonce.pomeri(1);
				if(balonce.centar.dohvY() + balonce.precnik/2.0 > this.getHeight()) {
					izbaciFiguru(balonce);
					break;
				}
			}
		}
		for(KruznaFigura f : figure) {
			if(f instanceof Balon) {
				Balon balonce = (Balon)f;
				if(Krug.preklapajuSe(igrac, balonce))
					this.zavrsi();
				if(balonce.centar.dohvY() + balonce.precnik/2.0 >= balonce.scena.getHeight())
					izbaciFiguru(balonce);
			}
		}
		repaint();
	}
	private synchronized void generisiBalon() {
		double x = Math.random()*this.getWidth();
		double y = 21.0;
		Vektor centarBalona = new Vektor(x, y);
		Balon balon = new Balon(centarBalona, Color.RED, 20.0, new Vektor(0, 20), this);
		figure.add(balon);
		repaint();
	}
	public synchronized Scena dodajFiguru(KruznaFigura figura) throws GVecPostoji{
		if(igrac != null && figura instanceof Igrac) throw new GVecPostoji();
		boolean preklapaSe = false;
		for(KruznaFigura f: figure) {
			if(Math.sqrt(Math.pow(f.centar.dohvX()-figura.centar.dohvX(), 2) + Math.pow(f.centar.dohvY()-figura.centar.dohvY(), 2)) <= f.precnik/2.0 + figura.precnik/2.0) {
				preklapaSe = true;
			}
		}
		if(preklapaSe == false) {
			figure.add(figura);
			repaint();
		}
		return this;
	}
	public synchronized Scena izbaciFiguru(KruznaFigura figura) {
		figure.remove(figura);
		repaint();
		return this;
	}
}
