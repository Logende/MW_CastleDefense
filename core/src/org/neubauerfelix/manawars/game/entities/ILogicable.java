package org.neubauerfelix.manawars.game.entities;

/**
 * ILogicable objects are capable of thinking.
 * @author Felix Neubauer
 *
 */
public interface ILogicable {

	/**
	 * This method is automatically executed on a regular basis (every game tick).
	 * @param delta Time difference between this call and the latest method call in seconds.
	 */
	void doLogic(float delta);
	

}
