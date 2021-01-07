package wargame;

public class Soldat extends Position implements ISoldat{
	
	Position posSoldat;
	
	private int health, visualRange, damage, longRange, price, soldierType, movement;
	private boolean isHero;
	
	public Soldat(boolean isHero, int soldierType, int posX, int posY) {
		super(posX, posY);
		posSoldat = new Position(posX, posY);
		
		if(isHero) {
			health = TypesH.values()[soldierType].getHealth();
			visualRange = TypesH.values()[soldierType].getVisualRange();
			damage = TypesH.values()[soldierType].getDamage();
			longRange = TypesH.values()[soldierType].getLongRange();
			price = TypesH.values()[soldierType].getPrice();
			movement = TypesH.values()[soldierType].getMovement();
		}else {
			health = TypesM.values()[soldierType].getHealth();
			visualRange = TypesM.values()[soldierType].getVisualRange();
			damage = TypesM.values()[soldierType].getDamage();
			longRange = TypesM.values()[soldierType].getLongRange();
			price = TypesM.values()[soldierType].getPrice();
			movement = TypesM.values()[soldierType].getMovement();
		}

		this.isHero = isHero;
		this.soldierType = soldierType;
		
	}
	
	
	public Position getPosition() {
		return this.posSoldat;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int l) {
		health = l;
	}
	
	public int getTour() {
		return 0;
	}

	public int getVisualRange() {
		return visualRange;
	}

	public int getPrice() {
		return price;
	}
	
	/*a faire*/
	public void joueTour(int tour) {
		
	}
	
	public boolean getisHero() {
		return isHero;
	}
	
	public int getSoldierType() {
		return soldierType;
	}
	
	public int getMovement() {
		return movement;
	}
	
	//a changer
	public int distance(Position p) {
		return 0;
	}
	
	public int combat(Soldat soldier, int attackType) {
		if(attackType == 1) {
			//the attack is a melee attack
			soldier.setHealth(soldier.getHealth() - this.damage);
			return 0;
		}else {
			//it's a long range attack
			if(distance(soldier.getPosition()) < this.visualRange) {
				soldier.setHealth(soldier.getHealth() - this.longRange);
				return 0;
			}
			return -1;
		}
	}
	

	public void seDeplace(Position newPos) {
		this.posSoldat = newPos;
	}
	
}
