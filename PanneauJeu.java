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
			} catch (WargameException e1) {
				// TODO Auto-generated catch block
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
		if (command.equals("load")) {
			try {
				carte.charger();
			} catch (WargameException e1) {
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
			System.out.println("Loaded !");
		}
	}
	
	public void afficherInfobulle(Soldat soldier) {
		System.out.println("Soldat "+soldier.getPosition()+" "+soldier.getHealth()+" PV");
		
	}
	
}
