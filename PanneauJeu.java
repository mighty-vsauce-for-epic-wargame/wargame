package wargame;

import java.awt.*;
import javax.swing.JPanel;

public class PanneauJeu extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// creating the map
	Carte carte= new Carte();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		carte.toutDessiner(g);
	}
	
}
