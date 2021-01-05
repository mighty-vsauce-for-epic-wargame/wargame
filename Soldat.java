package wargame;


public class Soldat extends Position implements ISoldat{
	
	Position posSoldat;
	
	private int health, visualRange, damage, longRange, price;
	
	
	public Soldat(int soldierType, int posX, int posY) {
		super(posX, posY);
		posSoldat = new Position(posX, posY);
		switch(soldierType) {
		case 1:
			//creating a human
			health = TypesH.HUMAN.getHealth();
			visualRange = TypesH.HUMAN.getVisualRange();
			damage = TypesH.HUMAN.getDamage();
			longRange = TypesH.HUMAN.getLongRange();
			price = TypesH.HUMAN.getPrice();
			break;
		case 2:
			//creating a dwarf
			health = TypesH.DWARF.getHealth();
			visualRange = TypesH.DWARF.getVisualRange();
			damage = TypesH.DWARF.getDamage();
			longRange = TypesH.DWARF.getLongRange();
			price = TypesH.DWARF.getPrice();
			break;
		case 3:
			//creating a elf
			health = TypesH.ELF.getHealth();
			visualRange = TypesH.ELF.getVisualRange();
			damage = TypesH.ELF.getDamage();
			longRange = TypesH.ELF.getLongRange();
			price = TypesH.ELF.getPrice();
			break;
		case 4:
			//creating a hobbit
			health = TypesH.HOBBIT.getHealth();
			visualRange = TypesH.HOBBIT.getVisualRange();
			damage = TypesH.HOBBIT.getDamage();
			longRange = TypesH.HOBBIT.getLongRange();
			price = TypesH.HOBBIT.getPrice();
			break;
		
			
		case 5:
			//creating a troll
			health = TypesM.TROLL.getHealth();
			visualRange = TypesM.TROLL.getVisualRange();
			damage = TypesM.TROLL.getDamage();
			longRange = TypesM.TROLL.getLongRange();
			price = TypesM.TROLL.getPrice();
			break;
		case 6:
			//creating a orc
			health = TypesM.ORC.getHealth();
			visualRange = TypesM.ORC.getVisualRange();
			damage = TypesM.ORC.getDamage();
			longRange = TypesM.ORC.getLongRange();
			price = TypesM.TROLL.getPrice();
			break;
		case 7:
			//creating a goblin
			health = TypesM.GOBLIN.getHealth();
			visualRange = TypesM.GOBLIN.getVisualRange();
			damage = TypesM.GOBLIN.getDamage();
			longRange = TypesM.GOBLIN.getLongRange();
			price = TypesM.TROLL.getPrice();
			break;
		default:
			break;		
		}
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
