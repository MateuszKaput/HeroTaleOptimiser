
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
	double mobAttackTime;
	double mobMinAttack;
	double mobMaxAttack;
	boolean dash;
	
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
		this.dash = Boolean.parseBoolean(data[10]);
		this.mobAttackTime = chargeTime+attackSpeed;
		this.mobMinAttack = power+(Math.pow(power,2.1)/100)*((range>0) ? 0.7 : 0.9);
		this.mobMaxAttack = power+(Math.pow(power,2.1)/100)*((range>0) ? 1.3 : 1.1);
	}
}
