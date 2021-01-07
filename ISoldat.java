package wargame;

public interface ISoldat {
	
	public final int NUM_HEROES = 4, NUM_MONSTERS = 3;
	
	
	static enum TypesH {
		HUMAN(110, 3, 20, 8, 100, 4), DWARF(50, 2, 10, 0, 40, 3), ELF(90, 4, 12, 15, 180, 4), HOBBIT(30, 8, 5, 2, 70, 7);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE, MOVEMENT;

		TypesH(int points, int portee, int puissance, int tir, int prix, int movement) {
			HEALTH = points;
			VISUAL_RANGE = portee;
                        DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
			MOVEMENT = movement;
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
		
		public static TypesH getTypeHAlea() {
			return values()[(int) (Math.random() * values().length)];
		}
	}

	public static enum TypesM {
		TROLL(170, 1, 30, 0, 250, 3), ORC(100, 2, 25, 4, 100, 4), GOBLIN(30, 8, 5, 2, 70, 7);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE, MOVEMENT;

		TypesM(int points, int portee, int puissance, int tir, int prix, int movement) {
			HEALTH = points;
			VISUAL_RANGE = portee;
			DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
			MOVEMENT = movement;
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
