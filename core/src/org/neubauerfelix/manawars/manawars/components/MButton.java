package org.neubauerfelix.manawars.manawars.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A clickable image button.
 * @author Felix Neubauer
 *
 */
public abstract class MButton extends MComponent {


	private TextureRegion texture;
	private TextureRegion texturePressed;
	

	public MButton(float x, float y, float width, float height, TextureRegion texture){
		super(x, y, width, height);
		this.texture = texture;
	}


	public MButton(float x, float y, float width, float height, TextureRegion texture, TextureRegion texturePressed){
		this(x, y, width, height, texture);
		this.texturePressed = texturePressed;
	}


	public TextureRegion getTexture(){
		return texture;
	}
	public TextureRegion getTexturePressed(){
		return texturePressed;
	}

	public void setTexture(TextureRegion texture, float width, float height){
		this.setImages(texture, texture, width, height);
	}

	public void setImages(TextureRegion texture, TextureRegion texturePressed, float width, float height){
		this.texture = texture;
		this.texturePressed = texturePressed;
		setSize(width, height);
	}


	@Override
	public void draw(Batch batcher, float offsetX, float offsetY) {
		TextureRegion image = (isPressed() && texturePressed != null) ? texturePressed : texture;
		batcher.draw(image, getX() + offsetX, getY() + offsetY, getWidth(), getHeight());
	}



}
