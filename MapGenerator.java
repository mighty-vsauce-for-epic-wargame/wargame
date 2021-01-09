package wargame;

import java.util.Random;
import wargame.Element.TypeTerrain;

public class MapGenerator
{
    public static Element[][] getRandomMap()
    {
        int i, j;
        
        Element[][] carte =
                new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
        
        for (i = 0; i < IConfig.LARGEUR_CARTE; i++)
        {
            for (j = 0; j < IConfig.HAUTEUR_CARTE; j++)
            {
                carte[i][j] = new Element(TypeTerrain.PLAINE);
            }
        }
        
        ajouterCoucheMap(carte, TypeTerrain.VILLAGE, nombreAleatoire(3, 7), true);
        ajouterCoucheMap(carte, TypeTerrain.FORET, nombreAleatoire(5, 10), true);
        ajouterCoucheMap(carte, TypeTerrain.MONTAGNE, nombreAleatoire(5, 10), false);
        ajouterCoucheMap(carte, TypeTerrain.LAC, nombreAleatoire(3, 7), false);
        
        return carte;
    }

    private static void generationBiome(
            Element[][] carte,
            TypeTerrain typeTerrain,
            int x,
            int y)
    {
        int[]   coordX = {x + 1, x - 1, x},
                coordY = {y + 1, y - 1, y};
        int newX, newY;
        
        carte[x][y] = new Element(typeTerrain);

        if (pourcentageChance(85))
        {
            newX = coordX[nombreAleatoire(0, 2)];
            newY = coordY[nombreAleatoire(0, 2)];

            if(coordonneeValide(newX, newY))
            {
                carte[newX][newY] = new Element(typeTerrain);                
                generationBiome(carte, typeTerrain, newX, newY);
            }
        }
    }
    
    private static boolean coordonneeValide(int x, int y)
    {
        return  (x >= 0 && x < IConfig.LARGEUR_CARTE) &&
                (y >= 0 && y < IConfig.HAUTEUR_CARTE);
    }
    
    private static int nombreAleatoire(int min, int max)
    {
        return new Random().nextInt(max - min) + min;
    }

    private static boolean pourcentageChance(int pourcentage)
    {
        return (new Random().nextInt(pourcentage) <= pourcentage);
    }
    
    private static void ajouterCoucheMap(
            Element[][] carte,
            TypeTerrain typeTerrain,
            int nombre,
            boolean biome)
    {
        int i, x, y;
        
        for (i = 0; i < nombre; i++)
        {
            x = nombreAleatoire(0, carte.length - 1);
            y = nombreAleatoire(0, carte[0].length - 1);

            if (biome)
                generationBiome(carte, typeTerrain, x, y);
            else
                carte[x][y] = new Element(typeTerrain);
        }
    }
}
