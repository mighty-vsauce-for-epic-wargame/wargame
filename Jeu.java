package wargame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

/** classe principale de l'application, contient la fenêtre
 * 
 * @author Alexandre VERNET
 * @author Roman GUIRBAL
 * @author Milan JANKOVIC
 *
 */
public class Jeu {
	
	public static JLabel info;
	public static final int X= 0;
	public static final int Y= 1;
	static Soldat unit; // used for events
	
	
	public static void main(String[] args) {		
		
		// create the JFrame
		JFrame frame= new JFrame("Mighty Loot for Epic Wargame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// creating a menu
		JMenuBar menuBar;
		JMenu m_file,m_jeu;
		JMenuItem mi_save,mi_load,mi_quit,mi_end,mi_restart;
		menuBar= new JMenuBar();
		m_file= new JMenu("Fichier");
		m_file.setMnemonic(KeyEvent.VK_F);
		m_jeu= new JMenu("Jeu");
		m_jeu.setMnemonic(KeyEvent.VK_J);
		menuBar.add(m_file);
		menuBar.add(m_jeu);
		mi_save= new JMenuItem("Sauvegarder",KeyEvent.VK_S);
		mi_save.setActionCommand("save");
		mi_load= new JMenuItem("Charger",KeyEvent.VK_C);
		mi_load.setActionCommand("load");
		mi_quit= new JMenuItem("Quitter",KeyEvent.VK_Q);
		mi_quit.setActionCommand("quit");
		mi_restart= new JMenuItem("Nouvelle partie",KeyEvent.VK_N);
		mi_restart.setActionCommand("newgame");
		m_file.add(mi_save);
		m_file.add(mi_load);
		m_file.addSeparator();
		m_file.add(mi_quit);
		mi_end= new JMenuItem("Fin du tour",KeyEvent.VK_T);
		mi_end.setActionCommand("end_of_turn");
		m_jeu.add(mi_restart);
		m_jeu.addSeparator();
		m_jeu.add(mi_end);
		frame.setJMenuBar(menuBar);
		
		// adding the top bar
		JPanel topbar= new JPanel();
		topbar.setLayout(new BorderLayout());
		frame.getContentPane().add(topbar,BorderLayout.NORTH);
		
		//adding a panel for the top commands
		JPanel commands= new JPanel();
		topbar.add(commands,BorderLayout.NORTH);
		
		// adding a panel for game buttons
		JPanel game_buttons= new JPanel();
		topbar.add(game_buttons,BorderLayout.SOUTH);
		
		// adding save and load buttons
		JButton b_save= new JButton("Sauvegarder");
		JButton b_load= new JButton("Charger");
		JButton b_new= new JButton("Redémarrer");
		b_save.setPreferredSize(new Dimension(128,32));
		b_save.setActionCommand("save");
		b_load.setPreferredSize(new Dimension(100,32));
		b_load.setActionCommand("load");
		b_new.setPreferredSize(new Dimension(128,32));
		b_new.setActionCommand("newgame");
		commands.add(b_save,BorderLayout.CENTER);
		commands.add(b_load,BorderLayout.CENTER);
		commands.add(b_new,BorderLayout.CENTER);
		
		// adding the end of turn button
		JButton b_fin= new JButton("Fin de tour");
		b_fin.setPreferredSize(new Dimension(128,32));
		b_fin.setBackground(new Color(200,0,0));
		b_fin.setActionCommand("end_of_turn");
		game_buttons.add(b_fin,BorderLayout.CENTER);
		
		// adding the info string next to the button
		info= new JLabel();
		info.setText(Soldat.NB_HEROS+" héros, "+Soldat.NB_MONSTRES+" monstres");
		info.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		game_buttons.add(info,BorderLayout.CENTER);
		
		// adding the map
		PanneauJeu map= new PanneauJeu();
		int h= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int r= (int)Hexagon.calculR(IConfig.HEX_SIZE);

		map.setPreferredSize(new Dimension(
				IConfig.LARGEUR_CARTE * ( IConfig.HEX_SIZE + h ) + h + 1,
				( 2 + IConfig.HAUTEUR_CARTE * 2 ) * r + 1));
		frame.getContentPane().add(map);
		
		// adding the bottom info label
		JLabel hover_info= new JLabel();
		frame.getContentPane().add(hover_info,BorderLayout.SOUTH);
		hover_info.setText("Hello world!");
		hover_info.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		
		// events of top commands and end of turn button and menu items
		b_load.addActionListener(map);
		b_save.addActionListener(map);
		b_fin.addActionListener(map);
		b_new.addActionListener(map);
		mi_save.addActionListener(map);
		mi_load.addActionListener(map);
		mi_quit.addActionListener(map);
		mi_end.addActionListener(map);
		mi_restart.addActionListener(map);
		
		// events of map
		map.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				Position pos= null;
				Soldat unite;
				int mousePos[]= Carte.posToHex(e.getX(),e.getY());
				info.setText(Soldat.NB_HEROS+" héros, "+Soldat.NB_MONSTRES+" monstres");
				if (mousePos!=null) {
					pos= new Position(mousePos[X],mousePos[Y]);
				    map.carte.mouse_x= e.getX();
				    map.carte.mouse_y= e.getY();
				    unite= map.carte.getUnite(pos);
				    if (!map.carte.getElement(pos).getBrouillard()) {
					    if (unite!=null) {
					    	hover_info.setText(
					    			unite.getSoldierType()
					    			+" ("+unite.getHealth()
					    			+" PV / "
					    			+unite.getDamage()
					    			+" MEL / "
					    			+unite.getLongRange()
					    			+" RNG) "
					    			+unite.getMovement()
					    			+" PM");
					    } else {
					    	hover_info.setText(
					    			map.carte.getElement(pos).getTypeTerrain()
					    			+" ( +"
					    			+map.carte.getElement(pos).getTypeTerrain().getDegatModif()
					    			+" DEF )");
					    }
				    } else {
				    	hover_info.setText("Inconnu");
				    }
				}
			    map.repaint();
			}
		    
		});
		map.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) {
				int coord[]= Carte.posToHex(e.getX(),e.getY());
				if (coord!=null) {
					unit= map.carte.getUnite(new Position(coord[X],coord[Y]));
					if (unit!=null) {
						if (!unit.isHero()) {
							unit= null;
						} else {
							map.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int coord[]= Carte.posToHex(e.getX(),e.getY());
				Position pos;
				Soldat other;
				if (coord!=null) {
					pos= new Position(coord[X],coord[Y]);
					if (unit!=null) {
						other= map.carte.getUnite(pos);
						if (other!=null && !other.isHero()) {
							try {
								map.carte.combat(unit,other);
							} catch (WargameException e2) {
								// TODO Auto-generated catch block
								WargameException.montrerMessageBoxNonFatal(e2.getMessage());
							}
						} else {
							try {
								map.carte.deplacerSoldat(pos, unit);
							} catch (WargameException e1) {
								// TODO Auto-generated catch block
								WargameException.montrerMessageBoxNonFatal(e1.getMessage());
							}
						}
					}
					map.carte.mouse_x= e.getX();
				    map.carte.mouse_y= e.getY();
				    map.repaint();
				}
				map.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
