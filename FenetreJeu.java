package wargame;

import java.awt.*;
import javax.swing.*;

public class FenetreJeu {

	public static void main(String[] args) {		
		
		// create the JFrame
		JFrame frame= new JFrame("Mighty Loot for Epic Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// adding the top bar
		JPanel topbar= new JPanel();
		frame.getContentPane().add(topbar,BorderLayout.NORTH);
		
		// adding the end of turn button on the top bar
		JButton b_fin= new JButton("Fin de tour");
		b_fin.setPreferredSize(new Dimension(128,32));
		topbar.add(b_fin,BorderLayout.CENTER);
		
		// adding the info string next to the button
		JLabel info= new JLabel();
		info.setText("Some very useful info");
		info.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		topbar.add(info,BorderLayout.CENTER);
		
		// adding the map
		PanneauJeu map= new PanneauJeu();
		int h= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int r= (int)Hexagon.calculR(IConfig.HEX_SIZE);

		map.setPreferredSize(new Dimension(
				IConfig.LARGEUR_CARTE * ( IConfig.HEX_SIZE + h ) + h + 1,
				( 1 + IConfig.HAUTEUR_CARTE * 2 ) * r + 1));
		map.setBackground(Color.BLUE);
		frame.getContentPane().add(map);
		
		// adding the bottom info label
		JLabel hover_info= new JLabel();
		frame.getContentPane().add(hover_info,BorderLayout.SOUTH);
		hover_info.setText("Hello world!");
		hover_info.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		
		// setting the window and displaying it
		frame.setLocation(256,128);
		//frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

}
