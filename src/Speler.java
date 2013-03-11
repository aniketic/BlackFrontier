package blackfrontier;

import phonegame.*;

public class Speler extends GamePlayer {
	// Fields
	private Heelal heelal;
	private Controler cnt;
	private int health;
	private int lives;
	private int plevel; // Het power level van het wapen
	private int punten;
	private int charge;
	private boolean alive;
	private boolean indestructible;

	// Constructor
	/**
	 * Constructor voor de Speler class.
	 * 
	 * @param heelal
	 *            Het 'spelwereld' object.
	 */
	public Speler(Heelal heelal) {
		this.heelal = heelal;
		health = 100;
		lives = 2;
		plevel = 0;
		charge = 0;
		punten = 0;
		alive = true;
		indestructible = false;

		// Deze controler zorgt voor de timer van de Straal
		cnt = new Controler(heelal, 5, this);

		setPosition(100, 200);
		setImage("/images/spelerAni.png", 32, 47);
	}

	// Methods
	/**
	 * Deze method zorgt ervoor dat de image voor speler meer frames gebruikt.
	 */
	public void animate() {
		nextFrame();
	}

	/**
	 * Deze method zorgt ervoor dat de speler omhoog beweegt.
	 */
	public void moveUp() {
		if (getY() - 8 >= heelal.getMinY() && alive == true) {
			movePlayer(getX(), getY() - 8);
		}
	}

	/**
	 * Deze method zorgt ervoor dat de speler omlaag beweegt.
	 */
	public void moveDown() {
		if (getY() + 8 <= heelal.getMaxY() && alive == true) {
			movePlayer(getX(), getY() + 8);
		}
	}

	/**
	 * Deze method zorgt ervoor dat de speler naar links beweegt.
	 */
	public void moveLeft() {
		if (getX() - 8 >= heelal.getMinX() && alive == true) {
			movePlayer(getX() - 8, getY());
		}
	}

	/**
	 * Deze method zorgt ervoor dat de speler naar rechts beweegt.
	 */
	public void moveRight() {
		if (getX() + 8 <= heelal.getMaxX() && alive == true) {
			movePlayer(getX() + 8, getY());
		}
	}

	/**
	 * Deze method zorgt ervoor dat de speler kan schieten en met wat voor
	 * aanval.
	 */
	public void fire() {
		if (!heelal.getGameOver()) {
			switch (plevel) {
			case 0:
				singleBlaster();
				break;
			case 1:
				dualBlaster();
				break;
			case 2:
				straal();
				break;
			default:
				dualBlaster();
				straal();
			}
		}
	}

	/**
	 * Deze method zorgt voor één laser.
	 */
	public void singleBlaster() {
		Laser l = new Laser(getX() + 14, getY(), heelal, plevel);
		heelal.addGameItem(l);
	}

	/**
	 * Deze method zorgt voor twee lasers.
	 */
	public void dualBlaster() {
		Laser d1 = new Laser(getX() + -1, getY() + 10, heelal, plevel);
		Laser d2 = new Laser(getX() + 29, getY() + 10, heelal, plevel);
		heelal.addGameItem(d1);
		heelal.addGameItem(d2);
	}

	/**
	 * Deze method zorgt voor een straal.
	 */
	public void straal() {
		charger();
		vuurCharge();
	}

	/**
	 * Deze method is deel van de timer mechaniek van de straal. Wanneer er met
	 * de straal geschoten word gaat de charge counter omhoog. Er zit een limit
	 * van 10 op charge, anders duurt het te lang om weer te kunnen schieten.
	 */
	public void charger() {
		if (charge < 10) {
			charge++;
		}
	}

	/**
	 * Deze method controlerd of charge laag genoeg is om te mogen schieten.
	 */
	public void vuurCharge() {
		if (charge < 5) {
			Straal s = new Straal(getX() + 8, getY() + -380, heelal);
			heelal.addGameItem(s);
		}
	}

	/**
	 * Deze methode set de charge.
	 * 
	 * @param charge
	 *            Bepaalt of de straal afgevuurd mag worden.
	 */
	public void setCharge(int charge) {
		this.charge = charge;
	}

	/**
	 * Deze method returned de charge.
	 * 
	 * @return charge bepaalt of de straal afgevuurd mag worden.
	 */
	public int getCharge() {
		return charge;
	}

	/**
	 * Deze method bepaalt wat er gebeurd wanneer de speler met een ander object
	 * botst. Daarna wordt de health van de speler gecontroleerd.
	 */
	public void collisionOccured(GameItem collidedItem) {
		if (collidedItem instanceof Astroide) {
			astroidCollision(collidedItem);
		} else if (collidedItem instanceof Ruimteschip) {
			ruimteschipCollision(collidedItem);
		} else if (collidedItem instanceof PowerUp) {
			powerUpCollision(collidedItem);
		} else if (collidedItem instanceof Laser) {
			laserCollision(collidedItem);
		} else if (collidedItem instanceof EpBall) {
			epBallCollision(collidedItem);
		}

		checkHealth();
	}

	/**
	 * Deze method bepaalt wat er gebeurt waneer de speler met een Astroide
	 * botst.
	 * 
	 * @param collidedItem
	 *            Is de Astroide.
	 */
	public void astroidCollision(GameItem collidedItem) {
		if (!indestructible) {
			collision();
		}
		heelal.deleteGameItem(collidedItem);
	}

