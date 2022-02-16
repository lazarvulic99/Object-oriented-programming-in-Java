package igra2;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje {

	public Trava(Mreza mreza) {
		super(mreza);
	}

	@Override
	public boolean dozvoljenaFigura(Figura figura) {
		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(new Color(0, 255, 51));
		for(int i=0;i<this.dohvMrezuPolja().dohvNovcice().size();i++)
			if(this.dohvMrezuPolja().dohvNovcice().get(i).dohvPoljeFigure()==this)
				this.dohvMrezuPolja().dohvNovcice().get(i).iscrtajFiguru(this);
		for(int i=0;i<this.dohvMrezuPolja().dohvTenkice().size();i++)
			if(this.dohvMrezuPolja().dohvTenkice().get(i).dohvPoljeFigure()==this)
				this.dohvMrezuPolja().dohvTenkice().get(i).iscrtajFiguru(this);
		if(this.dohvMrezuPolja().dohvIgraca()!=null)
			if(this.dohvMrezuPolja().dohvIgraca().dohvPoljeFigure()==(this))
				dohvMrezuPolja().dohvIgraca().iscrtajFiguru(this);
	}

}
