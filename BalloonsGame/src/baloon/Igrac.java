package baloon;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends KruznaFigura {
	public Igrac(Vektor centar, Color boja, double precnik, Vektor vektorBrzine, Scena scena) {
		super(centar, Color.GREEN, precnik, null, scena);
	}
	public void pomeriNaVreme(double vreme) {
		this.pomeri(0);
	}
	public Igrac pomeri(Vektor pomeraj)throws GPomeraj {
		if(pomeraj.dohvY() != 0)
			throw new GPomeraj();
		else {
			if(this.centar.dohvX() + this.precnik/2.0 + pomeraj.dohvX() <= this.scena.getWidth() && (this.centar.dohvX() - this.precnik/2.0 + pomeraj.dohvX() >= 0)) {
					if(pomeraj.dohvX() < 0) {
						this.centar = this.centar.saberi(new Vektor((pomeraj.dohvX() - precnik/2.0 + 1.0)/100, 0));
					}
					if(pomeraj.dohvX() > 0) {
						this.centar = this.centar.saberi(new Vektor((pomeraj.dohvX() + precnik/2.0 - 1.0)/100, 0));
					}
			}
		}
		return this;
	}
	public void paint(Scena scena) {
		Graphics g = scena.getGraphics();
		g.setColor(Color.GREEN);
		g.fillOval((int)(this.centar.dohvX()-this.precnik/2.0), (int)(this.centar.dohvY() - this.precnik/2.0), 30, 30);
		g.setColor(Color.BLUE);
		g.fillOval((int)(this.centar.dohvX()-this.precnik/4.0), (int)(this.centar.dohvY() - this.precnik/4.0), 15, 15);
	}
}
