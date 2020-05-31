package org.neubauerfelix.manawars.castledefense.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.neubauerfelix.manawars.castledefense.CDManaWars;
import org.neubauerfelix.manawars.castledefense.CastleDefense;
import org.neubauerfelix.manawars.tools.Evaluation;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new CDManaWars(args), config);
	}
}
