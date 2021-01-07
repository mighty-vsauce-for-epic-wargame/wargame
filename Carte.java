package wargame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class Carte implements ICarte
{
    private final Element carte[][];
    private final Soldat unites[][];
    private final int VALEUR_CORRECTIVE = 4;
    
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
                unites[i][j] = genererUniteAleatoire(i, j);
            }
        }
    }
    
    private Soldat genererUniteAleatoire(int x, int y)
    {
        if (new Random().nextBoolean())
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
    public Heros trouverHeros()
    {
        int i, j;
        ArrayList<Heros> heroes = new ArrayList<>();
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                if (unites[i][j].getisHero())
                    heroes.add((Heros) unites[i][j]);
            }
        }
        
        return heroes.get(new Random().nextInt(heroes.size()));
    }

    @Override
    public Heros trouverHeros(Position pos)
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
		int xOffset= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int yOffset= (int)Hexagon.calculR(IConfig.HEX_SIZE);
	
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                points = Hexagon.calculPoints(
                    i * IConfig.HEX_SIZE + xOffset * (i + 1),
                    j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) +
                            yOffset * (Math.floorMod(i, 2) + j),
                    IConfig.HEX_SIZE);
                
                Polygon hex= new Polygon(points[Hexagon.X],points[Hexagon.Y],6);
                ((Graphics2D) g).setClip(hex); // pour que l'image se dessine uniquement dans l'hexagone
                
                g.drawImage(
                        carte[i][j].getTerrainSprite(),
                        i * IConfig.HEX_SIZE + xOffset * i,
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) + yOffset * (Math.floorMod(i, 2) + j),
                        null);
                //g.fillPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                ((Graphics2D) g).setClip(0,0,10000,10000); // pour rétablir le clip d'origine
                g.setColor(Color.BLACK);
                g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
            }
        }
    }
}
