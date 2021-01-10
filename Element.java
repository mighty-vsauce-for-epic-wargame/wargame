package wargame;

import java.io.Serializable;

/**
 * Décrit un élément de terrain de la carte.
 * @author Alexandre Vernet
 */
public class Element implements Serializable
{
    private static final long serialVersionUID = 5704426075833872252L;
    
    private boolean brouillard;

    /**
     * Décrit un type de terrain avec des modifications appliquées aux unités
     * s'y trouvant.
     */
    public enum TypeTerrain
    {
        
    	LAC(0),
        FORET(+2),
        MONTAGNE(0),
        PLAINE(0),
        VILLAGE(+3);
        
        private final int degatModif;
        
        private TypeTerrain(int degatModif)
        {
            this.degatModif = degatModif;
        }
        
        public int getDegatModif()
        {
            return this.degatModif;
        }
    };
    
    private final TypeTerrain typeTerrain;
    
    public Element(TypeTerrain typeTerrain)
    {
        this.typeTerrain = typeTerrain;
        this.brouillard = false;
    }
    
    public TypeTerrain getTypeTerrain()
    {
        return typeTerrain;
    }
    
    public boolean getBrouillard()
    {
        return brouillard;
    }
    
    public void setBrouillard(boolean brouillard)
    {
        this.brouillard = brouillard;
    }
}
