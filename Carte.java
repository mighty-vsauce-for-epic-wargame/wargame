package wargame;

import java.awt.Color;
import java.awt.Graphics;

public class Carte implements ICarte
{
    private final Element carte[][];
    private final int VALEUR_CORRECTIVE = 4;
    
    public Carte()
    {
        carte = new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
        genererCarteAleatoire();
    }
    
    private void genererCarteAleatoire()
    {
        int i, j;
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
                carte[i][j] = new Element();
    }

    @Override
    public Element getElement(Position pos)
    {
        return carte[pos.getX()][pos.getY()];
    }

    @Override
    public Position trouverPositionVide()
    {
        return null;
    }

    @Override
    public Position trouverPositionVide(Position pos)
    {
        return null;
    }

    @Override
    public Heros trouverHeros()
    {
        return null;
    }

    @Override
    public Heros trouverHeros(Position pos)
    {
        return null;
    }

    @Override
    public boolean deplacerSoldat(Position pos, Soldat soldat)
    {
        return false;
    }

    /* WTF ? */
    @Override
    public void mort(Soldat perso)
    {
        
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
                
                g.drawImage(
                        carte[i][j].getTerrainSprite(),
                        i * IConfig.HEX_SIZE + xOffset * i,
                        j * (IConfig.HEX_SIZE - VALEUR_CORRECTIVE) + yOffset * (Math.floorMod(i, 2) + j),
                        null);
                //g.fillPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
                
                g.setColor(Color.BLACK);
                g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
            }
        }
    }
}
