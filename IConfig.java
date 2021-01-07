package wargame;

import java.awt.Color;

public interface IConfig {
    int LARGEUR_CARTE = 20;
    int HAUTEUR_CARTE = 10;
    int NB_PIX_CASE = 20;
    int HEX_SIZE = 25;
    int POSITION_X = 100;
    int POSITION_Y = 50;
    int NB_HEROS = 6;
    int NB_MONSTRES = 15;
    int NB_OBSTACLES = 20;
    Color COULEUR_VIDE = Color.white, COULEUR_INCONNU = Color.lightGray;
    Color COULEUR_TEXTE = Color.black, COULEUR_MONSTRES = Color.black;
    Color COULEUR_HEROS = Color.red, COULEUR_HEROS_DEJA_JOUE = Color.pink;

    String SPRITE_LAC = "resources/LAC_50.png";
    String SPRITE_FORET = "resources/FORET_50.png";
    String SPRITE_MONTAGNE = "resources/MONTAGNE_50.png";
    String SPRITE_PLAINE = "resources/PLAINE_50.png";
    String SPRITE_VILLAGE = "resources/VILLAGE_50.png";
}
