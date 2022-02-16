package igra2;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	private Thread nit;
	private boolean radi = false;
	private long intervalVremena = 500;
	public Tenk(Polje poljeFigure) {
		super(poljeFigure);
		this.nit = new Thread(this);
	}
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					while(!radi)
						wait();
				}
				Thread.sleep(intervalVremena);
				azurirajTenk();
				iscrtajFiguru(this.dohvPoljeFigure());
			}
		}catch(InterruptedException e) {}
	}
	
	private void azurirajTenk() {
		double p = Math.random(); // Vraca broj iz interala [0, 1)
		Polje novoPolje = null;
		if(p>=0 && p<0.25) //Levo
			novoPolje = this.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(0, -1);
		if(p>=0.25 && p<0.5) //Desno
			novoPolje = this.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(0, 1);
		if(p>=0.5 && p<0.75) //Gore
			novoPolje = this.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(-1, 0);
		if(p>=0.75 && p<1) //Dole
			novoPolje = this.dohvPoljeFigure().dohvPoljeZaZadatiPomeraj(1, 0);
		if(novoPolje.dozvoljenaFigura(this) && novoPolje != null)
			this.pomeriFiguru(novoPolje);
	}
	
	public synchronized void pokreni() {
		this.radi = true;
		this.nit.start();
	}
	
	public void zaustavi() {
		this.nit.interrupt();
		this.radi = false;
	}

	@Override
	public void iscrtajFiguru(Polje polje) {
		Graphics g = polje.getGraphics();
		int sirinaPolja = polje.getWidth();
		int visinaPolja = polje.getHeight();
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, sirinaPolja-1, visinaPolja-1);
		g.drawLine(0, visinaPolja-1, sirinaPolja-1, 0);
	}

}
