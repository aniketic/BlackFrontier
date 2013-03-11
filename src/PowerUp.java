package blackfrontier;

import phonegame.*;

public class PowerUp extends MoveableGameItem implements IStepListener {
	//Fields
	private Heelal heelal;

	//Constructor
	
	/**
	 * De Constructor voor de PowerUp class. Tevens wordt er een steplistener
	 * gemaakt in heelal.
	 * 
	 * @param heelal
	 *            = het 'spelwereld' object.
	 * @param type
	 * 			= het type PowerUp object.
	 */
	
	public PowerUp(Heelal heelal, int type) {
		this.heelal = heelal;
		
		setImage("/images/powerupGun.png", 32, 26);
		
		setDirectionSpeed(270, 5);

		heelal.addStepListener(this);
	}
	
	//Methods
	
	/**
	 * Stepaction methode die bepaald wat een PowerUp moet doen na stepnr
	 * aantal stappen.
	 * 
	 * @param stepnr
	 *            = hoeveeleid stappen die nodig zijn om iets uit te voeren.
	 */
	
	public void stepAction(int stepnr) {
	}
	
	/**
	 * Deze method zorgt ervoor dat de image voor PowerUp meer frames gebruikt.
	 */
	
	public void animate() {
		nextFrame();
	}
	
	/**
	 * Deze methode bepaalt wat er gebeurt als een PowerUp buiten de
	 * spelwereld komt. Deze wordt dan verwijderd.
	 */
	
	public void outsideWorld() {
		heelal.deleteGameItem(this);
	}
}
