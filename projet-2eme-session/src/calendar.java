import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import Model.database;

public class calendar {
	public static void main(String[] args) {
		database db = new database();
		db.getData("*", "membres", "");
		try {
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
