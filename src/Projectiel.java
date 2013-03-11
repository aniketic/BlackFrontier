package blackfrontier;

import phonegame.*;
import phonegame.MoveableGameItem;

public abstract class Projectiel extends MoveableGameItem {

	// Fields
	private Heelal heelal;

	// Constructor

	/**
	 * De constructor voor de Projectiel class.
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

	public Projectiel(int x, int y, Heelal heelal) {
		this.heelal = heelal;
		setPosition(x, y);
	}

	// Methods

	/**
	 * Deze methode bepaalt wat er gebeurt als een projectiel buiten de
	 * spelwereld komt. Deze wordt dan verwijderd.
	 */
	
	public void outsideWorld() {
		heelal.deleteGameItem(this);
	}

	/**
	 * Deze methode is abstract en dwingt subclasses van projectiel om de
	 * getDamage getter te implementeren.
	 * 
	 * @return damage
	 */
	
	public abstract int getDamage();
}
