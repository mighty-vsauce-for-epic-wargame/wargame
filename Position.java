package wargame;

import java.io.Serializable;

public class Position implements IConfig,Serializable{
	
	private static final long serialVersionUID = -3922521254560781030L;
	private int x, y;

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean estValide() {
		if (x < 0 || x >= LARGEUR_CARTE || y < 0 || y >= HAUTEUR_CARTE)
			return false;
		else
			return true;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public int[] hex_to_cube(Position p) {
		int x = p.getX();
		int z = p.getY() - (x - (x % 2)) / 2;
		int y = - x - z;
		int a[] = {x, z, y};
		return a;
	}
        
	
	public int distance(Position p) {
		int[] a = hex_to_cube(this);
		int[] b = hex_to_cube(p);
		int rep = (Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + Math.abs(a[2] - b[2])) / 2;
		System.out.println("La distance entre ces deux est : " + rep);
		return rep;
	}

	public boolean estVoisine(Position pos) {
		return ((Math.abs(x - pos.x) <= 1) && (Math.abs(y - pos.y) <= 1));
	}
	@Override
	public boolean equals(Object o) {
		Position pos= (Position) o;
		return (pos.getX()==this.getX() && pos.getY()==this.getY());
	}
}
