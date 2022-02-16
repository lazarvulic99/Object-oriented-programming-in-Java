package baloon;

import java.awt.Color;
import java.awt.Graphics;

public class Balon extends KruznaFigura {
	public Balon(Vektor centar, Color boja, double precnik, Vektor vektorBrzine, Scena scena) {
		super(centar, boja, precnik, vektorBrzine, scena);
	}
	public void paint(Scena scena) {
		Graphics g = scena.getGraphics();
		g.setColor(Color.RED);
		g.fillOval((int)(this.centar.dohvX() - this.precnik/2.0), (int)(this.centar.dohvY() - this.precnik/2.0), 20, 20);
	}
}
