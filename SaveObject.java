package wargame;

import java.io.Serializable;

public class SaveObject implements Serializable
{

	private static final long serialVersionUID = 5924297110123552994L;
	private Element carte[][];
    private Soldat unites[][];
    
    public SaveObject(Element carte[][], Soldat unites[][])
    {
        this.carte = carte;
        this.unites = unites;
    }
    
    public Element[][] getCarte()
    {
        return carte;
    }
    
    public Soldat[][] getUnites()
    {
        return unites;
    }
}
