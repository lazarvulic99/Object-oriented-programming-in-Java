package igrica1;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {

	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	protected void udariZivotinju() {
		this.rupa.zivotinja = null;
		this.rupa.zaustavi();
	}

	@Override
	protected void crtajZivotinju() {
		Graphics g=rupa.getGraphics();
		double procenat=(double)((double)(rupa.dohvTrenKoraka())/(double)(rupa.dohvUkKoraka()));
		int koordinataX=rupa.getWidth()/rupa.dohvUkKoraka();
		int koordinataY=rupa.getHeight()/rupa.dohvUkKoraka();
		koordinataX = koordinataX*(rupa.dohvUkKoraka() - rupa.dohvTrenKoraka())/2;
		koordinataY = koordinataY*(rupa.dohvUkKoraka() - rupa.dohvTrenKoraka())/2;
		int sirinaRupe = rupa.getWidth();
		int visinaRupe = rupa.getHeight();
		int sirina = (int)(sirinaRupe*procenat);
		int visina = (int)(visinaRupe*procenat);
		g.setColor(Color.DARK_GRAY);
		g.fillOval(koordinataX, koordinataY, sirina, visina);
	}

	@Override
	protected void pobeglaZivotinja() {
		this.rupa.dohvBastu().ukradiPovrce();
		this.rupa.dohvBastu().slobodnaRupa(this.rupa);
	}

}
