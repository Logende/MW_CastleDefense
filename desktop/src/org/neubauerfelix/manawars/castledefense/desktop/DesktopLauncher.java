package org.neubauerfelix.manawars.castledefense.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.neubauerfelix.manawars.castledefense.CastleDefense;
import org.neubauerfelix.manawars.manawars.MManaWars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MManaWars(), config);
	}
}
