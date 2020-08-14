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
	
	

	public boolean course(Database db, int compet, String membre, String style, String distance) {
		String [] split = membre.split("\\s", 0);
		String nom = split[0];
		String prenom = split[1];
		DefaultTableModel membreData = db.getData("id", "membres", "WHERE nom = '"+nom+"' AND prenom = '"+prenom+"'");
		int id = (int) membreData.getValueAt(0, 0);
		int verifier = db.getData("*", "courses", "WHERE nageur = "+id+" AND style = '"+style+"' AND distance = '"+distance+"'").getRowCount();
		
		DefaultTableModel recordData = db.getData("temps", "records", "WHERE nageur = "+id+" AND style = '"+style+"' AND distance = '"+distance+"'");
		if (recordData.getRowCount()>0 && verifier == 0) {
			String temps = (String) recordData.getValueAt(0, 0);
			db.insertData("courses", "compet, nageur, distance, style, inscription", compet+", "+id+", '"+distance+"', '"+style+"', '"+temps+"'");
			return true;
		} else if (verifier == 0){
			db.insertData("courses", "compet, nageur, distance, style, inscription", compet+", "+id+", '"+distance+"', '"+style+"', 'NT'");
			return true;
		} 
		return false;
	}

}
