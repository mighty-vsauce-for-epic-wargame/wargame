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
		g.setColor(Color.BLACK);
		
		/*carte[3][4]= Obstacle(Obstacle.TypeObstacle.getObstacleAlea(),new Position(3,4));*/
		
		for (i=0;i<IConfig.LARGEUR_CARTE;i++)
			for (j=0;j<IConfig.HAUTEUR_CARTE;j++)
				g.drawRect(
						i*IConfig.NB_PIX_CASE,
						j*IConfig.NB_PIX_CASE,
						IConfig.NB_PIX_CASE,
						IConfig.NB_PIX_CASE);
		
		for (i=0;i<IConfig.LARGEUR_CARTE;i++) {
			for (j=0;j<IConfig.HAUTEUR_CARTE;j++) {
				if (carte[i][j]!=null) {
					System.out.println(carte[i][j].getClass().getSimpleName());
				}
				g.setColor(Color.WHITE);
				g.fillRect(
						i * IConfig.NB_PIX_CASE + 1,
						j * IConfig.NB_PIX_CASE + 1,
						IConfig.NB_PIX_CASE - 1,
						IConfig.NB_PIX_CASE - 1);
			}
		}
	}
}
