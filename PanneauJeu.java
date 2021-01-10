package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/** panneau de jeu qui contient la carte et définit des fonctions utiles au gameplay 
 * 
 * @author Roman GUIRBAL
 * @author Alexandre VERNET
 *
 */
public class PanneauJeu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// creating the map
	Carte carte= new Carte();
	
	/** fait jouer tous les monstres de manière aléatoire
	 * 
	 * @throws WargameException
	 */
    public void jouerTourIA() throws WargameException
    {
        int i, j;
        boolean aAttaque = false;
        Position pos;
        ArrayList<Soldat> monstres = new ArrayList<Soldat>();
        Soldat[][] unites= carte.getUnites();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j]!=null) {
                	if (!unites[i][j].isHero()) {
                    	unites[i][j].setPlayed(false);
                		monstres.add(unites[i][j]);
                	}
                }
            } 
        }
        
        for (Soldat monstre : monstres)
        {
            /* Temporisation visuelle */
            /*try
            {
                Thread.sleep(750);
            }
            catch (InterruptedException e)
            {
                throw new WargameException("Erreur lors du tour de l'IA, arrêt du programme");
            }*/
            
            /* On essaye d'attaquer */
            pos = carte.trouverHeros(monstre.getPosition(), monstre.getVisualRange());
            
            /* Si on peut, on attaque */
            if (pos != null)
            {
            	if (pos.distance(monstre.getPosition())==1 || monstre.getLongRange()!=0) {
	                carte.combat(monstre, unites[pos.getX()][pos.getY()]);
	                aAttaque = true;
            	}
            }
            
            /* Si on a pas attaqué, on essaye de déplacer */
            if (!aAttaque)
            {
                pos = carte.trouverPositionVide(
                        monstre.getPosition(), monstre.getMovement());
                
                /* Si elle peut se déplacer, on déplace */
                if (pos != null)
                {
                    try
                    {
                        carte.deplacerSoldat(pos, monstre);
                    }
                    catch (WargameException e)
                    {
                        WargameException.
                                montrerMessageBoxNonFatal(e.getMessage());
                    }
                }
            }
            
            aAttaque = false;
            
            //monstre.setPlayed(false);
            repaint();
        }
        
        // regain health
        for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
        	for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
        		Soldat s= carte.getUnites()[i][j];
        		if (s!=null) {
        			if (!s.getPlayed()) {
	        			s.setHealth(s.getHealth()+ISoldat.REST);
	        			if (s.getHealth()>s.getSoldierType().getHealth()) {
	        				s.setHealth(s.getSoldierType().getHealth());
	        			}
        			}
        		}
        	}
        }
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j]!=null) {
                	if (unites[i][j].isHero()) {
                    	unites[i][j].setPlayed(false);
                	}
                }
            } 
        }
    }
    
    @Override
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
				WargameException.montrerMessageBoxNonFatal("Partie sauvegardée");
			} catch (WargameException e1) {
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
		if (command.equals("load")) {
			try {
				carte.charger();
				repaint();
				System.out.println("Loaded !");
				Soldat.NB_HEROS= carte.compteHeros(carte.getUnites());
				Soldat.NB_MONSTRES= carte.compteMonstres(carte.getUnites());
				WargameException.montrerMessageBoxNonFatal("Partie chargée");
			} catch (WargameException e1) {
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
		if (command.equals("end_of_turn")) {
			try {
				jouerTourIA();
			} catch (WargameException e1){
				WargameException.montrerMessageBoxNonFatal(e1.getMessage());
			}
		}
		if (command.equals("newgame")) {
			Soldat.NB_HEROS= 0;
			Soldat.NB_MONSTRES= 0;
			this.carte= new Carte();
			repaint();
		}
		if (command.equals("quit")) {
			System.exit(0);
		}
	}

}
