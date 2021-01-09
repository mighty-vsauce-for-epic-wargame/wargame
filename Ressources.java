package wargame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import wargame.Element.TypeTerrain;
import wargame.ISoldat.TypesS;

public class Ressources
{
    private BufferedImage spriteLac;
    private BufferedImage spriteForet;
    private BufferedImage spriteMontagne;
    private BufferedImage spritePlaine;
    private BufferedImage spriteVillage;
    
    private BufferedImage spriteBrouillard;
    
    private BufferedImage spriteHumain;
    private BufferedImage spriteNain;
    private BufferedImage spriteElfe;
    private BufferedImage spriteHobbit;
    private BufferedImage spriteOrque;
    private BufferedImage spriteTroll;
    private BufferedImage spriteGobelin;
    private BufferedImage spriteNazgul;
    
    public Ressources() throws WargameException
    {
        try
        {
            initTerrainSprites();
            initSoldatsSprites();
        }
        catch (IOException | IllegalArgumentException e)
        {
            throw new WargameException(
                    "Erreur lors du chargement d'un sprite, " +
                    "arrêt de l'application");
        }
    }
    
    private void initTerrainSprites() throws
            IOException,
            IllegalArgumentException
    {
        spriteLac = 
            ImageIO.read(
                    getClass().getResource(IConfig.SPRITE_LAC));
        
        spriteForet = 
            ImageIO.read(
                    getClass().getResource(IConfig.SPRITE_FORET));
        
        spriteMontagne = 
            ImageIO.read(
                    getClass().getResource(IConfig.SPRITE_MONTAGNE));
        
        spritePlaine = 
            ImageIO.read(
                    getClass().getResource(IConfig.SPRITE_PLAINE));
        
        spriteVillage = 
            ImageIO.read(
                    getClass().getResource(IConfig.SPRITE_VILLAGE));
        
        spriteBrouillard = 
            ImageIO.read(
                    getClass().getResource(IConfig.SPRITE_BROUILLARD));
    }
    
    private void initSoldatsSprites() throws
            IOException,
            IllegalArgumentException
    {
        spriteHumain = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_HUMAN));
        
        spriteNain = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_DWARF));
        
        spriteElfe = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_ELF));
        
        spriteHobbit = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_HOBBIT));
        
        spriteOrque = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_ORC));
        
        spriteTroll = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_TROLL));
        
        spriteGobelin = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_GOBLIN));
        
        spriteNazgul = 
            ImageIO.read(
                    getClass().getResource(ISoldat.SPRITE_NAZGUL));
    }
    
    public BufferedImage getBrouillardSprite()
    {
        return spriteBrouillard;
    }
    
    public BufferedImage getTerrainSprite(TypeTerrain typeTerrain)
    {
        switch (typeTerrain)
        {
            case LAC:
                return spriteLac;                
            case FORET:
                return spriteForet;                
            case MONTAGNE:
                return spriteMontagne;                
            case PLAINE:
                return spritePlaine;                
            case VILLAGE:
                return spriteVillage;
                
            default:
                return null;
        }
    }
    
    public BufferedImage getSoldatSprite(TypesS typeSoldat)
    {
        switch (typeSoldat)
        {                
            case HUMAN:
                return spriteHumain;                
            case DWARF:
                return spriteNain;
            case ELF:
                return spriteElfe;                
            case HOBBIT:
                return spriteHobbit;                
            case ORC:
                return spriteOrque;                
            case TROLL:
                return spriteTroll;                
            case GOBLIN:
                return spriteGobelin;                
            case NAZGUL:
                return spriteNazgul;
                
            default:
                return null;
        }
    }
}
