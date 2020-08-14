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



	public void result(String data, Database db, Object nom, Object prenom, Object distance, Object style) {
		
		DefaultTableModel getid = db.getData("id", "membres", "WHERE nom = '"+nom.toString()+"'");
		int id = (int) getid.getValueAt(0, 0);
				
		DefaultTableModel pb = db.getData("temps", "records", "WHERE nageur = "+id+" AND distance = '"+distance.toString()+"' AND style = '"+style.toString()+"'");

		if(distance.toString().equals("50 m")) fifty(data, pb, db, id, distance, style);
		else hundreds(data, pb, db, id, distance, style);
	}

	private void hundreds(String data, DefaultTableModel pb, Database db, int id, Object distance, Object style) {
		
		String[] time = data.split("\\.");
		System.out.println(data.split("\\.").length);
		System.out.println(time[0]);
		int timeMiliSeconds = Integer.parseInt(time[1]);
		time = time[0].split(":");
		int timeSeconds = Integer.parseInt(time[1]);
		int timeMinutes = Integer.parseInt(time[0]);

		db.updateData("courses", "resultat", "'" +data+"' WHERE nageur ="+id+" AND distance = '"+distance.toString()+"' AND style = '"+style.toString()+"'");

		if(pb.getRowCount()>0) {
			String[] record = pb.getValueAt(0, 0).toString().split(".");
			int recordMiliSeconds = Integer.parseInt(record[1]);
			record = record[0].split(":");
			int recordSeconds = Integer.parseInt(record[1]);
			int recordMinutes = Integer.parseInt(record[0]);
			
			
			if(timeMinutes < recordMinutes || 
					timeMinutes == recordMinutes && timeSeconds < recordSeconds ||
					timeMinutes == recordMinutes && timeSeconds == recordSeconds && timeMiliSeconds < recordMiliSeconds) {
				db.updateData("records", "temps", "'" +data+"' WHERE nageur ="+id+" AND distance = '"+distance.toString()+"' AND style = '"+style.toString()+"'");
			} 
			else System.out.println("No PB");
		} 
		else {
			db.updateData("records", "temps", "'" +data+"' WHERE nageur ="+id+" AND distance = '"+distance.toString()+"' AND style = '"+style.toString()+"'");
		}
	}



	private void fifty(String data, DefaultTableModel pb, Database db, int id, Object distance, Object style) {
		String[] time = data.split("\\.");
		System.out.println(time[0]+" "+time[1]);
		
		db.updateData("courses", "resultat", "'"+data+"' WHERE nageur = "+id);
		if(pb.getRowCount()>0) {
			String[] record = pb.getValueAt(0, 0).toString().split("\"");
			
			if(Integer.parseInt(time[0]) < Integer.parseInt(record[0]) ||
					Integer.parseInt(time[0]) == Integer.parseInt(record[0]) && Integer.parseInt(time[1]) < Integer.parseInt(record[1])) {
				db.updateData("records", "temps", "'" +data+"' WHERE nageur ="+id+" AND distance = '"+distance.toString()+"' AND style = '"+style.toString()+"'");
			} else System.out.println("No PB");
		} else {
			db.updateData("records", "temps", "'" +data+"' WHERE nageur ="+id+" AND distance = '"+distance.toString()+"' AND style = '"+style.toString()+"'");
		}
	}
}
