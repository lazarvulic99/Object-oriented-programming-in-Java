package igra2;

import java.awt.Graphics;

public abstract class Figura {
	private Polje trenPoljeFigure;
	private Polje staroPoljeFigure;
	public Figura(Polje poljeFigure) {
		super();
		this.trenPoljeFigure = this.staroPoljeFigure = poljeFigure;
	}
	public Polje dohvPoljeFigure() {
		return trenPoljeFigure;
	}
	public Figura pomeriFiguru(Polje novoPolje) {
		if(novoPolje.dozvoljenaFigura(this) && novoPolje != null) {
			this.staroPoljeFigure = this.trenPoljeFigure;
			this.trenPoljeFigure = novoPolje;
		}
		this.staroPoljeFigure.repaint();
		return this;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figura other = (Figura) obj;
		if (staroPoljeFigure == null) {
			if (other.staroPoljeFigure != null)
				return false;
		} else if (!staroPoljeFigure.equals(other.staroPoljeFigure))
			return false;
		if (trenPoljeFigure == null) {
			if (other.trenPoljeFigure != null)
				return false;
		} else if (!trenPoljeFigure.equals(other.trenPoljeFigure))
			return false;
		return true;
	}
	public abstract void iscrtajFiguru(Polje polje);
}
