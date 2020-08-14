/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 * @author nathan debongnie
 *
 */
public class Database {

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
			
			model = new DefaultTableModel(names, 0) {
				private static final long serialVersionUID = 4889741207303239583L;

				@Override
			    public boolean isCellEditable(int row, int column) {
					if (this.findColumn("resultat") > -1) return column == this.findColumn("resultat");
					return column == this.findColumn("Absent");
			    }
				@Override
		        public Class<?> getColumnClass(int columnIndex) {
					int col = this.findColumn("Absent");
					if (columnIndex == col) return Boolean.class;
					else return Object.class;
		        }
			};
			
			while(result.next()) {
				Object[] values = new Object[result.getMetaData().getColumnCount()];
				for (int i = 0; i < result.getMetaData().getColumnCount(); i ++ ) {
					values[i] = result.getObject(i + 1);
				}
				model.addRow(values);
			}			
			result.last();
			Object[] values = new Object[result.getRow()];
			for(int i = 0; i< values.length; i++) {
				values[i] = false;
			}
			if (model.findColumn("Groupe") > 0) {
				model.addColumn("Absent", values);
				values = null;
				model.addColumn("Motif", values);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return model;
	}

	public int insertData(String table, String columns, String values) {
		String sql = "INSERT INTO "+table+" ("+columns+") VALUES ("+values+")";
		int result = 0;
		try {
			Connection co = connexion();
			Statement state;
			state = co.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			result = state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateData(String table, String column, String data) {
		String sql = "UPDATE "+table+" SET ";
		try {
			Connection co = connexion();
			Statement state = co.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			int result = state.executeUpdate(sql);
			if (result>0) {
				System.out.println("Row(s) updated");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	};
	}

