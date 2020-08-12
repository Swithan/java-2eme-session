package model;

public class Competition {

	public boolean newCompet(Database db, String nom, String debut, String fin, String lieu) {
		String values = "'"+nom+"','"+debut+"','"+fin+"','"+lieu+"'";
		if(nom.length() > 0 && debut.length() >0 && fin.length()>0 && lieu.length()>0 ) {
			int insert = db.insertData("competition", "nom, debut, fin, lieu", values);
			if (insert > 0) return true;
		}
		return false;
	}
	
	public void editCompet(Object id) {
		System.out.println(id);
	}

}
