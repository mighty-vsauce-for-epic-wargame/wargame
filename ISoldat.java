package wargame;

public interface ISoldat {
	static enum TypesH {
		HUMAN(110, 3, 20, 8, 100), DWARF(50, 2, 10, 0, 40), ELF(90, 4, 12, 15, 180), HOBBIT(30, 8, 5, 2, 70);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE;

		TypesH(int points, int portee, int puissance, int tir, int prix) {
			HEALTH = points;
			VISUAL_RANGE = portee;
			DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
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

		public static TypesH getTypeHAlea() {
			return values()[(int) (Math.random() * values().length)];
		}
	}

	public static enum TypesM {
		TROLL(170, 1, 30, 0, 250), ORC(100, 2, 25, 4, 100), GOBLIN(30, 8, 5, 2, 70);

		private final int HEALTH, VISUAL_RANGE, DAMAGE, LONG_RANGE, PRICE;

		TypesM(int points, int portee, int puissance, int tir, int prix) {
			HEALTH = points;
			VISUAL_RANGE = portee;
			DAMAGE = puissance;
			LONG_RANGE = tir;
			PRICE = prix;
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
