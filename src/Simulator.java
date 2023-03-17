import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Simulator {
	public static void main(String[] args) {
		String mobFile = "./resources/MobData";
		String locationFile = "./resources/LocationData";
		HashMap<String,Mob> mobList = getMobData(mobFile);
		HashMap<String,Location> locationList =getLocationsData(locationFile);
		
		final int numberOfSimulations = 10000;
		
		mainSimulation(locationList,numberOfSimulations,mobList);
	}
	
	public static HashMap<String,Mob> getMobData(String pathFile){
		File mobInput = new File(pathFile);
		HashMap<String,Mob> mobList = new HashMap<>();
		try {
			Scanner sc = new Scanner(mobInput);
			while(sc.hasNextLine()) {
				String data = sc.nextLine();
				String[] singleMobInfo = data.split("\t");
				Mob mob = new Mob(singleMobInfo);
				mobList.put(singleMobInfo[0], mob);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return mobList;
	}
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
	
	public static void mainSimulation(HashMap<String,Location> locationList, int numberOfSimulations, HashMap<String,Mob> mobList) {
		
		for(Location location : locationList.values()) {
			Player player = new Player();
			ReturnData locationStats = new ReturnData();
			
			for(int i=0; i<numberOfSimulations; i++) {
				double random = (double) Math.floor(Math.random() *(10000 + 1))/100;
				double suma = 0;
				
				for(String mob : location.mobNamesAndSpawnChances.keySet()) {
					suma+=location.mobNamesAndSpawnChances.get(mob);
					
					if(suma>=random) {
						Mob enemy = mobList.get(mob);
						ReturnData returnedData = singleFight(player,enemy);
						locationStats.updateData(returnedData);
						break;
					}
				}
			}
			System.out.println("Location: "+location.name);
			System.out.println("Exp gained: "+Math.round(locationStats.expGain));
			System.out.println(" Total hits: "+Math.round(locationStats.numberOfHits));
			System.out.println(" Total time: "+Math.round(locationStats.fightTime));
			System.out.println(" exp/h: "+Math.round(locationStats.expGain/locationStats.fightTime)*3600);
			System.out.println(" Hp left: "+Math.round(player.remainingHealth));
			System.out.println("----------------- ");
			
		}
	}
	
	public static ReturnData singleFight(Player player, Mob mob) {
		ReturnData newReturn = new ReturnData();

		double numberOfHits = 0;
		double totalFightTime = 0;
		
		double playerNextAction = player.timeUntilPlayerAttack;
		double mobNextAction = mob.mobAttackTime;
		
		while(player.remainingHealth>0 && mob.health>0) {
			
			double timeUntilNextAttack = Math.min(playerNextAction, mobNextAction);
			playerNextAction-=timeUntilNextAttack;
			mobNextAction-=timeUntilNextAttack;
			totalFightTime+=timeUntilNextAttack;
			
			if(playerNextAction==0) {
				player.remainingHealth = Math.min(player.remainingHealth+player.hpRegen*timeUntilNextAttack,player.maxHealth);
				double critMulti = ((Math.floor(Math.random() *(100-0+1))>player.cChance) ? player.cPower : 1);
				double playerDmg =Math.max(Math.floor(Math.random() *(player.playerMaxAttack-player.playerMinAttack+1)+player.playerMinAttack)*critMulti*(1-mob.defence/100)-mob.armor,0);
				
				mob.health -= playerDmg;
				playerNextAction +=player.playerAttackTime;
				numberOfHits++;
			}
			if(mobNextAction==0){
				player.hit(mob.mobMinAttack,mob.mobMaxAttack);
				player.remainingHealth = Math.min(player.remainingHealth+player.hpRegen*timeUntilNextAttack,player.maxHealth);
				double mobDmg =Math.max(Math.floor(Math.random() *(mob.mobMaxAttack-mob.mobMinAttack+1)+mob.mobMinAttack)*(1-player.defence/100)-player.armor,0);
				player.remainingHealth -= mobDmg;
				mobNextAction += mob.mobAttackTime;
			}
		}
		newReturn.numberOfHits = numberOfHits;
		newReturn.expGain = ((player.remainingHealth>0) ? mob.exp : 0);
		newReturn.fightTime = totalFightTime;
		return newReturn;
	}
}
