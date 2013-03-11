package blackfrontier;

import phonegame.*;

public class Astroide extends MoveableGameItem implements IStepListener {
	// Fields
	private Heelal heelal;
	private int health;

	// Constructor

	/**
	 * De Constructor voor de Astroide class. Door middel van een type int wordt
	 * bepaald wat voor plaatje voor Astroide gebruikt wordt. Tevens wordt er
	 * een steplistener gemaakt in heelal.
	 * 
	 * @param heelal
	 *            = het 'spelwereld' object.
	 * @param type
	 *            = het type Astroide object.
	 */

	public Astroide(Heelal heelal, int type) {
		this.heelal = heelal;
		health = 100;

		switch (type) {
		case 0:
			setImage("/images/astroide1.png", 24, 32);
			break;
		case 1:
			setImage("/images/astroide2.png", 32, 48);
			break;
		case 2:
			setImage("/images/astroide3.png", 18, 26);
			break;
		case 3:
			setImage("/images/astroide4.png", 48, 48);
		}

		setDirectionSpeed(270, 5);

		heelal.addStepListener(this);
	}

	// Methods

	/**
	 * Stepaction methode die bepaald wat een Astroide moet doen na stepnr
	 * aantal stappen.
	 * 
	 * @param stepnr
	 *            = hoeveeleid stappen die nodig zijn om iets uit te voeren.
	 */

	public void stepAction(int stepnr) {
	}

	/**
	 * Deze methode bepaalt wat er gebeurt als iets in botsing komt met een
	 * Astroide zoals een projectiel. De astroide verliest health en kan zozeer
	 * verwijderd worden als hij geen health meer heeft.
	 * 
	 * @param collidedItem
	 *            = Het object waar de Astroide mee 'botst'.
	 */

	public void collisionOccured(GameItem collidedItem) {
		if (collidedItem instanceof Projectiel) {
			heelal.deleteGameItem(collidedItem);
			setDamage(((Projectiel) collidedItem).getDamage());
		}
	}

	/**
	 * Deze methode bepaalt wat er gebeurt als een Astroide buiten de spelwereld
	 * komt. Deze wordt dan verwijderd.
	 */

	public void outsideWorld() {
		heelal.deleteGameItem(this);
	}

	/**
	 * Deze methode zorgt ervoor dat een Astroide health verliest naarmate hij
	 * damage krijgt. Uiteindelijk wordt de Astroide verwijderd als hij health
	 * meer heeft en krijgt de speler punten hiervoor. Ook wordt de
	 * puntendisplay geupdate.
	 * 
	 * @param damage
	 *            = de schade waarde die toegepast wordt in de methode. Bepalend
	 *            wat de health waardes worden.
	 */

	public void setDamage(int damage) {
		health = health - damage;

		if (health <= 0) {
			Speler speler = heelal.getSpeler();
			heelal.deleteGameItem(this);
			speler.setPunten(+5);
			heelal.setPointsDisplay();
		}
	}
}
