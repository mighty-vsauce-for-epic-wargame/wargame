package wargame;

import java.awt.Color;
import java.awt.Graphics;

import wargame.Obstacle.TypeObstacle;

public class Carte implements ICarte {

	protected Element carte[][]= new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
		
	@Override
	public Element getElement(Position pos) {
		return carte[pos.getX()][pos.getY()];
	}

	@Override
	public Position trouvePositionVide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position trouvePositionVide(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Heros trouveHeros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Heros trouveHeros(Position pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deplaceSoldat(Position pos, Soldat soldat) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mort(Soldat perso) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean actionHeros(Position pos, Position pos2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void jouerSoldats(PanneauJeu pj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toutDessiner(Graphics g) {
		int i,j;
		int points[][];
		int xOffset= (int)Hexagon.calculH(IConfig.HEX_SIZE);
		int yOffset= (int)Hexagon.calculR(IConfig.HEX_SIZE);
	
		for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
			for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
				points= Hexagon.calculPoints(
						i * IConfig.HEX_SIZE + xOffset * ( i + 1 ),
						j * ( IConfig.HEX_SIZE - 4 /*valeur corrective */ ) + yOffset * ( Math.floorMod(i,2) + j),
						IConfig.HEX_SIZE);
				g.setColor(IConfig.COULEUR_VIDE); /* a modifier plus tard */
				g.fillPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
			}
		}
		
		g.setColor(Color.BLACK);
		
		for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
			for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
				points= Hexagon.calculPoints(
						i * IConfig.HEX_SIZE + xOffset * ( i + 1 ),
						j * ( IConfig.HEX_SIZE - 4 /*valeur corrective */ ) + yOffset * ( Math.floorMod(i,2) + j),
						IConfig.HEX_SIZE);
				g.drawPolygon(points[Hexagon.X], points[Hexagon.Y], 6);
			}
		}
		
	}
}
