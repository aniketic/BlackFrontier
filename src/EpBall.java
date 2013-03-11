package blackfrontier;

import phonegame.*;

public class EpBall extends Projectiel {

	// Fields
	private Heelal heelal;
	private int damage;

	// Constructor

	/**
	 * De constructor voor de EpBall class. Overigens gebruik van een methode
	 * die objecten van deze class naar de speler's positie stuurt.
	 * 
	 * @param x
	 *            = x coordinaat in de spelwereld.
	 * @param y
	 *            = y coordinaat in de spelwereld.
	 * @param heelal
	 *            = het 'spelwereld' object.
	 * 
	 */

	public EpBall(int x, int y, Heelal heelal) {
		super(x, y, heelal);
		this.heelal = heelal;
		this.damage = 20;

		setImage("/images/EpBall.png", 20, 24);
		setDirectionSpeed(270, 10);
		moveTowardsAPoint(heelal.getPlayerX(), heelal.getPlayerY());
	}

	// Methods

	/**
	 * Deze method zorgt ervoor dat de image voor speler meer frames gebruikt.
	 */

	public void animate() {
		nextFrame();
	}

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
