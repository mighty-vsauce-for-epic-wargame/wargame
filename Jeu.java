package wargame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class Jeu {
	
	public static JLabel info;
	public static final int X= 0;
	public static final int Y= 1;
	static Soldat unit; // used for events
	
	
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
		info= new JLabel();
		info.setText(Soldat.NB_HEROS+" héros, "+Soldat.NB_MONSTRES+" monstres");
		info.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		topbar.add(info,BorderLayout.CENTER);
		
		// adding the map
		PanneauJeu map= new PanneauJeu();
		int h= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int r= (int)Hexagon.calculR(IConfig.HEX_SIZE);

		map.setPreferredSize(new Dimension(
				IConfig.LARGEUR_CARTE * ( IConfig.HEX_SIZE + h ) + h + 1,
				( 2 + IConfig.HAUTEUR_CARTE * 2 ) * r + 1));
		frame.getContentPane().add(map);
		
		// events of map
		map.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
			    map.carte.mouse_x= e.getX();
			    map.carte.mouse_y= e.getY();
			    map.repaint();
			}
		    
		});
		map.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				int coord[]= Carte.posToHex(e.getX(),e.getY());
				if (coord!=null) {
					System.out.println("Pressed at "+coord[X]+","+coord[Y]);
					unit= map.carte.getUnite(new Position(coord[X],coord[Y]));
					System.out.println(unit);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int coord[]= Carte.posToHex(e.getX(),e.getY());
				Position pos;
				Soldat other;
				if (coord!=null) {
					pos= new Position(coord[X],coord[Y]);
					System.out.println("Released at "+pos.getX()+","+pos.getY());
					if (unit!=null) {
						other= map.carte.getUnite(pos);
						if (other!=null && !other.getisHero()) {
							map.carte.combat(unit,other);
						} else {
							map.carte.deplacerSoldat(pos, unit);
						}
					}
					map.carte.mouse_x= e.getX();
				    map.carte.mouse_y= e.getY();
				    map.repaint();
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// adding the bottom info label
		JLabel hover_info= new JLabel();
		frame.getContentPane().add(hover_info,BorderLayout.SOUTH);
		hover_info.setText("Hello world!");
		hover_info.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		
		// setting the window and displaying it
		frame.setLocation(256,128);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public static void update_info_string() {
		info.setText(Soldat.NB_HEROS+" héros, "+Soldat.NB_MONSTRES+" monstres");
	}
	
}
