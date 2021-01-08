package wargame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

import wargame.Element.TypeTerrain;

public class Carte implements ICarte
{
    private final Element carte[][];
    private final Soldat unites[][];
    private final int VALEUR_CORRECTIVE = 4;
    private TypeTerrain biomeTypeTerrain = null;
    
    public int mouse_x, mouse_y;    
    
    public Carte()
    {
        carte  = new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
        unites = new Soldat[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
        initialiserCarte();
    }
    
    private void initialiserCarte()
    {
        int i, j;
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                carte[i][j] = new Element();
                
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
    
    /*private TypeTerrain getRandomTerrainType()
    {        
        if (biomeTypeTerrain != null)
        {
            if (!unCinq())
                return biomeTypeTerrain;
            else
            {
                return biomeTypeTerrain =
                    TypeTerrain.values()[
                    new Random().nextInt(TypeTerrain.values().length)];
            }
        }
        else
        {
            return biomeTypeTerrain =
                TypeTerrain.values()[
                new Random().nextInt(TypeTerrain.values().length)];
        }
    }*/
    
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
            {
                return new Soldat(
                    true, new Random().nextInt(ISoldat.NUM_HEROES), x, y);
            }
            else
            {
                return new Soldat(
                    false, new Random().nextInt(ISoldat.NUM_MONSTERS), x, y);
            }
        }
        
        return null;
    }

    @Override
    public Element getElement(Position pos)
    {
        return carte[pos.getX()][pos.getY()];
    }

    @Override
    public Position trouverPositionVide()
    {
        int i, j;
        ArrayList<Position> positions = new ArrayList<>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j] == null)
                    positions.add(new Position(i, j));
            }
        }
        
        return positions.get(new Random().nextInt(positions.size()));
    }

    @Override
    public Position trouverPositionVide(Position pos)
    {
        return null;
    }

    @Override
    public Soldat trouverHeros()
    {
        int i, j;
        ArrayList<Soldat> heroes = new ArrayList<>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j].getisHero())
                    heroes.add(unites[i][j]);
            }
        }
        
        return heroes.get(new Random().nextInt(heroes.size()));
    }

    @Override
    public Soldat trouverHeros(Position pos)
    {
        return null;
    }

    @Override
    public boolean deplacerSoldat(Position pos, Soldat soldat)
    {
        unites[pos.getX()][pos.getY()] =
            unites[soldat.getPosition().getX()][soldat.getPosition().getY()];
        unites[soldat.getPosition().getX()][soldat.getPosition().getY()] = null;
        
        return true;
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
                        carte[i][j].getTerrainSprite(),
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
                	g.setColor(Color.RED);
                	((Graphics2D)g).setStroke(new BasicStroke(2.0f));
                	g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                	((Graphics2D)g).setStroke(new BasicStroke(1.0f));
                }
        		
        		/* draw the troops */
                unit= unites[i][j];
                if (unit!=null) {
                	g.drawImage(
                			unit.getSprite(),
                			i * IConfig.HEX_SIZE + xOffset * i + IConfig.HEX_SIZE*3/4,
                            j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE)
                            + yOffset * (Math.floorMod(i, 2) + j + 1)
                            - IConfig.HEX_SIZE/4,
                            null);
                }
        	}
        }
    }
}
