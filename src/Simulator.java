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
		
		locationSimulation(locationList,numberOfSimulations,mobList);
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
	
	public static void locationSimulation(HashMap<String,Location> locationList, int numberOfSimulations, HashMap<String,Mob> mobList) {
		
		for(Location location : locationList.values()) {
			Player player = new Player();
			
			ReturnData locationSummary = new ReturnData();
			
			for(int i=0; i<numberOfSimulations; i++) {
				double random = (double) Math.floor(Math.random() *(10000 + 1))/100;
				double suma = 0;
				Mob enemy;
				
				for(String mob : location.mobNamesAndSpawnChances.keySet()) {
					suma+=location.mobNamesAndSpawnChances.get(mob);
					
					if(suma>=random) {
						enemy = mobList.get(mob);
						ReturnData returnedData = singleFight(player,enemy,locationSummary);
						locationSummary.expGain += returnedData.expGain;
						locationSummary.fightTime += returnedData.fightTime;
						locationSummary.numberOfHits += returnedData.numberOfHits;
						locationSummary.remainingHealth = returnedData.remainingHealth;
						break;
					}
				}
			}
			System.out.println("Location: "+location.name);
			System.out.println("Exp gained: "+Math.round(locationSummary.expGain));
			System.out.println(" Total hits: "+Math.round(locationSummary.numberOfHits));
			System.out.println(" Total time: "+Math.round(locationSummary.fightTime));
			System.out.println(" exp/h: "+Math.round(locationSummary.expGain/locationSummary.fightTime)*3600);
			System.out.println(" Hp left: "+Math.round(locationSummary.remainingHealth));
			System.out.println("----------------- ");
			
		}
	}
	
	public static ReturnData singleFight(Player player, Mob mob,ReturnData previousFight) {
		ReturnData newReturn = new ReturnData();
		
		double playerActualHealth = ((previousFight.remainingHealth != 0.0) ? previousFight.remainingHealth : player.health);
		double playerRemainingMana = ((previousFight.remainingMana != 0.0) ? previousFight.remainingMana : player.mana);
		double healTimeRemaining = previousFight.healTimeRemaining;
		
		double playerAttackTime = player.attackSpeed + ((player.range>0) ? player.rangeChargeTime:player.combatChargeTime);
		double playerMinAttack = player.power+(Math.pow(player.power+player.ranged,2.1)/100)*((player.range>0) ? 0.7 : 0.9);
		double playerMaxAttack = player.power+(Math.pow(player.power+player.ranged,2.1)/100)*((player.range>0) ? 1.3 : 1.1);
		
		double enemyHealth = mob.health;
		double enemyAttackTime = mob.chargeTime+mob.attackSpeed;
		double mobMinAttack = mob.power+(Math.pow(mob.power,2.1)/100)*((mob.range>0) ? 0.7 : 0.9);
		double mobMaxAttack = mob.power+(Math.pow(mob.power,2.1)/100)*((mob.range>0) ? 1.3 : 1.1);
		
		double searchTime = ((player.onRush) ? 2 : 4);
		double timeUntilPlayerAttack = playerAttackTime+searchTime;
		double timeUntilMobAttack = enemyAttackTime+searchTime;
		double numberOfHits = 0;
		double totalFightTime = 0;
		
		while(playerActualHealth>0 && enemyHealth>0) {
			
			double timeUntilNextAttack = Math.min(timeUntilPlayerAttack, timeUntilMobAttack);
			timeUntilPlayerAttack-=timeUntilNextAttack;
			timeUntilMobAttack-=timeUntilNextAttack;
			totalFightTime+=timeUntilNextAttack;
			
			if(timeUntilPlayerAttack==0) {
				playerActualHealth = Math.min(playerActualHealth+player.hpRegen*timeUntilNextAttack,player.health);
				double critMulti = ((Math.floor(Math.random() *(playerMaxAttack-playerMinAttack+1))>player.cChance) ? player.cPower : 1);
				double playerDmg =Math.max(Math.floor(Math.random() *(playerMaxAttack-playerMinAttack+1)+playerMinAttack)*critMulti*(1-mob.defence/100)-mob.armor,0);
				
				enemyHealth -= playerDmg;
				timeUntilPlayerAttack +=playerAttackTime;
				numberOfHits++;
			}
			if(timeUntilMobAttack==0){
				playerActualHealth = Math.min(playerActualHealth+player.hpRegen*timeUntilNextAttack,player.health);
				double mobDmg =Math.max(Math.floor(Math.random() *(mobMaxAttack-mobMinAttack+1)+mobMinAttack)*(1-player.defence/100)-player.armor,0);
				playerActualHealth -= mobDmg;
				timeUntilMobAttack += enemyAttackTime;
			}
		}
		newReturn.numberOfHits = numberOfHits;
		newReturn.expGain = ((previousFight.remainingHealth>0) ? mob.exp : 0);
		newReturn.fightTime = totalFightTime;
		newReturn.remainingHealth = playerActualHealth;
		newReturn.remainingMana = playerRemainingMana;
		return newReturn;
	}
}
