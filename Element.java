package wargame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Element
{
    public enum TypeTerrain
    {
        LAC(IConfig.SPRITE_LAC, 0),
        FORET(IConfig.SPRITE_FORET, -2),
        MONTAGNE(IConfig.SPRITE_MONTAGNE, 2),
        PLAINE(IConfig.SPRITE_PLAINE, 0),
        VILLAGE(IConfig.SPRITE_VILLAGE, -3);
        
        private final String spritePath;
        private final int degatModif;
        
        private TypeTerrain(String spritePath, int degatModif)
        {
            this.spritePath = spritePath;
            this.degatModif = degatModif;
        }
        
        private String getSpritePath()
        {
            return this.spritePath;
        }
        
        private int getDegatModif()
        {
            return this.degatModif;
        }
    };
    
    private TypeTerrain typeTerrain;
    private BufferedImage sprite;
    private BufferedImage spriteBrouillard;
    private boolean visible;
    
    public Element() throws WargameException
    {
        setRandomTerrainType();
        visible = true;
        
        try
        {
            setSprite();
        }
        catch (IOException | IllegalArgumentException e)
        {
            throw new WargameException(
                    "Erreur lors du chargement d'un sprite, " +
                    "arrêt de l'application");
        }
    }
    
    public Element(TypeTerrain typeTerrain) throws WargameException
    {
        visible = true;
        this.typeTerrain = typeTerrain;
        
        try
        {
            setSprite();
        }
        catch (IOException | IllegalArgumentException e)
        {
            throw new WargameException(
                    "Erreur lors du chargement d'un sprite, " +
                    "arrêt de l'application");
        }
    }
    
    /* Shall we keep this ? */
    private void setRandomTerrainType()
    {        
        typeTerrain = TypeTerrain.values()[
                new Random().nextInt(TypeTerrain.values().length)];
    }
    
    private void setSprite() throws IOException, IllegalArgumentException
    {
        sprite = ImageIO.read(
                getClass().getResource(typeTerrain.getSpritePath()));
        
        spriteBrouillard = ImageIO.read(
                        getClass().getResource(IConfig.SPRITE_BROUILLARD));
    }
    
    public BufferedImage getTerrainSprite()
    {
        if (visible)
            return sprite;
        else
            return spriteBrouillard;
    }
    
    public int getDegatModif()
    {
        return typeTerrain.getDegatModif();
    }
    
    public TypeTerrain getTypeTerrain()
    {
        return typeTerrain;
    }
    
    public boolean estVisible()
    {
        return visible;
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
