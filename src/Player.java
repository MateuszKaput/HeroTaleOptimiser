
public class Player {

	double health = 3143;
	double mana = 442;
	double power = 10;
	double inventoryPower = 34;
	double attackSpeed = 0.03;
	double armor = 0;
	double defence = 0;
	double range = 0;
	double movementSpeed = 2.65;
	double hpRegen = 2.8;
	double mpRegen = 1.52;
	double cChance = 53.9;
	double cPower = 2.7;
	double block = 24;
	double combat = 20;
	double ranged = 92;
	double magic = 107.8;
	boolean onRush = true;
	boolean hunter = true;
	boolean oneStepAhead = true;
	boolean dash = true;
	
	double rangeChargeTime = 1.5 * Math.pow(0.99,ranged);
	double combatChargeTime = 1.5 * Math.pow(0.99,combat);
	double combatDamage = power + Math.pow(power+combat,2.1)/100;
	double rangedDamage = power + Math.pow(power+ranged,2.1)/100;
	double magicDamage = power + Math.pow(power+magic,2.1)/100;
}
