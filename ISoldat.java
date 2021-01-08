package wargame;

public interface ISoldat {
	
	public final int NUM_HEROES = 4, NUM_MONSTERS = 4;
	public final int HUMAN= 0, DWARF= 1, ELF= 2, HOBBIT= 3;
	public final int TROLL= 0, ORC= 1, GOBLIN= 2, NAZGUL= 3;
	
	String SPRITE_HUMAN= "resources/hobbit.png";
	String SPRITE_DWARF= "resources/nain.png";
	String SPRITE_ELF= "resources/elfe.png";
	String SPRITE_HOBBIT= "resources/hobbit.png";
	String SPRITE_ORC= "resources/orque.png";
	String SPRITE_TROLL= "resources/nazgul.png";
	String SPRITE_GOBLIN= "resources/gobelin.png";
	String SPRITE_NAZGUL= "resources/nazgul.png";
	
	
	static enum TypesH {
		HUMAN(110, 3, 20, 8, 100, 4, 0), DWARF(50, 2, 10, 0, 40, 3, 0), ELF(90, 4, 12, 15, 180, 4, 0), 
		HOBBIT(30, 8, 5, 2, 70, 7, 0);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE, MOVEMENT, DEFENCE;

		TypesH(int points, int portee, int puissance, int tir, int prix, int movement, int defence) {
			HEALTH = points;
			VISUAL_RANGE = portee;
            DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
			MOVEMENT = movement;
			DEFENCE = defence;
		}

		public int getHealth() {
			return HEALTH;
		}

		public int getVisualRange() {
			return VISUAL_RANGE;
		}

		public int getDamage() {
			return DAMAGE;
		}

		public int getLongRange() {
			return LONG_RANGE;
		}
		
		public int getPrice() {
			return PRICE;
		}
		
		public int getMovement() {
			return MOVEMENT;
		}
		
		public int getDefence() {
			return DEFENCE;
		}
		
		public static TypesH getTypeHAlea() {
			return values()[(int) (Math.random() * values().length)];
		}
	}

	public static enum TypesM {
		TROLL(170, 1, 30, 0, 250, 3, 0), ORC(100, 2, 25, 4, 100, 4, 0), GOBLIN(30, 8, 5, 2, 70, 7, 0),
		NAZGUL(200, 1, 50, 0, 500, 2, 0);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE, MOVEMENT, DEFENCE;

		TypesM(int points, int portee, int puissance, int tir, int prix, int movement, int defence) {
			HEALTH = points;
			VISUAL_RANGE = portee;
			DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
			MOVEMENT = movement;
			DEFENCE = defence;
		}

		public int getHealth() {
			return HEALTH;
		}

		public int getVisualRange() {
			return VISUAL_RANGE;
		}

		public int getDamage() {
			return DAMAGE;
		}

		public int getLongRange() {
			return LONG_RANGE;
		}
		
		public int getPrice() {
			return PRICE;
		}
		
		public int getMovement() {
			return MOVEMENT;
		}
		
		public int getDefence() {
			return DEFENCE;
		}

		public static TypesM getTypeMAlea() {
			return values()[(int) (Math.random() * values().length)];
		}
	}
	
	public Position getPosition();
	
	int getHealth();
	
	void setHealth(int l);

	/*a faire*/
	int getTour();

	int getVisualRange();
	
	int getPrice();
	
	/*a faire*/
	void joueTour(int tour);

	/**this function executes the attack of the character on soldat
	 * 
	 * @param soldier attacked soldier
	 * @param attackType 1 for melee 2 for long range
	 * @return 0 if attack executed, 0 if attack impossible
	 */
	int combat(Soldat soldier, int attackType);
	
	/**this function changes the position of the soldier to newPos
	 * 
	 * @param newPos
	 */
	void seDeplace(Position newPos);
}
