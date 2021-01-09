package wargame;

public class SaveObject
{
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
