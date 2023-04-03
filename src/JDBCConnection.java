import java.sql.*;

public class JDBCConnection {
	Connection c;
	Statement s;

	public JDBCConnection() {
		String url="jdbc:mysql://localhost/heroTaleDB";
		String username ="root";
		String password ="";
		String driver = "com.mysql.cj.jdbc.Driver";
		
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url,username,password);
			s = c.createStatement();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
