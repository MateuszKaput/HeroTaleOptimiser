public class Player {
	
	//base stats
	double power = 64.9;
	double inventoryPower = 34;
	double chargeTime = 0.03;
	double armor = 50.4;
	double defence = 40.7;
	double range = 0;
	double movementSpeed = 3.57;
	double cChance = 58.3;
	double cPower = 2.7;
	double block = 24;
	double blockedHits = 0;
	double combat = 99;
	double ranged = 94;
	double magic = 114.4;
	double maxHealth = 3521;
	double hpRegen = 2.8;
	double maxMana = 593;
	double mpRegen = 1.52;
	
	//perk tree skills
	boolean dash = true;
	boolean onRush = true;
	boolean hunter = true;
	boolean manOfFocus = true;
	boolean oneStepAhead = true;
	
	//autocasting spells
	boolean autoCastPoison = false;
	boolean autoCastHealing = true;
	boolean autoCastThunder = false;
	
	//calculated stats
	double remainingHealth = maxHealth;
	double buffedHpRegen = Math.pow(magic,0.3);
	double remainingMana = maxMana;
	double magicPower = power - inventoryPower*(manOfFocus?1.1:1);
	double spellCastTime =2* Math.pow(0.99, magic); 
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
	
	
	//heal spell math
	double healCooldown = 12;
	double healCooldownRemaining;
	boolean isHealingActive = false;
	double healingTime = Math.pow(magic,0.5);
	double healTimeRemaining = 0;
	double healMagicPower = 5*(manOfFocus?1.1:1)+magicPower;
	double healAmount = healMagicPower + Math.pow(healMagicPower+magic,2.1)/100;
	
	//poison spell math
	double poisonCooldown = 16;
	double poisonCooldownRemaining;
	//To-do
	
	//thunder spell math
	double thunderCooldown = 40;
	double thunderCooldownRemaining;
	//To-do
	
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
		healCooldownRemaining = healCooldown;
		isHealingActive = true;
		healTimeRemaining = healingTime;
		remainingHealth = Math.min(remainingHealth+healAmount, maxHealth);
	}
	public void hit(double mobMinAttack, double mobMaxAttack) {
		
	}
}
