package blackfrontier;

import phonegame.*;
import phonegame.MoveableGameItem;

public class AchtergrondObject extends MoveableGameItem {
	// Fields
	private Heelal heelal;
	private int posY; // y-positie van het object

	// Constructor
	/**
	 * De constructor voor het AchtergrondObject class.
	 * 
	 * @param heelal
	 *            Het 'spelwereld' object.
	 * @param posY
	 *            De y-positie van het object.
	 * @param type
	 *            De type van het object.
	 */
	public AchtergrondObject(Heelal heelal, int posY, int type) {
		this.heelal = heelal;
		this.posY = posY;

		setPosition(0, posY);
		bepaalType(type);
	}

	// Methods
	/**
	 * Deze method bepaalt wat de type is van het object en wat het moet doen.
	 * 
	 * @param type
	 *            De type van het object.
	 */
	public void bepaalType(int type) {
		switch (type) {
		case 1:
			setImage("/images/ruimte.png", 240, 290);
			setDirectionSpeed(270, 5);
			break;
		case 2:
			setImage("/images/gameover.png", 240, 290);
		}
	}

	/**
	 * Deze method plaatst het object bovenaan het scherm als het zich buiten het scherm bevindt.
	 */
	public void outsideWorld() {
		setPosition(0, -290);
	}
}