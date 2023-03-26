public class Player {
	
	double power = 1;
	double inventoryPower = 1;
	double chargeTime = 3.5;
	double armor = 0;
	double defence = 0;
	double range = 0;
	double movementSpeed = 1;
	double cChance = 0;
	double cPower = 1.4;
	double block = 0;
	double combat = 1;
	double ranged = 1;
	double magic = 1;
	double maxHealth = 10;
	double hpRegen = 0.05;
	double maxMana = 1;
	double mpRegen = 0.01;
	
	boolean onRush = false;
	boolean hunter = false;
	boolean oneStepAhead = false;
	boolean dash = false;
	boolean manOfFocus = false;
	
	double remainingHealth = maxHealth;
	double buffedHpRegen = Math.pow(magic,0.3);
	double remainingMana = maxMana;
	
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
			double healingValue = healTimeRemaining*buffedHpRegen+(timeUntilNextAttack-healTimeRemaining)*hpRegen;
			remainingHealth = Math.min(remainingHealth+healingValue,maxHealth);
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
