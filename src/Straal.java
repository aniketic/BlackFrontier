package blackfrontier;

import phonegame.*;

public class Straal extends Projectiel {
	// Fields
	private Heelal heelal;
	private int damage;

	// Constructor

	/**
	 * De constructor voor de Straal class.
	 * 
	 * @param x
	 *            = x coordinaat in de spelwereld.
	 * @param y
	 *            = y coordinaat in de spelwereld.
	 * @param heelal
	 *            = het 'spelwereld' object.
	 * 
	 */

	public Straal(int x, int y, Heelal heelal) {
		super(x, y, heelal);
		this.heelal = heelal;
		this.damage = 40;
		setImage("/images/straal.png", 18, 400);
		setDirectionSpeed(90, 90);
		setPosition(x, y);
	}

	// Methods

	/**
	 * Getter voor damage variable. Gebruikt om te controleren hoeveel schade
	 * dit object doet.
	 * 
	 * @return damage
	 */

	public int getDamage() {
		return damage;
	}
}
