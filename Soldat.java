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
	
	@Override
	public Position getPosition() {
		return this.posSoldat;
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public int getDamage() {
		return damage;
	}
	
	@Override
	public void setDamage(int d) {
		this.damage = d;
	}
	
	@Override
	public int getLongRange() {
		return longRange;
	}
	
	@Override
	public void setLongRange(int lr) {
		this.longRange = lr;
	}
	
	@Override
	public void setHealth(int l) {
		health = l;
	}
	
	@Override
	public int getVisualRange() {
		return visualRange;
	}

	@Override
	public int getPrice() {
		return price;
	}
	
	@Override
	public boolean isHero() {
		return isHero;
	}
	
	@Override
	public TypesS getSoldierType() {
		return soldierType;
	}
	
	@Override
	public int getMovement() {
		return movement;
	}
	
	@Override
	public int getDefence() {
		return defence;
	}
	
	@Override
	public boolean getPlayed() {
		return played;
	}
	
	@Override
	public void setPlayed(boolean b) {
		this.played = b;
	}

	@Override
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
	
	@Override
	public void seDeplace(Position newPos) {
		this.posSoldat = newPos;
	}
}
