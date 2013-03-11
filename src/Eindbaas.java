package blackfrontier;

import phonegame.*;

public class Eindbaas extends Ruimteschip implements IStepListener {

	// Fields
	private Heelal heelal;
	private boolean alive;
	private int health;
	private int shield;
	private Controler cnt;
	private boolean hasShield;
	private boolean halfLife;

	private int elevel;
	private int coordext1;
	private int coordext12;
	private int coordext2;
	private int coordext22;
	private boolean l1exists;
	private boolean l2exists;

	// Constructor

	/**
	 * De Constructor voor de Eindbaas class
	 * 
	 * @param heelal
	 *            = het 'spelwereld' object.
	 */

	public Eindbaas(Heelal heelal) {
		super(heelal);
		health = 2500;
		shield = 2000;
		elevel = 0;
		hasShield = true;
		alive = true;
		halfLife = false;

		coordext1 = 25;
		coordext12 = 40;
		coordext2 = 205;
		coordext22 = 190;
		l1exists = false;
		l2exists = false;

		this.heelal = heelal;
		setImage("/images/boss1shield.png", 231, 133);
		setPosition(4, -133);
		setDirectionSpeed(270, 5);
	}

	// Methods

	/**
	 * Deze method zorgt ervoor dat de eindbaas shield en health verliest
	 * naarmate hij damage krijgt. Tevens worden de eindbaas phases bepaald
	 * naarmate hij shield en/of health verloren heeft. Dit in samenstelling met
	 * het veranderen van het plaatje. Dit allemaal door middel van methods
	 * bepalend op shield en health.
	 * 
	 * @param damage
	 *            = de schade waarde die toegepast wordt in de methode. Bepalend
	 *            wat de shield en health waardes worden.
	 * 
	 */

	public void setDamage(int damage) {

		if (shield > 0) {
			Shield(damage);
		}
		if (shield <= 0) {
			NoShield();
			Health(damage);
		}
		if (health <= 0) {
			Dead();
		}
	}

	/**
	 * Deze methode haalt schield waarde omlaag naarmate hij damage krijgt van
	 * een object.
	 * 
	 * @param damage
	 *            = de schade waarde die toegepast wordt in de methode. Bepalend
	 *            wat de shield en health waardes worden.
	 */

	public void Shield(int damage) {
		shield -= damage;
	}

	/**
	 * Deze methode verandert de status van de eindbaas nadat hij geen schild
	 * waarde meer heeft.
	 */

	public void NoShield() {
		if (hasShield) {
			elevel = 1;
			hasShield = false;
			setImage("/images/boss1.png", 225, 127);
			setPosition(getX() + 3, getY() + 3);
		}
	}

	/**
	 * Deze methode haalt health waarde omlaag naarmate hij damage krijgt van
	 * een object. Tevens verandert het de status van de eindbaas nadat hij nog
	 * maar een bepaald hoeveelheid health heeft.
	 * 
	 * @param damage
	 *            = de schade waarde die toegepast wordt in de methode. Bepalend
	 *            wat de shield en health waardes worden.
	 */

	public void Health(int damage) {
		health -= damage;

		if (!halfLife && health <= 1000) {
			halfLife = true;
			elevel = 2;
			setImage("/images/boss1Ani.png", 225, 127);
		}
	}

	/**
	 * Deze methode verandert de status van de eindbaas nadat hij geen leven
	 * meer heeft en dus dood is. Eindbaas wordt verwijdert en de speler krijgt
	 * punten voor het vernietigen van de eindbaas.
	 */

	public void Dead() {
		alive = false;
		heelal.deleteGameItem(this);
		Speler speler = heelal.getSpeler();
		speler.setPunten(+100);
		heelal.setPointsDisplay();
	}

	/**
	 * Deze methode creeert een nieuwe controller die uiteindelijk ervoor zorgt
	 * dat de Eindbaas na een bepaalde duur stopt met bewegen nadat hij gemaakt
	 * is.
	 */

	public void stopDirection() {
		Controler stoppen = new Controler(heelal, 8, this);
	}

