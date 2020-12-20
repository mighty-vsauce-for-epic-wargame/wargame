package wargame;

import java.awt.*;
import javax.swing.*;

public class Jeu {

	public static void main(String[] args) {
		// create the JFrame
		JFrame frame= new JFrame("Mighty Loot for Epic Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// setting the window and displaying it
		frame.setLocation(200,200);
		frame.getContentPane().setPreferredSize(new Dimension(600,400));
		frame.pack();
		frame.setVisible(true);
	}

}
