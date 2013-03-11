package blackfrontier;

import phonegame.*;

public class Heelal extends GameEngine implements IMenuListener {
	// Fields
	private Speler speler;
	private Controler cnt;
	private boolean gameOver = false;
	private GameDashboard mainDB;
	private static final String exitMenuItem = "Exit";
	private static final String pauseMenuItem = "Pause";
	private static final String playMenuItem = "Play";
	private static final String[] heelalmenu = { playMenuItem, pauseMenuItem,
			exitMenuItem };

	// Constructor
	/**
	 * Constructor voor de heelal class
	 */
	public Heelal() {
		// Start game engine en bepaal het speelveld
		super();
		setBounds(0, 0, 210, 250);
		setBackgroundColor(0, 0, 0);
		buildEnvironment();
		maakSterren();
		maakSpeler();
		maakDashboard();
		makeMenu(heelalmenu, this);

		// Deze Controler zorgt ervoor dat periodiek GameItems gemaakt worden
		cnt = new Controler(this, 1);

		setupGame();
	}

	// Methodes
	/**
	 * Hier wordt het dashboard gemaakt.
	 */
	public void maakDashboard() {
		mainDB = new GameDashboard();
		mainDB.setPosition(0, 274);
		mainDB.setForegroundColor(255, 255, 255);
		mainDB.setBackgroundColor(50, 50, 50);
		mainDB.setSize(240, 16);
		mainDB.addItem("Health", printHealth());
		mainDB.addItem("Lives", printLives());
		mainDB.addItem("Points", printPoints());
		addGameDashboard(mainDB);
	}

	/**
	 * Hier wordt het Speler object gemaakt en in de wereld geplaatst.
	 */
	public void maakSpeler() {
		speler = new Speler(this);
		this.addPlayer(speler);
		this.setPlayerPositionOnScreen(PLAYER_HCENTER | PLAYER_VCENTER);
		this.setPlayerPositionTolerance(0.3, 0.3);
	}

	/**
	 * Deze method zorgt ervoor dat de menu opties werken.
	 */
	public void menuAction(String label) {
		if (label.equals(exitMenuItem)) {
			exitGame();
		} else if (label.equals(pauseMenuItem)) {
			stopGame();
		} else if (label.equals(playMenuItem)) {
			startGame();
		}
	}

	/**
	 * Deze method return de X positie van de speler.
	 * 
	 * @return X = X-positie van de speler
	 */
	public int getPlayerX() {
		return speler.getX();
	}

	/**
	 * Deze method return de Y-positie van de speler.
	 * 
	 * @return Y = Y-positie van de speler
	 */
	public int getPlayerY() {
		return speler.getY();
	}

	/**
	 * Deze method bouwt de map waarin de speler zich bevindt.
	 */
	public void buildEnvironment() {
		byte[][] map = new byte[29][24];

		//Vul de complete map met nullen (er worden geen tiles gebruikt)
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 24; j++) {
				map[i][j] = 0;
			}
		}
		
		//Deze array bevat tiles die gebruikt kunnen worden om in de map te plaatsen
		String[] imagePaths = { "/images/tile1.png", "/images/tile2.png",
		"/images/ruimte.png" };

		this.setTileImages(imagePaths, 10, 10);
		this.addEnvironmentMap(map, 0, 0);
	}

	/**
	 * Deze method returned de correcte health aantal in een String.
	 * De toegevoegde spaties zorgen ervoor dat de opmaak van de dashboard consistent blijft.
	 * 
	 * @return strHealth = Aantal health
	 */
	public String printHealth() {
		String strHealth = "";
		int health = speler.getHealth() / 10;

		//Voeg "|" toe voor elke 'health' punt dat de speler heeft. (gedeelt door 10)
		for (int i = 0; i < health; i++) {
			strHealth += "|";
		}
		
		//Voeg " " toe voer elke 'health' punt dat de speler mist. (gedeelt door 10)
		int empty = 10 - health;
		for (int i = 0; i < empty; i++) {
			strHealth += " ";
		}

		return strHealth;
	}

	/**
	 * Deze method returned de correcte lives aantal in een String.
	 * 
	 * @return strLives = Aantal lives
	 */
	public String printLives() {
		int lives = speler.getLives();
		String strLives = "";

		//Voeg '|' toe voor elke 'lives' punt  dat de speler heeft. (gedeelt door 10)
		for (int i = 0; i < lives; i++) {
			strLives += "|";
		}

		return strLives;
	}

	/**
	 * Deze method returned de correcte points aantal in een String.
	 * 
	 * @return strPoints = het aantal punten
	 */
	public String printPoints() {
		String strPoints = "";
		strPoints = "" + speler.getPunten();

		return strPoints;
	}

	/**
	 * Deze method set de health display.
	 */
	public void setHealthDisplay() {
		mainDB.setItemValue("Health", printHealth());
	}

	/**
	 * Deze method set de lives display.
	 */
	public void setLivesDisplay() {
		mainDB.setItemValue("Lives", printLives());
	}
	
	/**
	 * Deze method set de points display.
	 */
	public void setPointsDisplay() {
		mainDB.setItemValue("Points", printPoints());
	}

	/**
	 * Deze method returned of de speler game over is of niet.
	 * 
	 * @return gameOver is true wanneer de levens op zijn
	 */
	public boolean getGameOver() {
		return gameOver;
	}

	/**
	 * Deze method zet het spel op gameOver of niet.
	 * 
	 * @param gameOver
	 *            is true wanneer de levens op zijn
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Deze method returned het speler object.
	 * 
	 * @return speler = het speler object
	 */
	public Speler getSpeler() {
		return speler;
	}

	/**
	 * Deze method maakt 2 AchtergrondObjecten die sterren zijn.
	 * Er zijn twee nodig om de illusie van oneindigheid te behouden.
	 */
	public void maakSterren() {
		AchtergrondObject sterren1 = new AchtergrondObject(this, -290, 1);
		AchtergrondObject sterren2 = new AchtergrondObject(this, 0, 1);
		this.addGameItem(sterren1);
		this.addGameItem(sterren2);
	}
}
