/**
 * 
 */
package model;

import vue.GUI;

/**
 * @author nathan Debongnie
 *
 */
public class App {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Database db = new Database();
		GUI gui = new GUI(null, null);
		
		gui.createInterface(db);
		gui.window.setVisible(true);
	}
}
