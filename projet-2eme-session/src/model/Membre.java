package model;

import javax.swing.table.DefaultTableModel;

public class Membre {

	public boolean createMembre(Database db, String nom, String prenom, String sexe, String naissance, String groupe) {
		String values ="'"+nom+"','"+prenom+"','"+sexe+"','"+naissance+"','"+groupe+"'";
		DefaultTableModel data = db.getData("nom, prenom", "membres", "WHERE nom ='"+nom+"' AND prenom = '"+prenom+"'");
		if(data.getRowCount()>0) {
			return false;
		} else {
			db.insertData("membres", "nom, prenom, sexe, naissance, groupe", values);
			return true;
		}
	}

	public void records(Object id) {
		// TODO Auto-generated method stub
		System.out.println(id);
	}
}
