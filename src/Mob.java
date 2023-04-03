import java.sql.ResultSet;
import java.sql.SQLException;

public class Mob {

	double health;
	double power;
	double chargeTime;
	
	double armor;
	double defence;
	double range;
	double movementSpeed;
	double attackSpeed;
	String name;
	double exp;
	double mobAttackTime;
	double mobMinAttack;
	double mobMaxAttack;
	boolean dash;
	
	public Mob(ResultSet resultSet) throws SQLException {
		this.name = resultSet.getString(1);
		this.health = (double)(resultSet.getInt(2));
		this.exp = (double)(resultSet.getInt(3));
		this.power = (double)(resultSet.getInt(4));
		this.chargeTime = resultSet.getDouble(5);
		this.armor =(double)(resultSet.getInt(6));
		this.defence = (double)(resultSet.getInt(7));
		this.range = resultSet.getDouble(8);
		this.movementSpeed = resultSet.getDouble(9);
		this.attackSpeed = resultSet.getDouble(10);
		this.dash = resultSet.getBoolean(11);
		this.mobAttackTime = chargeTime+attackSpeed;
		this.mobMinAttack = power+(Math.pow(power,2.1)/100)*((range>0) ? 0.7 : 0.9);
		this.mobMaxAttack = power+(Math.pow(power,2.1)/100)*((range>0) ? 1.3 : 1.1);
	}
}
