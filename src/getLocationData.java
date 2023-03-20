import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class getLocationData {
	public static HashMap<String,Location> getLocationsData(String pathFile){
		File locationInput = new File(pathFile);
		HashMap<String,Location> locationList = new HashMap<>();
		try {
			Scanner sc = new Scanner(locationInput);
			while(sc.hasNextLine()) {
				String data = sc.nextLine();
				String[] singleLocationInfo = data.split("\t");
				String[] locationMobList = singleLocationInfo[1].split(" ");
				HashMap<String,Double> mobSpawnChance = new HashMap<>();
				for(int i=0; i<locationMobList.length; i++) {
					String[] mobNameAndSpawnChance = locationMobList[i].split("-");
					mobSpawnChance.put(mobNameAndSpawnChance[0], Double.parseDouble(mobNameAndSpawnChance[1]));
				}
				Location location = new Location(singleLocationInfo[0],mobSpawnChance);
				locationList.put(singleLocationInfo[0],location);
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("No file");
			e.printStackTrace();
		}
		
		return locationList;
	}
}
