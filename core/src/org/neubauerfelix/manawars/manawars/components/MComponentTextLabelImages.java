package org.neubauerfelix.manawars.manawars.components;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.neubauerfelix.manawars.manawars.MManaWars;
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont;

public class MComponentTextLabelImages extends MComponentContainer {


	private MWFont font;
	private float scale;



	public MComponentTextLabelImages(float x, float y, String text, MWFont font, float scale){
		super(x, y);
		this.font = font;
		this.scale = scale;
		this.setText(text);
	}

	public MComponentTextLabelImages(float x, float y, String text){
		this(x, y, text, MWFont.MAIN, 1f);
	}

	public void setText(String text){
		this.clearComponents();
		float partX = this.getX();
		float partY = this.getY();
		float width = 0;
		float height = 0;

		for(String part : text.split("#")){

			if(part.startsWith("icon.")){
				TextureRegion icon = MManaWars.m.getImageHandler().getTextureRegionMain(part);
				float iconHeight = getLineHeight();
				float iconWidth = icon.getRegionWidth() * iconHeight / icon.getRegionHeight();
				MImage label = new MImage(partX, partY, iconWidth, iconHeight, icon);
				partX = label.getRight();
				width = partX;
				height = Math.max(height, label.getHeight());
				this.addComponent(label, true);
			}else{
				MTextLabel label = new MTextLabel(partX, partY, part, font, scale);
				partX = label.getRight();
				width = partX;
				height = Math.max(height, label.getHeight());
				this.addComponent(label, true);
			}
		}
	}


	private float getLineHeight(){
		return Math.abs(font.getFont(scale).getCapHeight())+ 6;
	}




}
