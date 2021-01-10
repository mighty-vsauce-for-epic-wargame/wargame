package wargame;

import java.io.Serializable;


public class Soldat extends Position implements ISoldat,Serializable{
	private static final long serialVersionUID = -4358118282828361652L;

	Position posSoldat;
	
	private int health, visualRange, damage, longRange, price, movement, defence;
	private boolean isHero, played;
    private TypesS soldierType;
	
	public static int NB_MONSTRES= 0;
	public static int NB_HEROS= 0;
	
	public Soldat(boolean isHero, TypesS soldierType, int posX, int posY) {
		super(posX, posY);
		posSoldat = new Position(posX, posY);
		
                health = soldierType.getHealth();
                visualRange = soldierType.getVisualRange();
                damage = soldierType.getDamage();
                longRange = soldierType.getLongRange();
                price = soldierType.getPrice();
                movement = soldierType.getMovement();
                defence = soldierType.getDefence();
                
		if(isHero)
                    NB_HEROS++;
		else
                    NB_MONSTRES++;

		this.isHero = isHero;
		this.soldierType = soldierType;
		this.played = false;
		Jeu.update_info_string();
	}
	
	
	public Position getPosition() {
		return this.posSoldat;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int d) {
		this.damage = d;
	}
	
	public int getLongRange() {
		return longRange;
	}
	
	public void setLongRange(int lr) {
		this.longRange = lr;
	}
	
	public void setHealth(int l) {
		health = l;
	}
	

	public int getVisualRange() {
		return visualRange;
	}

	public int getPrice() {
		return price;
	}
	
	public boolean getisHero() {
		return isHero;
	}
	
	public TypesS getSoldierType() {
		return soldierType;
	}
	
	public int getMovement() {
		return movement;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public boolean getPlayed() {
		return played;
	}
	
	public void setPlayed(boolean b) {
		this.played = b;
	}
	
	public int[] hex_to_cube(Position p) {
		int x = p.getX();
		int z = p.getY() - (x - (x % 2)) / 2;
		int y = - x - z;
		int a[] = {x, z, y};
		return a;
	}
	
	public int distance(Position p) {
		int[] a = hex_to_cube(this.posSoldat);
		int[] b = hex_to_cube(p);
		int rep = (Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + Math.abs(a[2] - b[2])) / 2;
		System.out.println("La distance entre ces deux est : " + rep);
		return rep;
	}

	public int combat(Soldat soldier, int attackType) {
		if(attackType == 1) {
			//the attack is a melee attack
			soldier.setHealth(soldier.getHealth() - this.damage + soldier.getDefence());
			return 0;
		}else {
			//it's a long range attack
			if(this.getPosition().distance(soldier) <= this.visualRange && this.longRange > 0) {
				soldier.setHealth(soldier.getHealth() - this.longRange + soldier.getDefence());
				return 0;
			}
			return -1;
		}
	}
	

	public void seDeplace(Position newPos) {
		this.posSoldat = newPos;
	}
}
