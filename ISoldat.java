package wargame;

import wargame.ISoldat.TypesS;

public interface ISoldat {
	
	public final int NUM_HEROES = 4, NUM_MONSTERS = 4;
	public final int HUMAN= 0, DWARF= 1, ELF= 2, HOBBIT= 3;
	public final int TROLL= 0, ORC= 1, GOBLIN= 2, NAZGUL= 3;
	
	String SPRITE_HUMAN= "resources/humain.png";
	String SPRITE_DWARF= "resources/nain.png";
	String SPRITE_ELF= "resources/elfe.png";
	String SPRITE_HOBBIT= "resources/hobbit.png";
	String SPRITE_ORC= "resources/orque.png";
	String SPRITE_TROLL= "resources/troll.png";
	String SPRITE_GOBLIN= "resources/gobelin.png";
	String SPRITE_NAZGUL= "resources/nazgul.png";
	
	
	static enum TypesS {
		HUMAN(110, 2, 20, 8, 100, 4, 0, false), DWARF(50, 2, 10, 0, 40, 3, 0, false), ELF(90, 4, 12, 15, 180, 4, 0, false), 
		HOBBIT(30, 5, 5, 0, 70, 7, 0, false), TROLL(150, 2, 30, 0, 250, 3, 0, false), 
		ORC(100, 3, 25, 10, 100, 4, 0, false), GOBLIN(30, 5, 5, 0, 70, 7, 0, false),
		NAZGUL(40, 1, 100, 0, 500, 2, 0, false);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE, MOVEMENT, DEFENCE;
		private final boolean PLAYED;

		TypesS(int points, int portee, int puissance, int tir, int prix, int movement, int defence, boolean played) {
			HEALTH = points;
			VISUAL_RANGE = portee;
            DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
			MOVEMENT = movement;
			DEFENCE = defence;
			PLAYED = played;
		}
		
		/**
		 * gets the health of a troop
		 * 
		 * @return int health of the troop
		 */
		public int getHealth() {
			return HEALTH;
		}
		
		/**
		 * gets the visual range of a troop
		 * 
		 * @return int visual range of the troop
		 */
		public int getVisualRange() {
			return VISUAL_RANGE;
		}
		
		/**
		 * gets the damage of a troop
		 * 
		 * @return int damage of the troop
		 */
		public int getDamage() {
			return DAMAGE;
		}
		
		/**
		 * gets the long range of a troop
		 * 
		 * @return int long range of the troop
		 */
		public int getLongRange() {
			return LONG_RANGE;
		}
		
		/**
		 * gets the price of a troop
		 * 
		 * @return int price of the troop
		 */
		public int getPrice() {
			return PRICE;
		}
		
		/**
		 * gets the movement distance of a troop
		 * 
		 * @return int movement distance of the troop
		 */
		public int getMovement() {
			return MOVEMENT;
		}
		
		/**
		 * gets the defence of a troop
		 * 
		 * @return int defence of the troop
		 */
		public int getDefence() {
			return DEFENCE;
		}
		
		/**
		 * tells whether a troop has been played or not
		 * 
		 * @return int troop played or not
		 */
		public boolean getPlayed() {
			return PLAYED;
		}
		/**gives a random troop
		 * 
		 * @return TypesS random troop
		 */
		public static TypesS getTypeSAlea() {
			return values()[(int) (Math.random() * values().length)];
		}
	}
	
	/**
	 * gets the position of a troop
	 * 
	 * @return Position position of the troop
	 */
	Position getPosition();
	
	/**
	 * gets the health of a troop
	 * 
	 * @return int health of the troop
	 */
	int getHealth();
	
	/**
	 * sets the health of the troop to the value of the parameter
	 * 
	 * @param l new health of the troop
	 */
	void setHealth(int l);

	/**
	 * gets the damage of a troop
	 * 
	 * @return int damage of the troop
	 */
	int getDamage();
	
	/**
	 * sets the damage of the troop to the value of the parameter
	 * 
	 * @param d new damage of the troop
	 */
	void setDamage(int d);
	
	/**
	 * gets the long range of a troop
	 * 
	 * @return int long range of the troop
	 */
	int getLongRange();
	
	/**
	 * sets the long range of the troop to the value of the parameter
	 * 
	 * @param lr new long range of the troop
	 */
	void setLongRange(int lr);
	
	/**
	 * gets the visual range of a troop
	 * 
	 * @return int visual range of the troop
	 */
	int getVisualRange();
	
	/**
	 * gets the price of a troop
	 * 
	 * @return int price of the troop
	 */
	int getPrice();

	/**
	 * tells whether the soldier is a hero
	 * 
	 * @return boolean tells us if it's a hero
	 */
	boolean isHero();
	
	/**
	 * gives the soldier's type
	 * 
	 * @return TypesS soldier's type
	 */
	TypesS getSoldierType();
	
	/**
	 * gives the soldier's movement distance
	 * 
	 * @return int movement
	 */
	int getMovement();
	
	/**
	 * gives the soldier's defence (damage absorption)
	 * 
	 * @return int defence
	 */
	int getDefence();
	
	/**
	 * sets a new value for soldier's defence
	 * 
	 * @param d new defence value
	 */
	void setDefence(int d);
	
	/**
	 * says whether a troop has played or not
	 * 
	 * @return boolean troop played or not
	 */
	boolean getPlayed();
	
	/**
	 * sets the played value to the boolean parameter
	 * 
	 * @param b boolean value to set to
	 */
	void setPlayed(boolean b);
	
	/**
	 * this function executes the attack of the character on soldat
	 * 
	 * @param soldier attacked soldier
	 * @param attackType 1 for melee 2 for long range
	 * @return int 0 if attack executed, -1 if attack impossible
	 */
	int combat(Soldat soldier, int attackType);
	
	/**
	 * this function changes the position of the soldier to newPos
	 * 
	 * @param newPos position the troop moves to
	 */
	void seDeplace(Position newPos);
}
