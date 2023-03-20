import java.text.DecimalFormat;
import java.util.*;

public class Simulator {
	public static void main(String[] args) {
		String mobFile = "./resources/MobData";
		String locationFile = "./resources/LocationData";
		HashMap<String,Mob> mobList = getMobData.getMobData(mobFile);
		HashMap<String,Location> locationList = getLocationData.getLocationsData(locationFile);
		
		final int numberOfSimulations = 10000;
		mainSimulation(locationList,numberOfSimulations,mobList);
	}
	
	public static void mainSimulation(HashMap<String,Location> locationList, int numberOfSimulations, HashMap<String,Mob> mobList) {
		for(Location location : locationList.values()) {
		//Location location = locationList.get("mogila");
			Player player = new Player();
			ReturnData locationStats = new ReturnData();
			
			for(int i=0; i<numberOfSimulations; i++) {
				double random = (double) Math.floor(Math.random() *(10000 + 1))/100;
				double suma = 0;
				
				for(String mob : location.mobNamesAndSpawnChances.keySet()) {
					suma+=location.mobNamesAndSpawnChances.get(mob);
					if(suma>=random) {
						Mob enemy = mobList.get(mob);
						ReturnData returnedData = singleFight.singleFight(player,enemy);
						locationStats.updateData(returnedData);
						break;
					}
				}
			}
			DecimalFormat formatter = new DecimalFormat("#,###");
			System.out.println("Location: "+location.name);
			System.out.println("Total hits: "+Math.round(locationStats.numberOfHits));
			System.out.println("exp/h: "+formatter.format(Math.round(locationStats.expGain/locationStats.fightTime)*3600));
			System.out.println("Hp left: "+Math.round(player.remainingHealth));
			System.out.println("----------------- ");
		}
	}
}
