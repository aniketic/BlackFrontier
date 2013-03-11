package blackfrontier;

import phonegame.*;

public class Ruimteschip extends MoveableGameItem implements IStepListener {
	// Fields
	private Heelal heelal;
	private int health;
	private boolean alive;
	private Controler schietControler;

	// Constructor

	/**
	 * De Constructor voor de Eindbaas class. Tevens wordt er een steplistener
	 * gemaakt in heelal.
	 * 
	 * @param heelal
	 *            = het 'spelwereld' object.
	 */

	public Ruimteschip(Heelal heelal) {
		this.heelal = heelal;
		health = 200;
		alive = true;

		setImage("/images/enemy1.png", 48, 48);

		setDirectionSpeed(270, 5);

		heelal.addStepListener(this);
	}

	// Methods

	/**
	 * Stepaction methode die bepaald wat een Ruimteschip moet doen na stepnr
	 * aantal stappen.
	 * 
	 * @param stepnr
	 *            = hoeveeleid stappen die nodig zijn om iets uit te voeren.
	 */

	public void stepAction(int stepnr) {
	}

	/**
	 * Deze methode bepaalt wat er gebeurt als iets in botsing komt met een
	 * Ruimteschip zoals een projectiel. Het ruimteschip verliest health en kan
	 * zozeer verwijderd worden als hij niet meer leeft.
	 * 
	 * @param collidedItem
	 *            = Het object waar het ruimteschip mee 'botst'.
	 */

	public void collisionOccured(GameItem collidedItem) {
		if (collidedItem instanceof Projectiel) {
			setDamage(((Projectiel) collidedItem).getDamage());
			if (getAlive())
				heelal.deleteGameItem(collidedItem);
		}
	}

	/**
	 * Deze methode bepaalt wat er gebeurt als een Ruimteschip buiten de
	 * spelwereld komt. Deze wordt dan verwijderd.
	 */

	public void outsideWorld() {
		heelal.deleteGameItem(this);
	}

	/**
	 * Deze methode zorgt ervoor dat een Ruimteschip health verliest naarmate
	 * hij damage krijgt. Uiteindelijk levert het een explosie van het
	 * Ruimteschip op.
	 * 
	 * @param damage
	 *            = de schade waarde die toegepast wordt in de methode. Bepalend
	 *            wat de health waardes worden.
	 */

	public void setDamage(int damage) {
		health = health - damage;

		if (health <= 0) {
			setExplosie();
		}
	}

	/**
	 * Deze methode zorgt voor een explosie animatie nadat een Ruimteschip niet
	 * meer leeft en dat deze na een bepaalde duurde verwijderd wordt door
	 * middel van het creeeren van een timer controller. Update ook de
	 * hoeveelheid punten van de speler en update de puntendisplay.
	 */

	public void setExplosie() {
		if (alive) {
			alive = false;
			setImage("/images/expAni.png", 64, 63);
			Controler cnt = new Controler(heelal, 4, this);
			Speler speler = heelal.getSpeler();
			speler.setPunten(+10);
			heelal.setPointsDisplay();
		}
	}

	/**
	 * Getter voor alive variable. Gebruikt om te controleren of Ruimteschip
	 * dood is of niet.
	 * 
	 * @return alive
	 */

	public boolean getAlive() {
		return alive;
	}

	/**
	 * Deze methode creeert een nieuwe controller die uiteindelijk ervoor zorgt
	 * dat een Ruimteschip op een interval schiet en niet eenmalig.
	 */

	public void maakSchietControler() {
		schietControler = new Controler(heelal, 7, this);
	}

	/**
	 * Deze methode zorgt voor het creeren van schietobjecten die een
	 * Ruimteschip afschiet en deze in de spelwereld geplaatst worden. Het gaat
	 * hier om een object van de Laser classe.
	 */

	public void schieten() {
		Laser l = new Laser(getX() + 22, getY() + 48, heelal);
		heelal.addGameItem(l);
	}
}