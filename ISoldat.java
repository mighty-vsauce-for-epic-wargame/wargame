package wargame;

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
		
		public boolean getPlayed() {
			return PLAYED;
		}
		
		public static TypesS getTypeSAlea() {
			return values()[(int) (Math.random() * values().length)];
		}
	}
	
	public Position getPosition();
	
	int getHealth();
	
	void setHealth(int l);

	/**gets the visual range of a troop
	 * 
	 * @return visual range
	 */
	int getVisualRange();
	
	/**gets the price of a troop
	 * 
	 * @return visual range
	 */
	int getPrice();

	/**this function executes the attack of the character on soldat
	 * 
	 * @param soldier attacked soldier
	 * @param attackType 1 for melee 2 for long range
	 * @return 0 if attack executed, -1 if attack impossible
	 */
	int combat(Soldat soldier, int attackType);
	
	/**this function changes the position of the soldier to newPos
	 * 
	 * @param newPos
	 */
	void seDeplace(Position newPos);
}
