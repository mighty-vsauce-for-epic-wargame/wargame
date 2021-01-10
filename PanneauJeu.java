package wargame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PanneauJeu extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// creating the map
	Carte carte= new Carte();
	
	/* Comportement aléatoire */
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
                	unites[i][j].setPlayed(false);
                	if (!unites[i][j].getisHero())
                		monstres.add(unites[i][j]);
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
    }
    
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
	}
	
	public void afficherInfobulle(Soldat soldier) {
		
	}

}
