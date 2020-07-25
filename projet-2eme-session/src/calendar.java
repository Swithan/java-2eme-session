import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class calendar {
	public static void main(String[] args) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		
		int date = dateobj.getDate() - 1;
		dateobj.setDate(date);
		String yesterday = df.format(dateobj);
		System.out.println(yesterday);
		
		
		try {
			System.out.println("start");
			Class.forName("org.postgresql.Driver");
			Connection co = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java", "postgres", "N@tDeb22");
			PreparedStatement stmt = co.prepareStatement("SELECT * FROM membres", ResultSet.TYPE_SCROLL_INSENSITIVE);
			ResultSet result = stmt.executeQuery();
			ResultSetMetaData rsmd = result.getMetaData();
			while(result.next()) {
				System.out.println(result.getRow());
				
				for (int i = 1; i<rsmd.getColumnCount() + 1; i ++ ) {
					System.out.println(result.getString(i));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("error "+ e);
		}
		
	}
}
