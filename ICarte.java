package wargame;

import java.awt.Graphics;

public interface ICarte
{
    Element getElement(Position pos);
    Position trouverPositionVide();
    Position trouverPositionVide(Position pos);
    Heros trouverHeros();
    Heros trouverHeros(Position pos);
    boolean deplacerSoldat(Position pos, Soldat soldat);
    void mort(Soldat perso);
    boolean actionHeros(Position pos, Position pos2);
    void jouerSoldats(PanneauJeu pj);
    void toutDessiner(Graphics g);
}
