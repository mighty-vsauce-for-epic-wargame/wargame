package wargame;

import java.io.Serializable;

/**
  * Cette classe représente une sauvegarde,
  * avec 2 choses :
  * <br>- Le terrain généré
  * <br>- Les unités placées, avec leurs stats
  *
  * @author Alexandre Vernet */
public class SaveObject implements Serializable
{
    private static final long serialVersionUID = 5924297110123552994L;
    private final Element carte[][];
    private final Soldat unites[][];
    
    /**
      * Renvoie un objet de sauvegarde représentant l'état entier d'une partie.
      * @param carte La carte sous forme d'un tableau à deux dimensions.
      * @param unites Les unités sous forme d'un tableau à deux dimensions.
      */
    public SaveObject(Element carte[][], Soldat unites[][])
    {
        this.carte = carte;
        this.unites = unites;
    }
    
    /**
      * Retourne le terrain sauvegardé.
      * @return Element[][] Le terrain sauvegardé.
      */
    public Element[][] getCarte()
    {
        return carte;
    }
    
    /**
      * Retourne les unités sauvegardées.
      * @return Soldat[][] Les unités sauvegardées.
      */
    public Soldat[][] getUnites()
    {
        return unites;
    }
}
