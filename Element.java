package wargame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Element
{
    public enum TypeTerrain { LAC, FORET, MONTAGNE, PLAINE, VILLAGE };
    
    private TypeTerrain typeTerrain;
    private BufferedImage sprite;
    
    public Element()
    {
        setRandomTerrainType();
        
        try
        {
            setSprite();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public Element(TypeTerrain typeTerrain)
    {
        this.typeTerrain = typeTerrain;
        
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
        int randomInt;
        
        randomInt = new Random().nextInt(5);
        
        switch (randomInt)
        {
            case 0:
                typeTerrain = TypeTerrain.LAC;
            break;
            
            case 1:
                typeTerrain = TypeTerrain.FORET;
            break;
            
            case 2:
                typeTerrain = TypeTerrain.MONTAGNE;
            break;
            
            case 3:
                typeTerrain = TypeTerrain.PLAINE;
            break;
            
            case 4:
                typeTerrain = TypeTerrain.VILLAGE;
            break;
        }
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
    }
    
    public BufferedImage getTerrainSprite()
    {
        return sprite;
    }
}
