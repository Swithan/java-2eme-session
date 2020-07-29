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
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.table.DefaultTableModel;

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
	
	public DefaultTableModel getData (String column,String table, String condition) {
		DefaultTableModel model = null;
		try {
			Connection connect = connexion();
			Statement state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet result = state.executeQuery("SELECT "+ column +" FROM "+ table + " " + condition);
			
			String[] names = new String[result.getMetaData().getColumnCount()];
			for (int i = 0; i < result.getMetaData().getColumnCount(); i ++ ) {
				names[i] = result.getMetaData().getColumnName(i + 1);
			}
			
			model = new DefaultTableModel(names, 0);
			
			while(result.next()) {
				String[] values = new String[result.getMetaData().getColumnCount()];
				for (int i = 0; i < result.getMetaData().getColumnCount(); i ++ ) {
					values[i] = result.getString(i + 1);
				}
				model.addRow(values);
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return model;
	};
	}

