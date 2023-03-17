
public class Player {

	double maxHealth = 3143;
	double remainingHealth = maxHealth;
	double hpRegen = 2.8;
	
	double maxMana = 442;
	double remainingMana = maxMana;
	double mpRegen = 1.52;
	
	double power = 10;
	double inventoryPower = 34;
	double chargeTime = 0.03;
	double armor = 0;
	double defence = 0;
	double range = 0;
	double movementSpeed = 2.65;
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
	
	boolean isHealingActive = false;
	double healTimeRemaining = 0;
	
	double searchTime = ((onRush) ? 2 : 4);
	double rangeSpeedTime = 1.5 * Math.pow(0.99,ranged);
	double combatSpeedTime = 1.5 * Math.pow(0.99,combat);
	double playerAttackTime = chargeTime + ((range>0) ? rangeSpeedTime : combatSpeedTime);
	double timeUntilPlayerAttack = playerAttackTime+searchTime;
	
	double combatDamage = power + Math.pow(power+combat,2.1)/100;
	double rangedDamage = power + Math.pow(power+ranged,2.1)/100;
	double magicDamage = power + Math.pow(power+magic,2.1)/100;
	double playerMinAttack = power+(Math.pow(power+((range>0) ? ranged : combat),2.1)/100)*((range>0) ? 0.7 : 0.9);
	double playerMaxAttack = power+(Math.pow(power+((range>0) ? ranged : combat),2.1)/100)*((range>0) ? 1.3 : 1.1);
	
	
	public void heal() {
		
	}
	public void hit(double mobMinAttack, double mobMaxAttack) {
		
	}
}
