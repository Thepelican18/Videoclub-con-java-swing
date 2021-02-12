package daniel.videoclub.mainpack;

import javax.swing.JPanel;

import daniel.videoclub.mainpack.Activities.CatalogManager;
import daniel.videoclub.mainpack.Activities.Login;

/**
 * class to change the JPanels
 * @author Pelicano
 *
 */
public class ActivityManager {
	
	private ActivityManager() {}
	
	private static JPanel actualActivity;
	
	
	static {
		
		setActualActivity(new Login());
	}


	public static JPanel getActualActivity() {
			
			return actualActivity;
	}


	public static void setActualActivity(JPanel newActivity) {
		
		if (actualActivity != null){
			
			actualActivity.removeAll();
			
		}
		
		actualActivity = newActivity;
		
	}

}
