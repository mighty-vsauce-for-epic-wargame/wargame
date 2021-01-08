package wargame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Element
{
    public enum TypeTerrain
    {
        LAC(0), FORET(-2), MONTAGNE(2), PLAINE(0), VILLAGE(-3);
        
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
    
    private TypeTerrain typeTerrain;
    private BufferedImage sprite;
    private BufferedImage spriteBrouillard;
    private boolean visible;
    
    public Element()
    {
        setRandomTerrainType();
        visible = true;
        
        try
        {
            setSprite();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void setRandomTerrainType()
    {        
        typeTerrain = TypeTerrain.values()[
                new Random().nextInt(TypeTerrain.values().length)];
    }
    
    private void setSprite() throws IOException
    {
        switch (typeTerrain)
        {
            case LAC:
                sprite = ImageIO.read(
                        getClass().getResource(IConfig.SPRITE_LAC));
            break;
            
            case FORET:
                sprite = ImageIO.read(
                        getClass().getResource(IConfig.SPRITE_FORET));
            break;
            
            case MONTAGNE:
                sprite = ImageIO.read(
                        getClass().getResource(IConfig.SPRITE_MONTAGNE));
            break;
            
            case PLAINE:
                sprite = ImageIO.read(
                        getClass().getResource(IConfig.SPRITE_PLAINE));
            break;
            
            case VILLAGE:
                sprite = ImageIO.read(
                        getClass().getResource(IConfig.SPRITE_VILLAGE));
            break;
        }
        
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
