package wargame;

import java.io.Serializable;

public class Element implements Serializable
{
    private static final long serialVersionUID = 5704426075833872252L;

    public enum TypeTerrain
    {
        LAC(0),
        FORET(+2),
        MONTAGNE(2),
        PLAINE(0),
        VILLAGE(+3);
        
        private final int degatModif;
        
        private TypeTerrain(int degatModif)
        {
            this.degatModif = degatModif;
        }
        
        private int getDegatModif()
        {
            return this.degatModif;
        }
    };
    
    private final TypeTerrain typeTerrain;
    
    public Element(TypeTerrain typeTerrain)
    {
        this.typeTerrain = typeTerrain;
    }
    
    public int getDegatModif()
    {
        return typeTerrain.getDegatModif();
    }
    
    public TypeTerrain getTypeTerrain()
    {
        return typeTerrain;
    }
}
