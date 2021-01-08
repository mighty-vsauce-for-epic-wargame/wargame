package wargame;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class PanneauJeu extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// creating the map
	Carte carte= new Carte();
	
	public void paintComponent(Graphics g) {
		carte.toutDessiner(g);
	}
	
}
