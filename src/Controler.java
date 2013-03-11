package blackfrontier;

import java.util.Random;
import phonegame.*;
import phonegame.utils.Tools;

public class Controler implements IAlarmListener {
	// Fields
	private Heelal heelal;
	private Ruimteschip ruimteschip;
	private Speler speler;
	private int event; // event staat voor het aantal keer dat de alarm method
						// gebruikt is
	private int id;

	// Constructor
	/**
	 * De constructor om GameItems in het spel te maken.
	 * 
	 * @param heelal
	 *            Het 'spelwereld' object.
	 * @param id
	 *            Bepaalt wat er gebeurd in de method<br>
	 *            1 - Maak een GameItem<br>
	 *            2 - Speler is dood en komt terug (onstervelijk)<br>
	 *            3 - Speler is niet meer onstervelijk<br>
	 *            4 - Verwijder Ruimteschip nadat hij ontploft<br>
	 *            5 - Timer van de het Projectiel 'Straal'<br>
	 *            6 - Niet bepaalt<br>
	 *            7 - Regelt het schieten van Ruimteschip<br>
	 *            8 - Het vooruit bewegen van de Eindbaas<br>
	 */
	public Controler(Heelal heelal, int id) {
		this.heelal = heelal;
		this.id = id;
		this.speler = null;
		event = 0;

		heelal.setTimer(2, id, this);
	}

	/**
	 * De constructor voor timers met betrekking tot de speler.
	 * 
	 * @param heelal
	 *            Het 'spelwereld' object.
	 * @param id
	 *            Bepaalt wat er gebeurt in de alarm method.
	 * @param speler
	 *            Het speler object.
	 */
	public Controler(Heelal heelal, int id, Speler speler) {
		this.heelal = heelal;
		this.id = id;
		this.speler = speler;
		event = 0;

		spelerSwitch();
	}

	/**
	 * Deze methode heeft een switch om te selecteren wat er bij de speler
	 * gebeurt.
	 */
	public void spelerSwitch() {
		switch (id) {
		// Speler is net gedood en komt terug
		case 2:
			heelal.setTimer(10, id, this);
			break;
		// Wordt niet gebruikt?
		case 4:
			heelal.setTimer(10, id, this);
			break;
		// De timer van de straal
		case 5:
			heelal.setTimer(2, id, this);
		}
	}

	/**
	 * De constructor voor timers met betrekking tot de ruimteschepen.
	 * 
	 * @param heelal
	 *            Het 'spelwereld' object.
	 * @param id
	 *            Bepaalt wat er gebeurt in de alarm method.
	 * @param ruimteschip
	 *            Het Ruimteschip object.
	 */
	public Controler(Heelal heelal, int id, Ruimteschip ruimteschip) {
		this.heelal = heelal;
		this.id = id;
		this.ruimteschip = ruimteschip;

		ruimteschipSwitch();
	}

	/**
	 * Deze methode heeft een switch om te selecteren wat er bij de speler
	 * gebeurt.
	 */
	public void ruimteschipSwitch() {
		switch (id) {
		// Verwijder ruimteschip nadat het ontploft
		case 4:
			heelal.setTimer(10, id, this);
			break;
		// Regelt het schieten van ruimteschip
		case 7:
			heelal.setTimer(10, id, this);
			ruimteschip.schieten();
			break;
		// Het vooruitbewegen van de eindbaas
		case 8:
			heelal.setTimer(20, id, this);
		}
	}

	// Methods
	/**
	 * Deze method zorgt dat er iets gebeurt dat tijdelijk is of na een bepaald
	 * tijd gebeurt.
	 * 
	 * @param id
	 *            Bepaalt wat er gebeurd in de method<br>
	 *            1 - Maak een GameItem<br>
	 *            2 - Speler is dood en komt terug (onstervelijk)<br>
	 *            3 - Speler is niet meer onstervelijk<br>
	 *            4 - Verwijder Ruimteschip nadat hij ontploft<br>
	 *            5 - Timer van de het Projectiel 'Straal'<br>
	 *            6 - Niet bepaalt<br>
	 *            7 - Regelt het schieten van Ruimteschip<br>
	 *            8 - Het vooruit bewegen van de Eindbaas<br>
	 */
	public void alarm(int id) {
		event++;
		switch (id) {
		// Creeër verschillende objecten
		case 1:
			maakGameItem();
			break;
		// Speler is net gedood en komt terug
		case 2:
			speler.terugVanDood();
			heelal.setTimer(20, 3, this);
			break;
		// Speler is niet meer onstervelijk en knippert niet meer
		case 3:
			speler.nietOnstervelijk();
			break;
		// Deleten van een ruimteschip na explosie
		case 4:
			heelal.deleteGameItem(ruimteschip);
			break;
		// De timer van de straal
		case 5:
			straalTimer();
			break;
		// Schieten van een ruimteschip (Op dit moment schiet alleen de eindbaas
		// meerdere keren)
		case 7:
			blijfSchieten();
			break;
		// Stop het bewegen van de eindbaas
		case 8:
			stopEindbaas();
		}
	}

