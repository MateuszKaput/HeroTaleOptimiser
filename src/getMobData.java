import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class getMobData {
	public static HashMap<String,Mob> getMobDataFunction(String pathFile){
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
}