	/**
	 * Deze methode is een override op outsideWorld methode in Ruimteschip.
	 * Zorgt ervoor dat de Eindbaas niet verwijderd wordt als hij buiten de
	 * wereld is. Maakt het bewegen van buiten naar binnen de viewport mogelijk.
	 */

	public void outsideWorld() {
	}

	/**
	 * Deze methode creeert een nieuwe controller die uiteindelijk ervoor zorgt
	 * dat de Eindbaas op een interval schiet en niet eenmalig.
	 */

	public void maakSchietControler() {
		cnt = new Controler(heelal, 7, this);
	}

	/**
	 * Deze methode zorgt voor een variatie op het schieten van de eindbaas.
	 * Geregeld door een switch met elevel var die de vuurphase bepaald
	 */

	public void schieten() {
		switch (elevel) {
		case 0:
			Vuurphase0();
			break;
		case 1:
			Vuurphase1();
			break;
		case 2:
			Vuurphase2();
			break;
		}
	}

	/**
	 * Deze methode bevat de inhoud wat vuurphase 0 van de eindbaas inhoud.
	 * LaserSalvo method is vuurphase 0.
	 * */

	public void Vuurphase0() {
		LaserSalvo();
	}

	/**
	 * Deze methode bevat de inhoud wat vuurphase 1 van de eindbaas inhoud.
	 * BallSalvo method is vuurphase 1.
	 * */

	public void Vuurphase1() {
		BallSalvo();
	}

	/**
	 * Deze methode bevat de inhoud wat vuurphase 2 van de eindbaas inhoud.
	 * LaserSalvo met de BallSalvo method is vuurphase 2.
	 * */

	public void Vuurphase2() {
		LaserSalvo();
		BallSalvo();
	}

	/**
	 * Deze methode bepaalt wat LaserSalvo is. Een viertal lasers worden gemaakt
	 * met een var voor X coordinatie en twee booleans worden op true gezet. Ook
	 * roept het de salvogen methode op als l1exists en l2exists waar zijn.
	 */

	public void LaserSalvo() {
		Laser l1 = new Laser(getX() + coordext1, getY() + 135, heelal);
		Laser l12 = new Laser(getX() + coordext12, getY() + 135, heelal);
		heelal.addGameItem(l1);
		heelal.addGameItem(l12);
		l1exists = true;
		Laser l2 = new Laser(getX() + coordext2, getY() + 135, heelal);
		Laser l22 = new Laser(getX() + coordext22, getY() + 135, heelal);
		heelal.addGameItem(l2);
		heelal.addGameItem(l22);
		l2exists = true;
		if (l1exists && l2exists) {
			salvogen();
		}
	}

	/**
	 * Deze methode genereert variatie op de plekken waar de lasers gevuurd
	 * worden op de eindbaas. Dit met behulp van een tweetal ints die per keer
	 * veranderen. UIteindelijk ook een methode aanroep wanneer deze vars
	 * gereset moeten worden dioor middel van een waarde vereiste.
	 */

	public void salvogen() {
		coordext1 += 20;
		coordext12 += 20;
		coordext2 -= 20;
		coordext22 -= 20;
		l1exists = false;
		l2exists = false;
		if (coordext1 > 205 && coordext12 > 205 && coordext2 < 25
				&& coordext22 < 25) {
			resetcoords();
		}
	}

	/**
	 * Deze methode bepaalt dat de coordinaat variablen gereset worden voor de
	 * salvogen methode.
	 */

	public void resetcoords() {
		coordext1 = 25;
		coordext12 = 40;
		coordext2 = 205;
		coordext22 = 190;
	}

	/**
	 * Deze methode bepaalt wat BallSalvo is. Een tweetal flikkerende ballen
	 * worden gemaakt. Deze twee ballen worden afgevuurd op de speler.
	 */

	public void BallSalvo() {
		EpBall eb = new EpBall(getX() + 77, getY() + 135, heelal);
		EpBall eb1 = new EpBall(getX() + 136, getY() + 135, heelal);
		heelal.addGameItem(eb);
		heelal.addGameItem(eb1);
	}

	/**
	 * Getter voor alive variable. Gebruikt om te controleren of Eindbaas dood
	 * is of niet.
	 * 
	 * @return alive
	 */

	public boolean getAlive() {
		return alive;
	}
}