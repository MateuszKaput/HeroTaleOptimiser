import java.util.HashMap;

public class Location {

	String name;
	HashMap<String,Double> mobNamesAndSpawnChances;
	
	public Location(String name,HashMap<String,Double> chances) {
		this.name= name;
		this.mobNamesAndSpawnChances =chances;
	}
}
