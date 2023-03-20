import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;

public class Simulator {
	public static void main(String[] args) {
		String mobFile = "./resources/MobData";
		String locationFile = "./resources/LocationData";
		HashMap<String,Mob> mobList = getMobData.getMobDataFunction(mobFile);
		HashMap<String,Location> locationList = getLocationData.getLocationsData(locationFile);
		
		final int numberOfSimulations = 10000;
		mainSimulation(locationList,numberOfSimulations,mobList);
	}
	
	public static void mainSimulation(HashMap<String,Location> locationList, int numberOfSimulations, HashMap<String,Mob> mobList) {
		try {
			FileWriter fileWriter = new FileWriter("simulatorResults.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
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
						ReturnData returnedData = singleFight.singleFightFunction(player,enemy);
						locationStats.updateData(returnedData);
						break;
					}
				}
			}
			DecimalFormat formatter = new DecimalFormat("#,###.##");
			System.out.printf("Location: "+location.name);
			printWriter.printf("Location: "+location.name);
			System.out.printf("\nSurvive?: "+(player.remainingHealth>0?"Yes":"No"));
			printWriter.printf("\nSurvive?: "+(player.remainingHealth>0?"Yes":"No"));
			System.out.printf("\nWeapon exp/h: "+formatter.format(locationStats.numberOfHits/locationStats.fightTime*3600));
			printWriter.printf("\nWeapon exp/h: "+formatter.format(locationStats.numberOfHits/locationStats.fightTime*3600));
			System.out.printf("\nexp/h: "+formatter.format(locationStats.expGain/locationStats.fightTime*3600));
			printWriter.printf("\nexp/h: "+formatter.format(locationStats.expGain/locationStats.fightTime*3600));
			
			System.out.printf("\n----------");
			printWriter.printf("\n\n");
			
		}
		printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
