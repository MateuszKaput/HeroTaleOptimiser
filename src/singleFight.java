
public class singleFight {
	public static ReturnData singleFight(Player player, Mob mob) {
		ReturnData newReturn = new ReturnData();
		double numberOfHits = 0;
		double totalFightTime = 0;
		double playerNextAction = player.timeUntilPlayerAttack;
		double mobNextAction = mob.mobAttackTime+player.searchTime;
		double enemyHp = mob.health;
		
		if(player.range>mob.range) {
			double chaseTime = player.movementSpeed*(player.range-mob.range)/mob.movementSpeed;
			if(mob.dash) {
				if(chaseTime<6) {
					mobNextAction += chaseTime/2;
				}else {
					mobNextAction += chaseTime-3;
				}
			}else {
				mobNextAction += chaseTime;
			}
		}else {
			double chaseTime = mob.movementSpeed*(mob.range-player.range)/player.movementSpeed;
			if(player.dash) {
				if(chaseTime<6) {
					playerNextAction += chaseTime/2;
				}else {
					playerNextAction += chaseTime-3;
				}
			}else {
				playerNextAction += chaseTime;
			}
		}
		while(player.remainingHealth>0 && enemyHp>0) {
			
			double timeUntilNextAttack = Math.min(playerNextAction, mobNextAction);
			playerNextAction-=timeUntilNextAttack;
			mobNextAction-=timeUntilNextAttack;
			totalFightTime+=timeUntilNextAttack;
			
			if(playerNextAction==0) {
				player.hpRegen(timeUntilNextAttack);
				player.remainingHealth = Math.min(player.remainingHealth+player.hpRegen*timeUntilNextAttack,player.maxHealth);
				double critMulti = ((Math.floor(Math.random() *(100-0+1))<player.cChance) ? player.cPower : 1);
				double playerDmg =Math.max(Math.floor(Math.random() *(player.playerMaxAttack-player.playerMinAttack+1)+player.playerMinAttack)*critMulti*(1-mob.defence/100)-mob.armor,0);
				enemyHp -= playerDmg;
				playerNextAction +=player.playerAttackTime;
				numberOfHits++;
			}
			if(mobNextAction==0){
				player.hpRegen(timeUntilNextAttack);
				double mobDmg =Math.max(Math.floor(Math.random() *(mob.mobMaxAttack-mob.mobMinAttack+1)+mob.mobMinAttack)*(1-player.defence/100)-player.armor,0);
				player.remainingHealth -= mobDmg;
				mobNextAction += mob.mobAttackTime;
			}
		}
		newReturn.numberOfHits = numberOfHits;
		newReturn.expGain = ((player.remainingHealth>0) ? mob.exp : 0);
		newReturn.fightTime = totalFightTime;
		return newReturn;
	}
}