	/**
	 * Deze method bepaalt wat er gebeurt waneer de speler met een Ruimteschip
	 * botst.
	 * 
	 * @param collidedItem
	 *            Is het Ruimteschip.
	 */
	public void ruimteschipCollision(GameItem collidedItem) {
		if (((Ruimteschip) collidedItem).getAlive() && !indestructible) {
			collision();
			((Ruimteschip) collidedItem).setExplosie();
		}
	}

	/**
	 * Deze method bepaalt wat er gebeurt waneer de speler met een PowerUp
	 * botst.
	 * 
	 * @param collidedItem
	 *            Is de PowerUp
	 */
	public void powerUpCollision(GameItem collidedItem) {
		if (plevel < 3)
			plevel++;
		heelal.deleteGameItem(collidedItem);
	}

	/**
	 * Deze method bepaalt wat er gebeurt waneer de speler met een Laser botst.
	 * 
	 * @param collidedItem
	 *            Is de laser.
	 */
	public void laserCollision(GameItem collidedItem) {
		if (((Laser) collidedItem).getFromEnemy()) {
			laserHit();
			heelal.deleteGameItem(collidedItem);
		}
	}

	/**
	 * Deze method bepaalt wat er gebeurt waneer de speler met een EpBall botst.
	 * 
	 * @param collidedItem
	 *            Is de EpBall.
	 */
	public void epBallCollision(GameItem collidedItem) {
		ballHit();
		heelal.deleteGameItem(collidedItem);
	}

	/**
	 * Deze method haalt 50 punten van de speler's health af na een botsing.
	 */
	public void collision() {
		health -= 50;
		heelal.setHealthDisplay();
	}

	/**
	 * Deze method haalt 10 punten van de speler's health af na een hit van een
	 * Laser.
	 */
	public void laserHit() {
		health -= 10;
		heelal.setHealthDisplay();
	}

	/**
	 * Deze method haalt 30 punten van de speler's health af na een hit van een
	 * EpBall.
	 */
	public void ballHit() {
		health -= 30;
		heelal.setHealthDisplay();
	}

	/**
	 * Deze method controleert hoeveel health een speler heeft. 0 health
	 * betekent dat de speler dood is.
	 */
	public void checkHealth() {
		if (health <= 0 && alive == true) {
			spelerDood();
			checkLevens();
		}
	}

	/**
	 * Deze method controleert hoeveel levens de speler heeft. Minder dan 0
	 * betekent game over.
	 */
	public void checkLevens() {
		if (lives < 0) {
			gameOver();
		} else {
			cnt = new Controler(heelal, 2, this);
		}
	}

	/**
	 * Deze method regelt wat er gebeurt wanneer de speler dood gaat.
	 */
	public void spelerDood() {
		lives--;
		plevel = 0;
		heelal.setLivesDisplay();
		setImage("/images/expAni.png", 64, 63);
		alive = false;
	}

	/**
	 * Deze method regelt wat er gebeurt als het game over is.
	 */
	public void gameOver() {
		AchtergrondObject gameOver = new AchtergrondObject(heelal, 0, 2);
		heelal.setGameOver(true);
		heelal.deleteAllGameItems();
		heelal.addGameItem(gameOver);
	}

	/**
	 * Deze method regelt wat er gebeurt wanneer de speler met een tile botst.
	 * Op dit moment wordt er geen gebruik gemaakt van tiles.
	 */
	public void collisionOccured(int tilePattern, boolean hor, int position) {

	}

	/**
	 * Deze method plaatst de speler terug in het spel als hij/zij buiten het
	 * scherm bevindt.
	 */
	public void outsideWorld() {
		setPosition(340, 240);
	}

	/**
	 * Vier toetsen van het numerieke keyboard: niks doen
	 */
	public void pressedButtonA() { // System.out.println("A");
	}

	public void pressedButtonB() { // System.out.println("B");
	}

	public void pressedButtonC() { // System.out.println("C");
	}

	public void pressedButtonD() { // System.out.println("D");
	}

	/**
	 * Deze method geeft het aantal health dat de speler heeft.
	 * 
	 * @return health is het aantal health van de speler.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Deze method geeft het aantal lives dat de speler heeft.
	 * 
	 * @return lives is het aantal lives van de speler.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Deze method maakt de speler levent of dood.
	 * 
	 * @param alive
	 *            true = levend, false is dood.
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * Deze method set de health van de speler.
	 * 
	 * @param health
	 *            Aantal wat de nieuwe health van de speler wordt.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Deze method maakt de speler onstervelijk of niet.
	 * 
	 * @param indestructible
	 *            true = onstervelijk, false is niet.
	 */
	public void setIndestructible(boolean indestructible) {
		this.indestructible = indestructible;
	}

	/**
	 * Deze method set het aantal punten van de speler.
	 * 
	 * @param punten
	 *            Het aantal punten van de speler.
	 */
	public void setPunten(int punten) {
		this.punten += punten;
	}

	/**
	 * Deze method geeft het aantal punten van de speler.
	 * 
	 * @return punten is het aantal punten van de speler.
	 */
	public int getPunten() {
		return punten;
	}

	/**
	 * Deze method bepaalt wat er gebeurt wanneer de speler terug van de dood
	 * komt.
	 */
	public void terugVanDood() {
		setImage("/images/spelerAniInv.png", 32, 47);
		setAlive(true);
		setPosition(100, 200);
		setIndestructible(true);
		setHealth(100);
		heelal.setHealthDisplay();
	}

	/**
	 * Deze method maakt de speler weer stervelijk.
	 */
	public void nietOnstervelijk() {
		setImage("/images/spelerAni.png", 32, 47);
		setIndestructible(false);
	}
}
