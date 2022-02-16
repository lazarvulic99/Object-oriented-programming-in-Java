package baloon;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Scena scena;
	public Igra() {
		super("Baloni");
		setSize(800, 700);
		this.scena = new Scena(this);
		this.scena.revalidate();
		this.scena.paint(scena.getGraphics());
		this.add(scena, BorderLayout.CENTER);
		dodajOsluskivace();
		setVisible(true);
	}
	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				scena.zavrsi();
				dispose();
			}
		});
	}
	public static void main(String[] args) {
		new Igra();
	}
}


