/**
 * 
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

/**
 * @author nathan debongnie
 *
 */
public class database extends Observable {

	private Connection connexion () {
		Connection co = null;
		try {
			Class.forName("org.postgresql.Driver");
			co = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java", "postgres", "N@tDeb22");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return co;
	};
	
	public ResultSet getData (String column,String table, String condition) {
		ResultSet result = null;
		try {
			Connection connect = connexion();
			Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//L'objet result contient le résultat de la requete SQL
			result = state.executeQuery("SELECT "+ column +" FROM "+ table + " " + condition);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return result;
	};
		
	public static void main (String[] args) {
		try {
			database db = new database();
			ResultSet result = db.getData("*", "membres", "");
			while(result.next()) {
				for (int i = 1; i<result.getMetaData().getColumnCount() + 1; i ++ ) {
					System.out.println(result.getString(i));
				}
			}
		} catch (SQLException e) {
			System.out.println("error");
		}
	}
}

