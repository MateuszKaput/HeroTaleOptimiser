
public class Mob {

	double health;
	double power;
	double attackSpeed;
	double armor;
	double defence;
	double range;
	double movementSpeed;
	double chargeTime;
	String name;
	double exp;
	boolean onRush;
	
	public Mob(String[] data) {
		this.name = data[0];
		this.health = Double.parseDouble(data[1]);
		this.exp = Double.parseDouble(data[2]);
		this.power = Double.parseDouble(data[3]);
		this.attackSpeed = Double.parseDouble(data[4]);
		this.armor = Double.parseDouble(data[5]);
		this.defence = Double.parseDouble(data[6]);
		this.range = Double.parseDouble(data[7]);
		this.movementSpeed = Double.parseDouble(data[8]);
		this.chargeTime =Double.parseDouble(data[9]);
	}
}
