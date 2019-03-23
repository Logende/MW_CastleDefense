package org.neubauerfelix.manawars.manawars.components;


import org.neubauerfelix.manawars.game.components.GameComponent;


/**
 * Represents a MComponent like for example a button or textlabel.
 * @author Felix Neubauer
 *
 */

public abstract class MComponent extends GameComponent {

	protected int pointerId = -1;
	private boolean hidden = false;
	private boolean pressed = false;


	public MComponent(float x, float y, float width, float height){
		super(x, y, width, height);
	}


	public void setHidden(boolean b){
		hidden=b;
	}
	@Override
	public boolean isHidden(){
		return hidden;
	}


	public void click(){
		if(pressed){
			return;
		}
		pressed=true;
		clickAction();
	}

	public void unclick(){
		if(!pressed){
			return;
		}
		pressed=false;
		unclickAction();
	}


	@Override
	public boolean isPressed(){
		return pressed;
	}



	@Override
	public boolean touch(float x, float y, int pointerId){
		if(isInside(x, y)){
			click();
			this.pointerId=pointerId;
			return true;
		}
		return false;
	}

	@Override
	public boolean release(float x, float y, int pointerId){
		if(pointerId == this.pointerId){
			unclick();
			this.pointerId=-1;
			return true;
		}
		return false;
	}

	@Override
	public boolean drag(float x, float y, int pointerId) {
		return false;
	}

	@Deprecated
	public abstract void clickAction();


	@Deprecated
	public abstract void unclickAction();



}