	/**
	 * Deze method bepaald of er een GameItem gemaakt mag worden. Het 20e object
	 * is altijd de eindbaas.
	 */
	public void maakGameItem() {
		if (!heelal.getGameOver()) {
			if (event < 20) {
				checkPowerUp();
			} else if (event == 20) {
				makeItem(4);
			}
		}
	}

	/**
	 * Deze method bepaald of er een PowerUp object gemaakt moet worden of een
	 * ander object.
	 */
	public void checkPowerUp() {
		if (!randomPowerup()) {
			beginFase();
		} else {
			makeItem(3);
		}
	}

	/**
	 * Deze returned 1 op de 10 keer true.
	 * 
	 * @return true = maak powerup, false = niet.
	 */
	public boolean randomPowerup() {
		Random rand = new Random();
		int kans = rand.nextInt(10);
		boolean spawn = false;

		if (kans == 1)
			spawn = true;

		return spawn;
	}

	/**
	 * Deze method bepaald of er een Astroide of Ruimteschip object gemaakt moet
	 * worden.
	 */
	public void beginFase() {
		if (event < 10) {
			makeItem(1);
		} else {
			makeItem(2);
		}
	}

	/**
	 * Deze method regelt de 'afkoel' mechanisme voor de Straal en zorgt ervoor
	 * dat de 'charge' counter aftelt.
	 */
	public void straalTimer() {
		int charge = speler.getCharge();

		if (charge > 0) {
			charge--;
			speler.setCharge(charge);
		}

		heelal.setTimer(2, id, this);
	}

	/**
	 * Deze method zorgt ervoor dat de eindbaas blijft schieten. Het is ook
	 * mogelijk om normale ruimteschepen vaker te laten schieten.
	 */
	public void blijfSchieten() {
		if (ruimteschip instanceof Eindbaas && ruimteschip.getAlive()
				&& !heelal.getGameOver()) {
			heelal.setTimer(20, id, this);
			ruimteschip.schieten();
		}
	}

	/**
	 * Deze method zorgt ervoor dat de eindbaas stopt met bewegen.
	 */
	public void stopEindbaas() {
		if (ruimteschip instanceof Eindbaas) {
			ruimteschip.setDirectionSpeed(0, 0);
		}
	}

	/**
	 * Deze method maakt een GameItem op een willekeurige locatie, na een
	 * willekeurig aantal miliseconden.
	 * 
	 * @param soort
	 *            1 = astroide, 2 = ruimteschip, 3 = powerup
	 */
	public void makeItem(int soort) {
		Random rand = new Random();
		int timer = rand.nextInt(20) + 20;
		int type = rand.nextInt(4);
		int x = rand.nextInt(200);
		int y = 0;

		selectSoortItem(soort, x, y, type);

		if (!heelal.getGameOver()) {
			heelal.setTimer(timer, id, this);
		}
	}

	/**
	 * Deze method kiest wat voor GameItem gemaakt wordt.
	 * 
	 * @param soort
	 *            Soort GameItem
	 * @param x
	 *            x-positie
	 * @param y
	 *            y-positie
	 * @param type
	 *            type van de GameItem (indien van toepassing)
	 */
	public void selectSoortItem(int soort, int x, int y, int type) {
		switch (soort) {
		case 1:
			makeAstroide(x, y, type);
			break;
		case 2:
			makeShip(x, y);
			break;
		case 3:
			makePowerup(x, y, type);
			break;
		case 4:
			makeEindbaas();
		}
	}

	/**
	 * Deze methode plaatst een astroide in het spel
	 * 
	 * @param x
	 *            x-positie
	 * @param y
	 *            y-positie
	 * @param type
	 *            soort astroide
	 */
	public void makeAstroide(int x, int y, int type) {
		Astroide a = new Astroide(heelal, type);

		a.setPosition(x, y);
		heelal.addGameItem(a);
	}

	/**
	 * Deze methode plaatst een ruimteschip in het spel
	 * 
	 * @param x
	 *            x-positie
	 * @param y
	 *            y-positie
	 */
	public void makeShip(int x, int y) {
		Ruimteschip r = new Ruimteschip(heelal);

		r.setPosition(x, y);
		heelal.addGameItem(r);
		r.maakSchietControler();
	}

	/**
	 * Deze methode plaatst een powerup in het spel
	 * 
	 * @param x
	 *            x-positie
	 * @param y
	 *            y-positie
	 * @param type
	 *            soort powerup image
	 */
	public void makePowerup(int x, int y, int type) {
		PowerUp p = new PowerUp(heelal, type);
		p.setPosition(x, y);
		heelal.addGameItem(p);
	}

	/**
	 * Deze methode plaatst een eindbaas in het spel
	 */
	public void makeEindbaas() {
		Eindbaas e = new Eindbaas(heelal);
		heelal.addGameItem(e);
		e.maakSchietControler();
		e.stopDirection();
	}
}
