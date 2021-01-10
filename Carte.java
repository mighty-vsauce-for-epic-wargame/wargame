package wargame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import wargame.Element.TypeTerrain;
import wargame.ISoldat.TypesS;

public class Carte implements ICarte
{
    private Element carte[][];
    private Soldat unites[][];
    private static final int VALEUR_CORRECTIVE = 4;
    private Ressources ressources;
    
    public int mouse_x, mouse_y;    
    
    public Carte()
    {
        carte  = new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
        unites = new Soldat[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
        ressources = null;
        
        try
        {
            ressources = new Ressources();
        }
        catch (WargameException e)
        {
            WargameException.montrerMessageBoxFatal(e.getMessage());
        }
        
        initialiserCarte();
        appliquerBrouillard();
        for (Soldat s_tab[] : unites) {
        	for (Soldat s : s_tab) {
        		if (s!=null) {
        			if (s.isHero())
        				enleverBrouillard(s);
        		}
        	}
        }
    }
    
    private void initialiserCarte()
    {
        int i, j;
        carte = MapGenerator.getRandomMap();
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {                
                if (peutSpawner(carte[i][j]))
                {
                    /* Monstres à gauche, héros à droite */
                    if (i < (IConfig.LARGEUR_CARTE / 2))
                        unites[i][j] = genererUniteAleatoire(i, j, false);
                    else
                        unites[i][j] = genererUniteAleatoire(i, j, true);
                }
            }
        }
    }
    
