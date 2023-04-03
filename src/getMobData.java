import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

public class getMobData {
	public static HashMap<String,Mob> getMobDataFunction(){
		HashMap<String,Mob> mobList = new HashMap<>();
		try {
			JDBCConnection conn = new JDBCConnection();
			String query = "select * from mobs";
			ResultSet resultSet = conn.s.executeQuery(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			
			while(resultSet.next()) {
				Mob mob = new Mob(resultSet);
				mobList.put(resultSet.getString(1), mob);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return mobList;
	}
}
