package model;

import javax.swing.table.DefaultTableModel;

public class Presence {

	public DefaultTableModel getPresence(Database db) {
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", absent as \"Absent\", motif as \"Motif\"", "membres join absence on membres.id = absence.nageur", "");
		return data;
	}
	
	public void updatePresence (Database db) {
		db.updateData(null, null);
	}
}
