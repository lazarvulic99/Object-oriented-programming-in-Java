package igrica1;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Basta bastica;
	private CheckboxGroup tezina;
	private Checkbox lako;
	private Checkbox srednje;
	private Checkbox tesko;
	private static Button kreni;
	private static final Igra instancaIgre = new Igra(); //Singleton pattern
	private static boolean uIgranju = false;
	private Igra() {
		super("Igra");
		setSize(850, 700);
		this.bastica = new Basta(4, 4);
		add(this.bastica, BorderLayout.CENTER);
		add(desno(), BorderLayout.EAST);
		dodajOsluskivace();
		setVisible(true);
	}
	public Igra dohvIgru() {
		return Igra.instancaIgre;
	}
	public static void azurirajDugme() {
		kreni.setLabel("Kreni");
		uIgranju = false;
	}
	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(uIgranju)
					bastica.trajnoZaustavi();
				dispose();
			}
		});
	}
	
	private Panel desno() {
		Panel desni = new Panel(new GridLayout(10,1,5,5));
		Label izborTezine = new Label("Tezina:");
		izborTezine.setFont(new Font(null, Font.BOLD, 20));
		izborTezine.setAlignment(Label.CENTER);
		tezina = new CheckboxGroup();
		lako = new Checkbox("Lako", tezina, false);
		srednje = new Checkbox("Srednje", tezina, false);
		tesko = new Checkbox("Tesko", tezina, false);
		kreni = new Button("Kreni");
		desni.add(izborTezine);
		desni.add(lako );
		desni.add(srednje );
		desni.add(tesko );
		desni.add(kreni);
		Label l1 = new Label();
		Label l2 = new Label();
		desni.add(l1);
		desni.add(l2);
		desni.add(bastica.dohvLabeluPovrca());
		dodajOsluskivacZaDugme(kreni);
		return desni;
	}
	private void dodajOsluskivacZaDugme(Button b) {
		b.addActionListener(e -> {
			if(!uIgranju) {
				if(tezina.getSelectedCheckbox()!=null) {
					if(tezina.getSelectedCheckbox()==lako )
						bastica.pokreniBastu(1000,10);
					if(tezina.getSelectedCheckbox()==srednje )
						bastica.pokreniBastu(750,8);
					if(tezina.getSelectedCheckbox()==tesko )
						bastica.pokreniBastu(500,6);
					b.setLabel("Stani");
					uIgranju=true;
				}
			} else {
				bastica.trajnoZaustavi();
				kreni.setLabel("Kreni");
				uIgranju=false;
			}
		});
	}
	public static void main(String[] args) {
		/*Napomena za asistenta: Samo jedna napomena, ja sam shvatio da krtice beze od nas, pa da se zato krtice smanjuju, mi ih kao jurimo, i 
		 onda mi broj trenBrKoraka u rupi na pocetku bude inicijalizovan na ukBrojKoraka i taj broj koraka sam smanjivao dok ne postane jednak nuli,
		 medjutim ja tada maksimalno mogu da dobijem da postoje 3 krtice na slici, dok Vi na slici prilozenoj uz zadatak imate 5 krtica. Sustinski,
		 mislim da nije naglaseno u zadatku da li krtica bezi od nas pa se smanjuje ili dodje iz rupe i postane maksimalne moguce velicine, 
		 fora je da kada uradim kako sam prvobitno zamislio, ta krtica se toliko smanji da postane nevidljiva, i onda smanjivanje povrca malo kasni jer krtica i
		 dalje postoji ali se ne vidi, pa se ne moze zgaziti */
	}
}
