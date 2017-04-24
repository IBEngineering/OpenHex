package openhex.run;

import openhex.game.Game;
import openhex.view.ViewApplication;

public class Test {

	public static void main(String[] args) {
		new Game();
		
		ViewApplication vapp = new ViewApplication();
		vapp.start();
	}

}
