package wargame;

import java.awt.Graphics;

public interface ICarte
{
	
    /**
     * enleve le brouillard dans toutes les cases que le soldat en argument peut voir
     * @param s a soldier
     */
    void enleverBrouillard(Soldat s);
    
    /**
     * applique le brouillard sur toute la carte
     */
    void appliquerBrouillard();
    
    
    /**
     * compte le nb de héros sur la map
     * @param unites tableau des unites
     * @return int le nombre d'heros
     */
    int compteHeros(Soldat unites[][]);
    
    
    /**
     * compte le nb de monstres sur la map
     * @param unites tableau des unites
     * @return int le nombre de monstres
     */
    int compteMonstres(Soldat unites[][]);
    
    /**
     * retourne la table des unites
     * @return Soldat[][] table des unites
     */
    Soldat[][] getUnites();
    
    /**
	 * retourne l'element a la position donnee
	 * @param pos position qu'on regarde
	 * @return Element l'element a la position donnee
	 */
    Element getElement(Position pos);
    
    /**
     * trouve une position vide a partir de la position pos a une distance distance
     * @param pos position
     * @param distance distance de deplacement
     * @return Position une position vide
     */
    Position trouverPositionVide(Position pos, int distance);
    
    /**
     * trouve si possible un hero a une distance
     * @param pos position
     * @param range  portee maximale de recherche
     * @return Position if hero found, null otherwise
     */
    public Position trouverHeros(Position pos, int range);
    
    /**
     * deplace le soldat soldat a la position pos
     * @param pos position ou le soldat va se deplacer
     * @param soldat soldat qui va se deplacer
     * @return boolean true si tout s'est bien passe 
     * @throws WargameException message envoye si pb de deplacement
     */
    public boolean deplacerSoldat(Position pos, Soldat soldat) throws WargameException;
    
    /** 
     * supprime un soldat
     * 
     * @param perso Soldat a supprimer
     * 
     */
    void mort(Soldat perso);
    
    /** 
     * fait s'attaquer deux soldats entre eux
     * 
     * @param s1 Premier soldat qui attaque
     * @param s2 Soldat qui se defend puis qui a son tour attaque
     * @throws WargameException message envoye si pb de combat
     * 
     */
    public void combat(Soldat s1, Soldat s2) throws WargameException;
    
    public boolean actionHeros(Position pos, Position pos2);
    
    public void jouerSoldats(PanneauJeu pj);
    
    /** 
     * écrit un fichier de sauvegarde 
     * 
     * @throws WargameException message envoye si pb de sauvegarde
     * 
     */
    public void sauvegarder() throws WargameException;
    
    /** 
     * charge un fichier de sauvegarde
     * 
     * @throws WargameException message envoye si pb de chargement
     * 
     */
    public void charger() throws WargameException;
    
    
	/** 
	 * draw the map GUI
	 * 
	 *  @param g Object Graphics.
	 *  
	 */
	public void toutDessiner(Graphics g);
	
	
}
