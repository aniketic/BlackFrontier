package blackfrontier;

import phonegame.*;

public class Laser extends Projectiel {
	// Fields
	private Heelal heelal;
	private int damage;
	private boolean fromEnemy;

	// Constructor

	/**
	 * De eerste constructor voor de Laser class.
	 * 
	 * @param x
	 *            = x coordinaat in de spelwereld.
	 * @param y
	 *            = y coordinaat in de spelwereld.
	 * @param heelal
	 *            = het 'spelwereld' object.
	 * @param plevel
	 *            = Powerup level van het 'speler' object;
	 * 
	 */

	public Laser(int x, int y, Heelal heelal, int plevel) {
		super(x, y, heelal);
		this.heelal = heelal;
		this.damage = 20;
		fromEnemy = false;

		setImage("/images/laser1.png", 3, 19);
		setDirectionSpeed(90, 10);
	}

	/**
	 * De tweede constructor voor de Laser class.
	 * 
	 * @param x
	 *            = x coordinaat in de spelwereld.
	 * @param y
	 *            = y coordinaat in de spelwereld.
	 * @param heelal
	 *            = het 'spelwereld' object.
	 * 
	 */

	public Laser(int x, int y, Heelal heelal) {
		super(x, y, heelal);
		this.heelal = heelal;
		this.damage = 20;
		fromEnemy = true;

		setImage("/images/laser1.png", 3, 19);
		setDirectionSpeed(270, 10);
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

	/**
	 * Setter voor fromEnemy. Controle of de lasers van een vijand zijn of niet.
	 * 
	 * @param fromEnemy
	 */

	public void setFromEnemy(boolean fromEnemy) {
		this.fromEnemy = fromEnemy;
	}

	/**
	 * Getter voor fromEnemy variable. Controle of de lasers van een vijand zijn
	 * of niet.
	 * 
	 * @return damage
	 */

	public boolean getFromEnemy() {
		return fromEnemy;
	}
}
