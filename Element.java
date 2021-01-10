package wargame;

import java.io.Serializable;

public class Element implements Serializable
{
    private static final long serialVersionUID = 5704426075833872252L;
    
    private boolean brouillard;

    public enum TypeTerrain
    {
        /*LAC(0, 0, 0, 0, 0),
        FORET(+2, +1, -1, -1, -1),
        MONTAGNE(-2, -2, +2, +3, -3),
        PLAINE(0, -4, 0, 0, +1),
        VILLAGE(+3, +2, +1, +1, -1);*/
    	
    	LAC(0),
        FORET(+2),
        MONTAGNE(0),
        PLAINE(0),
        VILLAGE(+3);
        
        private final int degatModif/*, defenseModif, porteeVisuelleModif, longRangeModif, deplacementModif*/;
        
        private TypeTerrain(int degatModif/*, int defenseModif, int porteeVisuelleModif, int longRangeModif, 
        		int deplacementModif*/)
        {
            this.degatModif = degatModif;
            /*this.defenseModif = defenseModif;
            this.porteeVisuelleModif = porteeVisuelleModif;
            this.longRangeModif = longRangeModif;
            this.deplacementModif = deplacementModif;*/
        }
        
        private int getDegatModif()
        {
            return this.degatModif;
        }
        
        /*private int getDefenseModif() {
        	return this.defenseModif;
        }
        
        private int getPorteeVisuelleModif() {
        	return this.porteeVisuelleModif;
        }
        
        private int getLongRangeModif() {
        	return this.longRangeModif;
        }
        
        private int getDeplacementModif() {
        	return this.deplacementModif;
        }*/
    };
    
    private final TypeTerrain typeTerrain;
    
    public Element(TypeTerrain typeTerrain)
    {
        this.typeTerrain = typeTerrain;
        this.brouillard = false;
    }
    
    public int getDegatModif()
    {
        return typeTerrain.getDegatModif();
    }
    
    /*public int getDefenseModif() {
    	return typeTerrain.getDefenseModif();
    }
    
    public int getPorteeVisuelleModif() {
    	return typeTerrain.getPorteeVisuelleModif();
    }
    
    public int getLongRangeModif() {
    	return typeTerrain.getLongRangeModif();
    }
    
    public int getDeplacementModif() {
    	return typeTerrain.getDeplacementModif();
    }*/
    
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
