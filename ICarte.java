package wargame;

import java.awt.Graphics;

public interface ICarte
{
    Element getElement(Position pos);
    Position trouverPositionVide();
    Position trouverPositionVide(Position pos);
    Soldat trouverHeros();
    Soldat trouverHeros(Position pos);
    boolean deplacerSoldat(Position pos, Soldat soldat) throws WargameException;
    void mort(Soldat perso);
    boolean actionHeros(Position pos, Position pos2);
    void jouerSoldats(PanneauJeu pj);
    void toutDessiner(Graphics g);
}
