
public class Player {
	
	double power = 64.9;
	double inventoryPower = 34;
	double chargeTime = 0.03;
	double armor = 52.8;
	double defence = 42.9;
	double range = 0;
	double movementSpeed = 3.57;
	double cChance = 54;
	double cPower = 2.7;
	double block = 24;
	double combat = 96;
	double ranged = 92;
	double magic = 111.1;

	double maxHealth = 3143;
	double remainingHealth = maxHealth;
	double hpRegen = 2.8;
	double buffedHpRegen = Math.pow(magic,0.3);
	
	double maxMana = 442;
	double mpRegen = 1.52;
	double remainingMana = maxMana;
	
	boolean onRush = true;
	boolean hunter = true;
	boolean oneStepAhead = true;
	boolean dash = true;
	boolean manOfFocus = true;
	
	double magicPower = power - inventoryPower*(manOfFocus?1.1:1);
	
	boolean isHealingActive = false;
	double healingTime = Math.pow(magic,0.5);
	double healTimeRemaining = 0;
	double healMagicPower = 5*(manOfFocus?1.1:1)+magicPower;
	double healAmount = healMagicPower + Math.pow(healMagicPower+magicPower,2.1)/100;
	
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
	
	
	public void hpRegen(double timeUntilNextAttack) {
		if(healTimeRemaining<=timeUntilNextAttack) {
			double healAmount = remainingHealth+(healTimeRemaining*buffedHpRegen)+((timeUntilNextAttack-healTimeRemaining)*hpRegen);
			remainingHealth = Math.min(healAmount,maxHealth);
			isHealingActive=false;
			healTimeRemaining=0;
		}else {
			remainingHealth = Math.min(remainingHealth+(timeUntilNextAttack*buffedHpRegen),maxHealth);
			healTimeRemaining -= timeUntilNextAttack;
		}
	}
	
	public void useHealSpell() {
		isHealingActive = true;
		healTimeRemaining = healingTime;
		remainingHealth = Math.min(remainingHealth+healAmount, maxHealth);
	}
	public void hit(double mobMinAttack, double mobMaxAttack) {
		
	}
}
