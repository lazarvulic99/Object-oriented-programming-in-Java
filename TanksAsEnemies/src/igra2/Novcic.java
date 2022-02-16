package igra2;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {
	public Novcic(Polje poljeFigure) {
		super(poljeFigure);
	}

	@Override
	public void iscrtajFiguru(Polje polje) {
		Graphics g = polje.getGraphics();
		int sirinaPolja = polje.getWidth();
		int visinaPolja = polje.getHeight();
		g.setColor(Color.YELLOW);
		g.fillOval(sirinaPolja/4, visinaPolja/4, sirinaPolja/2, visinaPolja/2);
	}

}
