package wargame;

import java.awt.Graphics;

public interface ICarte {
	Element getElement(Position pos);

	Position trouvePositionVide();

	Position trouvePositionVide(Position pos);

	Heros trouveHeros(); // Trouve aléatoirement un héros sur la carte

	Heros trouveHeros(Position pos); // Trouve un héros choisi aléatoirement
										// parmi les 8 positions adjacentes de pos

	boolean deplaceSoldat(Position pos, Soldat soldat);

	void mort(Soldat perso);

	boolean actionHeros(Position pos, Position pos2);

	void jouerSoldats(PanneauJeu pj);

	void toutDessiner(Graphics g);
}
