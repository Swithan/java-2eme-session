package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

public class Presence {
	

	public DefaultTableModel getPresence(Database db) {
		DefaultTableModel data = db.getData("nom as \"Nom\", prenom as \"Prénom\", groupe as \"Groupe\", absent as \"Absent\", motif as \"Motif\"", "membres join absence on membres.id = absence.nageur", "ORDER BY membres.id");
		return data;
	}
	
	public void setAbsent (Database db, int membre, Date today, String motif) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		db.insertData("absence", "nageur, date, motif, absent", membre+",'"+format.format(today)+"','"+motif+"',"+true);
	}
	
	public static void main (String[] args) {
	}
	

}
