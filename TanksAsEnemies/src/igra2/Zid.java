package igra2;

import java.awt.Color;
import java.awt.Graphics;

public class Zid extends Polje {

	public Zid(Mreza mreza) {
		super(mreza);
	}

	@Override
	public boolean dozvoljenaFigura(Figura figura) {
		return false;
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(Color.LIGHT_GRAY);
	}

}
