package main;

import java.util.Properties;

import javax.swing.UIManager;
import views.MainWindow;

public class Main__poe_c_g {
	
	public static final String cfgFileUri = "poe_currency_grid.conf";
	public static final Properties props = new Properties();
	
	public Main__poe_c_g() {

		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {
	    	System.err.println("Setting default UI (to System UI l&f) failed");
	    }
		
		new MainWindow();
		
	}

	public static void main(String[] args) {
		new Main__poe_c_g();
	}

}
