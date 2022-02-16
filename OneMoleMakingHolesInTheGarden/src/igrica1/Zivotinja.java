package igrica1;

public abstract class Zivotinja {
	protected Rupa rupa;
	public Zivotinja(Rupa rupa) {
		super();
		this.rupa = rupa;
	}

	protected abstract void udariZivotinju();

	protected abstract void crtajZivotinju();

	protected abstract void pobeglaZivotinja();

}
