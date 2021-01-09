package wargame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Soldat extends Position implements ISoldat,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4358118282828361652L;

	Position posSoldat;
	
	private int health, visualRange, damage, longRange, price, movement, defence;
	private boolean isHero;
	private BufferedImage sprite;
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
		
		try
        {
            setSprite();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		Jeu.update_info_string();
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
	
	public TypesS getSoldierType() {
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
		//they're in the same column or line
		if (this.posSoldat.getX() == p.getX()) {
			return Math.abs(this.posSoldat.getY() - p.getY());
		}else if (this.posSoldat.getY() == p.getY()) {
			return Math.abs(this.posSoldat.getX() - p.getX());
		}
		
		boolean xPair;
		if(this.posSoldat.getX() % 2 == 0)
			xPair = true;
		else
			xPair = false;
		
		int diffX = Math.abs(this.posSoldat.getX() - p.getX());
		int diffY = Math.abs(this.posSoldat.getY() - p.getY());
		
		//it's a neighbor
		if(Math.abs(diffX) + Math.abs(diffY) <= 2) {
			return 1;
		}
		
		//we look at the diagonals
		if(xPair && (diffX == 2 * diffY || diffX == 2 * diffY + 1)) {
			return Math.abs(diffX);
		}else if(!xPair && (diffX == 2 * diffY || diffX == 2 * diffY - 1)) {
			return Math.abs(diffX);
		}
		return -1;
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
			default:
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
			default:
				break;
			}
		}
    }
}
