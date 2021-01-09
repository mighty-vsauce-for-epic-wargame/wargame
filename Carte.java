package wargame;

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
    
    /* Comportement aléatoire */
    public void jouerTourIA() throws WargameException
    {
        int i, j;
        boolean aAttaque = false;
        Position pos;
        ArrayList<Soldat> monstres = new ArrayList<>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (!unites[i][j].getisHero())
                    monstres.add(unites[i][j]);
            } 
        }
        
        for (Soldat monstre : monstres)
        {
            /* Temporisation visuelle */
            try
            {
                Thread.sleep(750);
            }
            catch (InterruptedException e)
            {
                throw new WargameException("Erreur lors du tour de l'IA, arrêt du programme");
            }
            
            /* On essaye d'attaquer */
            pos = trouverHeros(monstre.getPosition(), monstre.getVisualRange());
            
            /* Si on peut, on attaque */
            if (pos != null)
            {
                combat(monstre, unites[pos.getX()][pos.getY()]);
                aAttaque = true;
            }
            
            /* Si on a pas attaqué, on essaye de déplacer */
            if (!aAttaque)
            {
                pos = trouverPositionVide(
                        monstre.getPosition(), monstre.getMovement());
                
                /* Si elle peut se déplacer, on déplace */
                if (pos != null)
                {
                    try
                    {
                        deplacerSoldat(pos, monstre);
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
        }
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
        ArrayList<Position> positions = new ArrayList<>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j] == null &&
                    carte[i][j].getTypeTerrain() != TypeTerrain.LAC &&
                    pos.distance(new Position(i, j)) <= distance)
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
                    unites[i][j].getisHero() &&
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
    	} else {
	        unites[pos.getX()][pos.getY()] =
	            unites[soldat.getPosition().getX()][soldat.getPosition().getY()];
	        unites[soldat.getPosition().getX()][soldat.getPosition().getY()] = null;
    		soldat.seDeplace(pos);
	        return true;
    	}
    }

    @Override
    public void mort(Soldat perso)
    {
    	if (perso.getisHero()) {
    		Soldat.NB_HEROS--;
    	} else {
    		Soldat.NB_MONSTRES--;
    	}
        unites[perso.getPosition().getX()][perso.getPosition().getY()] = null;
        Jeu.update_info_string();
    }

    public void combat(Soldat s1, Soldat s2) {
    	s1.combat(s2,1); // appelle la fonction combat du soldat avec par défaut une attaque mélée (1)
    	s2.combat(s1,1);
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
                g.drawImage(
                        ressources.getTerrainSprite(carte[i][j].getTypeTerrain()),
                        i * IConfig.HEX_SIZE + xOffset * i,
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) + yOffset * (Math.floorMod(i, 2) + j + 1),
                        null);
                //g.fillPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                ((Graphics2D) g).setClip(0,0,10000,10000); // pour rétablir le clip d'origine
                
                /* draw the hexes */
                g.setColor(new Color(100,150,100));
                g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
            }
        }
		
		/* draw the active hex and the troops */
        for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
        	for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
        		
        		points = Hexagon.calculPoints(
                        i * IConfig.HEX_SIZE + xOffset * (i + 1),
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
                                yOffset * (Math.floorMod(i, 2) + j + 1),
                        IConfig.HEX_SIZE);
                hex= new Polygon(points[Hexagon.X],points[Hexagon.Y],6);
        		if (hex.contains(mouse_x,mouse_y)) { // change to mouse coordinates
                	g.setColor(new Color(255,255,255,50));
                	g.fillPolygon(hex);
                	/*((Graphics2D)g).setStroke(new BasicStroke(2.0f));
                	g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                	((Graphics2D)g).setStroke(new BasicStroke(1.0f));*/
                }
        		
        		/* draw the troops */
                unit= unites[i][j];
                if (unit!=null) {
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
    }
	
	
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
