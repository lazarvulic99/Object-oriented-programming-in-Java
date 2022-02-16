package baloon;

import java.awt.Color;

public class KruznaFigura extends Krug {
	protected Vektor vektorBrzine;
	protected Scena scena;
	public KruznaFigura(Vektor centar, Color boja, double precnik, Vektor vektorBrzine, Scena scena) {
		super(centar, boja, precnik);
		this.vektorBrzine = vektorBrzine;
		this.scena = scena;
	}
	public void pomeri(double vreme) {
		Vektor vektorV = this.vektorBrzine;
		vektorV.pomnozi(vreme);
		this.centar = this.centar.saberi(vektorV);
		if((this.centar.dohvX() - precnik/2.0)<0 || (this.centar.dohvX() + this.precnik/2) > scena.getWidth()||(this.centar.dohvY() - precnik/2.0)<0 || (this.centar.dohvY() + precnik/2.0) > scena.getHeight())
			this.scena.izbaciFiguru(this);
	}
	public static boolean obavestiOsudaru(KruznaFigura f1, KruznaFigura f2) {
		if(KruznaFigura.preklapajuSe(f1, f2) == true) {
			f1.scena.zavrsi();
			return true;
			}
		else
			return false;
	}
}
