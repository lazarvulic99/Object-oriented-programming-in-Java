package igra2;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {
	public Igrac(Polje poljeFigure) {
		super(poljeFigure);
	}
	@Override
	public void iscrtajFiguru(Polje polje) {
		Graphics g = polje.getGraphics();
		g.setColor(Color.RED);
		int sirinaPolja = polje.getWidth();
		int visinaPolja = polje.getHeight();
		g.drawLine(sirinaPolja/2, 0, sirinaPolja/2, visinaPolja);
		g.drawLine(0, visinaPolja/2, sirinaPolja, visinaPolja/2);
	}
}
