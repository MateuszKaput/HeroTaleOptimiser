import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;

public class Simulator {
	public static void main(String[] args) {
		String locationFile = "./resources/LocationData";
		HashMap<String,Mob> mobList = getMobData.getMobDataFunction();
		HashMap<String,Location> locationList = getLocationData.getLocationsData(locationFile);
		
		mainSimulation(locationList,mobList);
	}
	
	public static void mainSimulation(HashMap<String,Location> locationList, HashMap<String,Mob> mobList) {
		try {
			FileWriter fileWriter = new FileWriter("simulatorResults.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);
			Location[] testing = {locationList.get("mogila")}; //uncomment this and 23, then comment 22 line to test specyfic location
		//for(Location location : locationList.values()) {
		for(Location location : testing) { 
			Player player = new Player();
			ReturnData locationStats = new ReturnData();
			while(locationStats.fightTime<86_400&&player.remainingHealth>0) {
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
			if(player.remainingHealth>0) {
				System.out.printf("\nLocation: "+location.name);
				System.out.printf("\nWeapon exp/h: "+formatter.format(locationStats.numberOfHits/locationStats.fightTime*3600));
				System.out.printf("\nexp/h: "+formatter.format(locationStats.expGain/locationStats.fightTime*3600)+"\n");
			}else {
				System.out.println("\nCan't survive: "+ location.name);
			}
			printWriter.printf("Location: "+location.name);
			printWriter.printf("\nSurvive?: "+(player.remainingHealth>0?"Yes":"No"));
			printWriter.printf("\nWeapon exp/h: "+formatter.format(locationStats.numberOfHits/locationStats.fightTime*3600));
			printWriter.printf("\nexp/h: "+formatter.format(locationStats.expGain/locationStats.fightTime*3600));
			printWriter.printf("\n\n");
			int seconds = (int) (locationStats.fightTime % 60);
			int minutes = (int) ((locationStats.fightTime/60)%60);
			int hours = (int) ((locationStats.fightTime/3600)%24);
			int days = (int) ((locationStats.fightTime/3600/24)%24);
			System.out.println(days+" days "+hours+" hours "+minutes+" minutes "+seconds+" seconds ");
		}
		printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
