package wargame;

import java.awt.*;
import javax.swing.*;

public class Jeu {

	public static void main(String[] args) {
		// create the JFrame
		JFrame frame= new JFrame("Mighty Loot for Epic Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// adding the top bar
		JPanel topbar= new JPanel();
		topbar.setPreferredSize(new Dimension(
				IConfig.LARGEUR_CARTE*IConfig.NB_PIX_CASE,
				64));
		frame.getContentPane().add(topbar,BorderLayout.NORTH);
		
		// adding the map
		PanneauJeu map= new PanneauJeu();
		map.setPreferredSize(new Dimension(
				IConfig.LARGEUR_CARTE*IConfig.NB_PIX_CASE,
				IConfig.HAUTEUR_CARTE*IConfig.NB_PIX_CASE));
		map.setBackground(Color.BLUE);
		frame.getContentPane().add(map);
		
		// adding the bottom info label
		JLabel info= new JLabel();
		info.setPreferredSize(new Dimension(
				IConfig.LARGEUR_CARTE*IConfig.NB_PIX_CASE,
				32));
		frame.getContentPane().add(info,BorderLayout.SOUTH);
		
		// setting the window and displaying it
		frame.setLocation(200,200);
		frame.getContentPane().setPreferredSize(new Dimension(600,400));
		frame.pack();
		frame.setVisible(true);
	}

}
