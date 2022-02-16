package baloon;

import java.awt.Color;
import java.awt.Graphics;

public class Krug {
	protected Vektor centar;
	protected Color boja;
	protected double precnik;
	public Krug(Vektor centar, Color boja, double precnik) {
		super();
		this.centar = centar;
		this.boja = Color.GREEN;
		this.precnik = precnik;
	}
	public static boolean preklapajuSe(Krug k1, Krug k2) {
		double rastojanjeCentara = Math.sqrt(Math.pow(k1.centar.dohvX()-k2.centar.dohvX(), 2) + Math.pow(k1.centar.dohvY()-k2.centar.dohvY(), 2));
		if(rastojanjeCentara > k1.precnik/2.0 + k2.precnik/2.0)
			return false;
		else
			return true;
	}
	public void paint(Scena scena) {
		Graphics g = scena.getGraphics();
		g.setColor(this.boja);
		g.fillOval((int)(this.centar.dohvX() - precnik/2.0), (int)(this.centar.dohvY() - precnik/2.0), (int)precnik, (int)precnik);
	}
}
