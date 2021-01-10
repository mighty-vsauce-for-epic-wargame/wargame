package wargame;

/** classe qui contient des fonctions de calcul concernant les hexagones de la map
 * 
 * @author Roman GUIRBAL
 *
 */
public abstract class Hexagon {
	public static final int X= 0;
	public static final int Y= 1;
	
	public static int[][] calculPoints(int xPos, int yPos, int side) {
		int h,r; // pour raisons géométriques
		int points[][]= new int[2][6];
		
		h= (int)calculH(side);
		r= (int)calculR(side);
		
		points[X][0]= xPos;
		points[Y][0]= yPos;
		
		points[X][1]= xPos + side;
		points[Y][1]= yPos;
        
		points[X][2]= xPos + side + h;
        points[Y][2]= yPos + r;
        
        points[X][3]= xPos + side;
        points[Y][3]= yPos + 2 * r;
        
        points[X][4]= xPos;
        points[Y][4]= yPos + 2 * r;
        
        points[X][5]= xPos - h;
        points[Y][5]= yPos + r;
        
        return points;
	}
	
	/** calcule la longueur de la projection verticale d'un côté non orthogonal de l'hexagone
	 * 
	 * @param int
	 * @return double
	 */
	public static double calculH(int side) {
		return Math.sin(degToRad(30)) * (double)side;
	}
	
	/** calcule la longueur de la projection horizontale d'un côté non orthogonal de l'hexagone
	 * 
	 * @param int
	 * @return double
	 */
	public static double calculR(int side) {
		return Math.cos(degToRad(30)) * (double)side;
	}
	
	/** transforme un angle en degres en radians
	 * 
	 * @param int
	 * @return double
	 */
	public static double degToRad(int deg) {
		return deg * Math.PI / 180;
	}
}
