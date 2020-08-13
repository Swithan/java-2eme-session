package model;

import javax.swing.table.DefaultTableModel;

public class Competition {

	public boolean newCompet(Database db, String nom, String debut, String fin, String lieu) {
		String values = "'"+nom+"','"+debut+"','"+fin+"','"+lieu+"'";
		if(nom.length() > 0 && debut.length() >0 && fin.length()>0 && lieu.length()>0 ) {
			int insert = db.insertData("competition", "nom, debut, fin, lieu", values);
			if (insert > 0) return true;
		}
		return false;
	}
	
	

	public DefaultTableModel course(Database db, int compet, String membre, String style, String distance) {
		String [] split = membre.split("\\s", 0);
		String nom = split[0];
		String prenom = split[1];
		DefaultTableModel membreData = db.getData("id", "membres", "WHERE nom = '"+nom+"' AND prenom = '"+prenom+"'");
		int id = (int) membreData.getValueAt(0, 0);
		DefaultTableModel recordData = db.getData("temps", "records", "WHERE nageur = "+id+" AND style = '"+style+"' AND distance = '"+distance+"'");
		int insert =0;
		if (recordData.getRowCount()>0) {
			String temps = (String) recordData.getValueAt(0, 0);
			insert = db.insertData("courses", "compet, nageur, distance, style, inscription", compet+", "+id+", '"+distance+"', '"+style+"', '"+temps+"'");
		} else {
			insert = db.insertData("courses", "compet, nageur, distance, style, inscription", compet+", "+id+", '"+distance+"', '"+style+"', 'NT'");
		}
		if(insert>0) return membreData;
		else return null;
	}

}
