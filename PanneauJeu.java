package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class PanneauJeu extends JPanel implements ActionListener {

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

	@Override
	public void actionPerformed(ActionEvent e) {
		String command= e.getActionCommand();
		if (command.equals("save")) {
			try {
				carte.sauvegarder();
				System.out.println("Saved !");
			} catch (WargameException e1) {
				// TODO Auto-generated catch block
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
		if (command.equals("load")) {
			try {
				carte.charger();
				repaint();
				System.out.println("Loaded !");
			} catch (WargameException e1) {
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
		if (command.equals("end_of_turn")) {
			try {
				carte.jouerTourIA();
			} catch (WargameException e1){
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
	}
	
	public void afficherInfobulle(Soldat soldier) {
		
	}
	
}
