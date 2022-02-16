package baloon;

public class Vektor implements Cloneable{
	private double kordinataX;
	private double kordinataY;
	public Vektor(double kordinataX, double kordinataY) {
		super();
		this.kordinataX = kordinataX;
		this.kordinataY = kordinataY;
	}
	public double dohvX() { 
		return kordinataX;
	}
	public double dohvY() { 
		return kordinataY;
	}
	public Vektor pomnozi(double skalar) {
		this.kordinataX = this.kordinataX*skalar;
		this.kordinataY = this.kordinataY*skalar;
		return this;
	}
	public Vektor saberi(Vektor v2) {
		double novoX = this.kordinataX + v2.kordinataX;
		double novoY = this.kordinataY + v2.kordinataY;
		return new Vektor(novoX, novoY);
	}
	@Override
	public Vektor clone() {
		try {
			Vektor kopija = (Vektor)super.clone();
			return kopija;
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