    public void enleverBrouillard(Soldat s) {
    	int i, j;
    	/* Pour chaque case, si l'unite unité peut voir la case,
        alors on enleve le brouillard */
    	for (i = 0; i < IConfig.LARGEUR_CARTE; i++){
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++){
            	if(carte[i][j].getBrouillard() && s.getPosition().distance(new Position(i, j))  <= s.getVisualRange()) {
            		carte[i][j].setBrouillard(false);
            	}
            }   
        }
    }
    
    /* Applique le brouillard de base sur la carte */
    
    public void appliquerBrouillard()
    {
        int i, j;
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                carte[i][j].setBrouillard(true);
            }
        }
    }

    /** compte le nb de héros sur la map */
    public int compteHeros(Soldat unites[][]) {
    	int i,j;
    	int s= 0;
    	for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
    		for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
    			if (unites[i][j]!=null) {
    				if (unites[i][j].isHero())
    					s++;
    			}
    		}
    	}
    	return s;
    }
    
    /** compte le nb de monstres sur la map */
    public int compteMonstres(Soldat unites[][]) {
    	int i,j;
    	int s= 0;
    	for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
    		for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
    			if (unites[i][j]!=null) {
    				if (!unites[i][j].isHero())
    					s++;
    			}
    		}
    	}
    	return s;
    }
    
    private boolean peutSpawner(Element elem)
    {
        TypeTerrain typeTerrain = elem.getTypeTerrain();
        
        return  (typeTerrain != TypeTerrain.LAC) &&
                (typeTerrain != TypeTerrain.MONTAGNE);
    }
    
    private boolean spawnChanceUnTiers()
    {
        return new Random().nextInt(9) < 3;
    }
    
    private Soldat genererUniteAleatoire(int x, int y, boolean heros)
    {
        if (spawnChanceUnTiers())
        {
            if (heros)
                return new Soldat(true, getRandomHeros(), x, y);
            else
                return new Soldat(false, getRandomMonstre(), x, y);
        }
        
        return null;
    }
    
    public Soldat[][] getUnites() {
    	return unites;
    }
    
    /* Chances de spawn
        Human : 20%
        Elf : 20%
        Dwarf : 30%
        Hobbit : 30% */
    private TypesS getRandomHeros()
    {
        int pourcentage = new Random().nextInt(100);
        
        if (pourcentage >= 0 && pourcentage < 20)
            return TypesS.HUMAN;
        else if  (pourcentage >= 20 && pourcentage < 40)
            return TypesS.ELF;
        else if  (pourcentage >= 40 && pourcentage < 70)
            return TypesS.DWARF;
        else
            return TypesS.HOBBIT;
    }
    
    /* Chances de spawn
        Nazgul : 10%
        Troll : 20%
        Orc : 30%
        Goblin : 40% */
    private TypesS getRandomMonstre()
    {
        int pourcentage = new Random().nextInt(100);
        
        if (pourcentage >= 0 && pourcentage < 10)
            return TypesS.NAZGUL;
        else if  (pourcentage >= 10 && pourcentage < 30)
            return TypesS.TROLL;
        else if  (pourcentage >= 30 && pourcentage < 60)
            return TypesS.ORC;
        else
            return TypesS.GOBLIN;
    }

    @Override
    public Element getElement(Position pos)
    {
        return carte[pos.getX()][pos.getY()];
    }
    
    public Soldat getUnite(Position pos)
    {
        return unites[pos.getX()][pos.getY()];
    }

    @Override
    public Position trouverPositionVide(Position pos, int distance)
    {
        int i, j;
        ArrayList<Position> positions = new ArrayList<Position>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j] == null
                	&& carte[i][j].getTypeTerrain() != TypeTerrain.LAC
                    && carte[i][j].getTypeTerrain() != TypeTerrain.MONTAGNE
                    && pos.distance(new Position(i, j)) <= distance)
                {
                    positions.add(new Position(i, j));
                }
            }
        }
        
        if (!positions.isEmpty())
            return positions.get(new Random().nextInt(positions.size()));
        
        /* Ceci veut dire qu'il n'y a pas de position valide */
        return null;
    }

    @Override
    public Position trouverHeros(Position pos, int range)
    {
        int i, j;
        ArrayList<Position> heros = new ArrayList<>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j] != null &&
                    unites[i][j].isHero() &&
                    pos.distance(new Position(i, j)) <= range)
                {
                    heros.add(new Position(i, j));
                }
            }
        }
        
        if (!heros.isEmpty())
            return heros.get(new Random().nextInt(heros.size()));
        
        /* Ceci veut dire qu'il n'y a pas de position valide */
        return null;
    }

    @Override
    public boolean deplacerSoldat(Position pos, Soldat soldat) throws WargameException
    {
    	Element terrain= carte[pos.getX()][pos.getY()];
    	if (soldat.getPosition().equals(pos) 
    			|| !pos.estValide() 
    			|| unites[pos.getX()][pos.getY()]!=null
    			|| terrain.getTypeTerrain()==Element.TypeTerrain.MONTAGNE
    			|| terrain.getTypeTerrain()==Element.TypeTerrain.LAC) {
    		throw new WargameException("Vous ne pouvez pas vous déplacer ici");
    	} else if(soldat.getPlayed()) {
    		throw new WargameException("Ce soldat a déjà joué son tour");
    	} else if (soldat.getPosition().distance(pos)>soldat.getMovement()) {
    		throw new WargameException("Ce soldat ne peut pas se déplacer aussi loin");
    	} else {
	        unites[pos.getX()][pos.getY()] =
	            unites[soldat.getPosition().getX()][soldat.getPosition().getY()];
	        unites[soldat.getPosition().getX()][soldat.getPosition().getY()] = null;
    		soldat.seDeplace(pos);
    		soldat.setPlayed(true);
    		if (soldat.isHero())
    			enleverBrouillard(soldat);
	        return true;
    	}
    }

    @Override
    public void mort(Soldat perso)
    {
    	if (perso.isHero()) {
    		Soldat.NB_HEROS--;
    	} else {
    		Soldat.NB_MONSTRES--;
    	}
        unites[perso.getPosition().getX()][perso.getPosition().getY()] = null;
        Jeu.update_info_string();
        if (Soldat.NB_HEROS==0) {
        	Object[] options = { "Quitter le jeu" };
            int retour;
            
            retour = JOptionPane.showOptionDialog(
                null,
                "Sauron a gagné, le Gondor est perdu...",
                "Fin du jeu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
            
            if (retour == JOptionPane.OK_OPTION)
                System.exit(0);
        } else if (Soldat.NB_MONSTRES==0) {
        	Object[] options = { "Quitter le jeu" };
            int retour;
            
            retour = JOptionPane.showOptionDialog(
                null,
                "Vous avez gagné ! Le Gondor est sauvé",
                "Fin du jeu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
            
            if (retour == JOptionPane.OK_OPTION)
                System.exit(0);
        }
    }

    public void combat(Soldat s1, Soldat s2) throws WargameException{
    	int d1, d2;
    	if(s1.getPlayed()) {
    		throw new WargameException("Ce soldat a déjà joué son tour");
    	}
    	if(s1.getPosition().distance(s2.getPosition()) == 1) { //Element.getTypeTerrain().getDegatModif()
    		//melee attack
    		d1 = s1.getDamage();
    		d2 = s2.getDamage();

    		if(s1.getDamage() > carte[s1.getPosition().getX()][s1.getPosition().getY()].getTypeTerrain().getDegatModif()){
    			s1.setDamage(s1.getDamage() + carte[s1.getPosition().getX()][s1.getPosition().getY()].getTypeTerrain().getDegatModif());
    		}else {
    			s1.setDamage(1);
    		}
    		if(s2.getDamage() > carte[s2.getPosition().getX()][s2.getPosition().getY()].getTypeTerrain().getDegatModif()) {
    			s2.setDamage(s2.getDamage() + carte[s2.getPosition().getX()][s2.getPosition().getY()].getTypeTerrain().getDegatModif());
    		}else {
    			s2.setDamage(1);
    		}
    		s1.combat(s2,1);
        	s2.combat(s1,1);
        	s1.setPlayed(true);
        	s1.setDamage(d1);
        	s2.setDamage(d2);
    	}else if(s1.getPosition().distance(s2.getPosition()) > s1.getVisualRange()) {
    		throw new WargameException("Ennemi trop loin");
    	}else if(s1.getLongRange() == 0){
    		throw new WargameException("Cette troupe ne peut pas attaquer de loin");
    	}else {
    		//long range attack
    		d1 = s1.getLongRange();
    		if(s1.getLongRange() > carte[s1.getPosition().getX()][s1.getPosition().getY()].getTypeTerrain().getDegatModif()) {
    			s1.setLongRange(s1.getLongRange() + 
    					carte[s1.getPosition().getX()][s1.getPosition().getY()].getTypeTerrain().getDegatModif());
    		}else {
    			s1.setLongRange(1);
    		}
    		s1.combat(s2,2);
    		s1.setLongRange(d1);
    		s1.setPlayed(true);
    		
    		System.out.println("distance = " + s2.getPosition().distance(s1.getPosition()) + 
    				"\nVisual range of the troop = " + s1.getVisualRange());
    		if(s2.getPosition().distance(s1.getPosition()) <= s2.getVisualRange() && s2.getLongRange() > 0) {
    			d2 = s2.getLongRange();
        		
    			if(s2.getLongRange() > carte[s2.getPosition().getX()][s2.getPosition().getY()].getTypeTerrain().getDegatModif()) {
    				s2.setLongRange(s2.getLongRange()
        				+ carte[s2.getPosition().getX()][s2.getPosition().getY()].getTypeTerrain().getDegatModif());
    			}
    			s2.combat(s1,2);
    			s2.setLongRange(d2);
    		}
    	}
    	if (s1.getHealth()<=0)
    		mort(s1);
    	if (s2.getHealth()<=0)
    		mort(s2);
    }
    
    @Override
    public boolean actionHeros(Position pos, Position pos2)
    {
        return false;
    }

    @Override
    public void jouerSoldats(PanneauJeu pj)
    {
        
    }
    
    public void sauvegarder() throws WargameException
    {
        ObjectOutputStream output;
        FileOutputStream fichierSauvegarde;
        SaveObject save;

        try
        {
            fichierSauvegarde = new FileOutputStream(IConfig.SAVEFILE_NAME);
            output = new ObjectOutputStream(fichierSauvegarde);
            
            save = new SaveObject(carte, unites);
            output.writeObject(save);
            
            output.flush();
            output.close();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
            throw new WargameException(
                    "Le jeu n'a pas pu être sauvegardé.");
        }
    }
    
    public void charger() throws
            WargameException
    {
        ObjectInputStream input;
        FileInputStream fichierInput;
        SaveObject save;

        try
        {
            fichierInput = new FileInputStream(IConfig.SAVEFILE_NAME);
            input = new ObjectInputStream(fichierInput);
            
            save = (SaveObject) input.readObject();
            carte = save.getCarte();
            unites = save.getUnites();
            input.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new WargameException(
                    "Le fichier de sauvegarde n'a pas pu être chargé.");
        }
    }

	@Override
	/** draw the map GUI */
	public void toutDessiner(Graphics g) {
		int i,j;
		int points[][];
		Polygon hex; //used for clipping the image of the hex into the hex
		Soldat unit; //used for the display of units
		int xOffset= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int yOffset= (int)Hexagon.calculR(IConfig.HEX_SIZE);
		
		/* draw the hexes and the map */
		for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                points = Hexagon.calculPoints(
                    i * IConfig.HEX_SIZE + xOffset * (i + 1),
                    j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
                            yOffset * (Math.floorMod(i, 2) + j + 1),
                    IConfig.HEX_SIZE);
                
                hex= new Polygon(points[Hexagon.X],points[Hexagon.Y],6);
                ((Graphics2D) g).setClip(hex); // pour que l'image se dessine uniquement dans l'hexagone
                                
                /* draw the map */
                if (carte[i][j].getBrouillard()) {
                	g.drawImage(
                            ressources.getBrouillardSprite(),
                            i * IConfig.HEX_SIZE + xOffset * i,
                            j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) + yOffset * (Math.floorMod(i, 2) + j + 1),
                            null);
                } else {
                	g.drawImage(
                            ressources.getTerrainSprite(carte[i][j].getTypeTerrain()),
                            i * IConfig.HEX_SIZE + xOffset * i,
                            j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) + yOffset * (Math.floorMod(i, 2) + j + 1),
                            null);
                }
                
                //g.fillPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                ((Graphics2D) g).setClip(0,0,10000,10000); // pour rétablir le clip d'origine

                if (!carte[i][j].getBrouillard()) {
                	if (unites[i][j]!=null) {
    	                if (unites[i][j].getPlayed() && unites[i][j].isHero()) {
    	                	g.setColor(new Color(64,64,64,128));
    	                	g.fillPolygon(hex);
    	                }
                    }
                    /* draw the hexes */
                    g.setColor(new Color(100,150,100));
                    g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                }
            }
        }
		
		/* draw the selection and the troops */
        for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
        	for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
        		
        		points = Hexagon.calculPoints(
                        i * IConfig.HEX_SIZE + xOffset * (i + 1),
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
                                yOffset * (Math.floorMod(i, 2) + j + 1),
                        IConfig.HEX_SIZE);
                hex= new Polygon(points[Hexagon.X],points[Hexagon.Y],6);
                int mouse[]= posToHex(mouse_x,mouse_y);
                Position mousePos;
                /* draw the ranges of the selected unit if there is one */
                if (mouse!=null && !carte[i][j].getBrouillard()) {
	                mousePos= new Position(mouse[0],mouse[1]);
	                Soldat hovering= unites[mouse[0]][mouse[1]];
	                int distance= mousePos.distance(new Position(i,j));
	                if (hovering!=null
	                		&& carte[i][j].getTypeTerrain()!=Element.TypeTerrain.MONTAGNE
	                		&& carte[i][j].getTypeTerrain()!=Element.TypeTerrain.LAC) {
	                	if (hovering.getLongRange()!=0) {
			                if (distance<=hovering.getVisualRange() && distance!=0) {
			                	g.setColor(new Color(255,0,0,100));
			                	g.fillPolygon(hex);
			                }
	                	} else {
	                		if (mousePos.distance(new Position(i,j))==1) {
			                	g.setColor(new Color(255,0,0,100));
			                	g.fillPolygon(hex);
	                		}
	                	}
		                if (mousePos.distance(new Position(i,j))<=hovering.getMovement()) {
		                	((Graphics2D)g).setStroke(new BasicStroke(2.0f));
		                	g.setColor(new Color(255,0,0,100));
		                	g.drawPolygon(hex);
		                	((Graphics2D)g).setStroke(new BasicStroke(1.0f));
		                }
	                }
                }
        		if (hex.contains(mouse_x,mouse_y)) { // change to mouse coordinates
                	g.setColor(new Color(255,255,255,50));
                	g.fillPolygon(hex);
                	/*((Graphics2D)g).setStroke(new BasicStroke(2.0f));
                	g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                	((Graphics2D)g).setStroke(new BasicStroke(1.0f));*/
                	
                }
        		
        		/* draw the troops */
                unit= unites[i][j];
                if (unit!=null && !carte[i][j].getBrouillard()) {
                	g.drawImage(
                			ressources.getSoldatSprite(unit.getSoldierType()),
                			i * IConfig.HEX_SIZE + xOffset * i + IConfig.HEX_SIZE*3/4,
                            j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE)
                            + yOffset * (Math.floorMod(i, 2) + j + 1)
                            - IConfig.HEX_SIZE/4,
                            null);
                }
        	}
        }
        
        /* draw the health bar */
        for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
        	for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
        		points = Hexagon.calculPoints(
                        i * IConfig.HEX_SIZE + xOffset * (i + 1),
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
                                yOffset * (Math.floorMod(i, 2) + j + 1),
                        IConfig.HEX_SIZE);
                hex= new Polygon(points[Hexagon.X],points[Hexagon.Y],6);
		        if (hex.contains(mouse_x,mouse_y) && !carte[i][j].getBrouillard()) {
		            if (unites[i][j]!=null) {
		        		ISoldat.TypesS st= unites[i][j].getSoldierType();
		        		int max_health= st.getHealth();
		        		g.setColor(Color.BLACK);
		        		g.fillRect(
		        				i * IConfig.HEX_SIZE + xOffset * (i + 1)-1,
		        				j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
		                        yOffset * (Math.floorMod(i, 2) + j)-1,
		                        max_health+2,
		                        6);
		        		g.setColor(new Color(128,0,0));
		        		g.fillRect(
		        				i * IConfig.HEX_SIZE + xOffset * (i + 1),
		        				j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
		                        yOffset * (Math.floorMod(i, 2) + j),
		                        max_health,
		                        4);
		        		g.setColor(Color.RED);
		        		g.fillRect(
		        				i * IConfig.HEX_SIZE + xOffset * (i + 1),
		        				j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
		                        yOffset * (Math.floorMod(i, 2) + j),
		                        unites[i][j].getHealth(),
		                        4);
		        	}
		        }
        	}
        }
    }
	
	/** convert int coordinates into the corresponding hex coordinates 
	 * 
	 * @param int
	 * @param int
	 * @return int[]
	 */
	public static int[] posToHex(int x, int y) {
		int i,j;
		int points[][];
		Polygon hex;
		int xOffset= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int yOffset= (int)Hexagon.calculR(IConfig.HEX_SIZE);
		int coord[]= new int[2];
		for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
        	for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
        		
        		points = Hexagon.calculPoints(
                        i * IConfig.HEX_SIZE + xOffset * (i + 1),
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
                                yOffset * (Math.floorMod(i, 2) + j + 1),
                        IConfig.HEX_SIZE);
                hex= new Polygon(points[Hexagon.X],points[Hexagon.Y],6);
        		if (hex.contains(x,y)) { // change to mouse coordinates
                	coord[0]= i;
                	coord[1]= j;
                	return coord;
                }
        	}
		}
		return null;
	}
	
	
}
