package igra2;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Mreza mreza;
	private TextField brNovcica = new TextField("0");
	private Button pokreni = new Button("Pokreni");
	private Label poeni = new Label("Poena: 0");
	private MenuBar bar = new MenuBar();
	private Menu menu = new Menu("Rezim");
	protected boolean uIgranju = false;
	protected boolean rezim; /*kada je true znaci da se igramo, kada je false, znaci da ne igramo */
	CheckboxGroup odabranaPodloga;
	Checkbox trava;
	Checkbox zid;
	public Igra() {
		super("Igra");
		setSize(600,500);
		mreza = new Mreza(this);
		rezim = true;
		add(mreza, BorderLayout.CENTER);
		add(footer(), BorderLayout.SOUTH);
		setMenuBar(bar);
		dodajMeni();
		add(desno(), BorderLayout.EAST);
		dodajOsluskivace();
		setVisible(true);
	}
	private Panel footer() {
		Panel p = new Panel();
		Panel coins = new Panel();
		coins.add(new Label("Novcica: "));
		coins.add(brNovcica);
		p.add(coins);
		mreza.azurirajMrezu();
		poeni.setAlignment(Label.CENTER);
		p.add(poeni);
		p.add(pokreni);
		return p;
	}
	private Panel desno() {
		Panel desni = new Panel(new GridLayout(1, 2, 0, 0));
		Label podloga = new Label("Podloga: ");
		podloga.setBackground(Color.WHITE);
		desni.add(podloga);
		Panel travazid = new Panel(new GridLayout(2, 1, 0, 0));
		Panel podlogaGore = new Panel(new GridLayout(1, 1, 0, 0));
		Panel podlogaDole = new Panel(new GridLayout(1, 1, 0, 0));
		podlogaGore.setBackground(new Color(0, 255, 51));
		podlogaDole.setBackground(Color.LIGHT_GRAY);
		CheckboxGroup odabranaPodloga = new CheckboxGroup();
		trava = new Checkbox("Trava", true, odabranaPodloga);
		trava.setEnabled(true);
		zid = new Checkbox("Zid", false, odabranaPodloga);
		trava.setBackground(new Color(0, 255, 51));
		zid.setBackground(Color.LIGHT_GRAY);
		podlogaGore.add(trava);
		podlogaDole.add(zid);
		travazid.add(podlogaGore);
		travazid.add(podlogaDole);
		desni.add(travazid);
		return desni;
	}
	private void dodajMeni() {
		ActionListener osluskivacMenija = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent dogadjaj) {
				MenuItem trenRezimIgre = (MenuItem) (dogadjaj.getSource());
				String vrati = trenRezimIgre.getLabel();
				if (vrati == "Rezim izmena") {
					if (uIgranju==true) {
						mreza.trajnoZaustavi();
						uIgranju = false;
					}
					rezim = false;
					mreza.repaint();
				} else {
					rezim = true;
					uIgranju = true;
					mreza.inicijalizujMrezu(Integer.parseInt(mreza.dohvLabelu().getText()));
					mreza.pokreniMrezu();
					mreza.requestFocus();
				}
			}
		};
		bar.add(menu);
		MenuItem rezimIzmena = new MenuItem("Rezim izmena", new MenuShortcut('C'));
		menu.add(rezimIzmena);
		menu.addSeparator();
		MenuItem rezimIgranja = new MenuItem("Rezim igranja", new MenuShortcut('P'));
		menu.add(rezimIgranja);
		rezimIzmena.addActionListener(osluskivacMenija);
		rezimIgranja.addActionListener(osluskivacMenija);
		rezimIzmena.setEnabled(true);
		rezimIgranja.setEnabled(true);
	}
	protected boolean dohvRezimRada() {
		return this.rezim;
	}
	protected String dohvRezimIzmene() {
		String vrati = "";
		if(trava.getState() == true) {
			vrati = "TRAVA";
		}
		if(zid.getState() == true) {
			vrati = "ZID";
		}
		return vrati;
	}
	private void dodajOsluskivace() {
		pokreni.addActionListener(e->{
			if(e.getActionCommand() == "Pokreni") {
				pokreni.setActionCommand("Zaustavi");
				pokreni.setLabel("Zaustavi");
				this.mreza.postaviLabelu(poeni);
				this.mreza.requestFocus();
				//setFocusable(true);
				this.mreza.pokreniMrezu();
				this.mreza.inicijalizujMrezu(Integer.parseInt(brNovcica.getText()));
			}
			else {
				pokreni.setActionCommand("Pokreni");
				pokreni.setLabel("Pokreni");
				this.mreza.trajnoZaustavi(); 
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(uIgranju==true) {
					uIgranju = false;
					mreza.trajnoZaustavi();
				}
				dispose();
			}
		});
	}
	public static void main(String[] args) {
		new Igra();
	}
}