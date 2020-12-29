package wargame;

import java.awt.*;
import javax.swing.JPanel;

public class PanneauJeu extends JPanel{

	// creating the map
	Carte carte= new Carte();
	
	public void paintComponent(Graphics g) {
		carte.toutDessiner(g);
	}
	
}
