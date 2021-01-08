package wargame;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Soldat extends Position implements ISoldat{
	
	Position posSoldat;
	
	private int health, visualRange, damage, longRange, price, soldierType, movement, defence;
	private boolean isHero;
	private BufferedImage sprite;
	
	public static int NB_MONSTRES= 0;
	public static int NB_HEROS= 0;
	
	public Soldat(boolean isHero, int soldierType, int posX, int posY) {
		super(posX, posY);
		posSoldat = new Position(posX, posY);
		
		if(isHero) {
			NB_HEROS++;
			health = TypesH.values()[soldierType].getHealth();
			visualRange = TypesH.values()[soldierType].getVisualRange();
			damage = TypesH.values()[soldierType].getDamage();
			longRange = TypesH.values()[soldierType].getLongRange();
			price = TypesH.values()[soldierType].getPrice();
			movement = TypesH.values()[soldierType].getMovement();
			defence = TypesH.values()[soldierType].getDefence();
		}else {
			NB_MONSTRES++;
			health = TypesM.values()[soldierType].getHealth();
			visualRange = TypesM.values()[soldierType].getVisualRange();
			damage = TypesM.values()[soldierType].getDamage();
			longRange = TypesM.values()[soldierType].getLongRange();
			price = TypesM.values()[soldierType].getPrice();
			movement = TypesM.values()[soldierType].getMovement();
			defence = TypesM.values()[soldierType].getDefence();
		}

		this.isHero = isHero;
		this.soldierType = soldierType;
		
		try
        {
            setSprite();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
	
	public boolean getisHero() {
		return isHero;
	}
	
	public int getSoldierType() {
		return soldierType;
	}
	
	public int getMovement() {
		return movement;
	}
	
	public int getDefence() {
		return defence;
	}
	
	//a changer
	public int distance(Position p) {
		return 0;
	}
	
	public int combat(Soldat soldier, int attackType) {
		if(attackType == 1) {
			//the attack is a melee attack
			soldier.setHealth(soldier.getHealth() - this.damage + soldier.getDefence());
			return 0;
		}else {
			//it's a long range attack
			if(distance(soldier.getPosition()) < this.visualRange) {
				soldier.setHealth(soldier.getHealth() - this.longRange + soldier.getDefence());
				return 0;
			}
			return -1;
		}
	}
	

	public void seDeplace(Position newPos) {
		this.posSoldat = newPos;
	}
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
	private void setSprite() throws IOException
    {
		if (getisHero()) {
			switch (soldierType)
	        {
	            case HUMAN:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_HUMAN));
	            break;
	            
	            case ELF:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_ELF));
	            break;
	            
	            case DWARF:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_DWARF));
	            break;
	            
	            case HOBBIT:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_HOBBIT));
	            break;
	            
	            
	        }
		} else {
			switch (soldierType) {
				case TROLL:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_TROLL));
	            break;
	            
	            case ORC:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_ORC));
	            break;
	            
	            case GOBLIN:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_GOBLIN));
	            break;
	            
	            case NAZGUL:
	                sprite = ImageIO.read(
	                        getClass().getResource(ISoldat.SPRITE_NAZGUL));
	            break;
			}
		}
    }
}
