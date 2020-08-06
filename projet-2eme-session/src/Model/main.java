/**
 * 
 */
package Model;

import Vue.GUI;

/**
 * @author natha
 *
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		GUI gui = new GUI();
		
		gui.createInterface(db);
		gui.window.setVisible(true);
	}

}
