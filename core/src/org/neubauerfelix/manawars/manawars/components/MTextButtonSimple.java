package org.neubauerfelix.manawars.manawars.components;


import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont;

public class MTextButtonSimple extends MTextButton {

	private Runnable runnable;


	public MTextButtonSimple(float x, float y, String text, Runnable runnable, float scale) {
		super(x, y, text, MWFont.MAIN, scale);
		this.runnable = runnable;
	}

	public MTextButtonSimple(float x, float y, String text, Runnable runnable) {
		super(x, y, text);
		this.runnable = runnable;
	}

	@Override
	public void clickAction() {
	}

	@Override
	public void unclickAction() {
	}

	@Override
	public void intendedUserAction() {
		runnable.run();
	}
}
