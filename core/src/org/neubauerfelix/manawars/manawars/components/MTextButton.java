package org.neubauerfelix.manawars.manawars.components;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont;

public abstract class MTextButton extends MComponent {


	private String text;
	private GlyphLayout layout;
	private MWFont font;
	private float scale;



	public MTextButton(float x, float y, String text, MWFont font, float scale){
		super(x, y,0, 0);
		this.font = font;
		this.scale = scale;
		this.layout = new GlyphLayout();
		setText(text);
	}

	public MTextButton(float x, float y, String text){
		this(x, y, text, MWFont.MAIN, 1f);
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		if(font == MWFont.MAIN){ //Does not support special german characters in upper case
			text = text.replace("Ä", "ä").replace("Ü", "ü").replace("ö", "Ö");
		}
		this.text = text;
		BitmapFont bitmapFont = font.getFont(scale);
		layout.setText(bitmapFont, text);
		setSize(layout.width, Math.abs(layout.height));
	}



	@Override
	public void draw(Batch batcher, float offsetX, float offsetY) {
		font.getFont(scale).draw(batcher, text, getX() + offsetX, getY() + offsetY);
	}




}
