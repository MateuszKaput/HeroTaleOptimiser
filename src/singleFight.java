public class singleFight {
	public static ReturnData singleFightFunction(Player player, Mob mob) {
		ReturnData newReturn = new ReturnData();
		//initialize basics data
		double numberOfHits = 0;
		double totalFightTime = 0;
		double enemyHp = mob.health;
		boolean tooLongFight = false;
		
		//set up timers
		double playerNextAction = player.timeUntilPlayerAttack;
		double mobNextAction = mob.mobAttackTime+player.searchTime;
		
		//checking who is chasing and adding chase time to action time
		if(player.range>mob.range) {
			double chaseTime = player.movementSpeed*(player.range-mob.range)/mob.movementSpeed;
			mobNextAction+= (mob.dash)? (chaseTime<6)? chaseTime/2 : chaseTime-3 :chaseTime;
		}else {
			double chaseTime = mob.movementSpeed*(mob.range-player.range)/player.movementSpeed;
			playerNextAction += (player.dash)? (chaseTime<6)? chaseTime/2 : chaseTime-3: chaseTime;
		}
		
		//while loop until player or mob is dead, or fight is taking too long(mob and player can't damage each other)
		while(player.remainingHealth>0 && enemyHp>0 && !tooLongFight) {
			tooLongFight = (totalFightTime>3600) ? true : false;
			
			//find min timer
			double timeUntilNextAction = Math.min(playerNextAction, mobNextAction);
			if(player.autoCastHealing) timeUntilNextAction = Math.min(timeUntilNextAction,player.healCooldownRemaining);
			if(player.autoCastPoison) timeUntilNextAction = Math.min(timeUntilNextAction,player.poisonCooldownRemaining);
			if(player.autoCastThunder) timeUntilNextAction = Math.min(timeUntilNextAction,player.thunderCooldownRemaining);
			
			//System.out.println("Timers: "+playerNextAction+" "+mobNextAction+" "+player.healCooldownRemaining);
			//System.out.println("Block: "+player.blockedHits+" "+Math.sqrt(player.blockedHits));
			
			//subtract min from timers
			playerNextAction-=timeUntilNextAction;
			mobNextAction-=timeUntilNextAction;
			if(player.autoCastHealing) player.healCooldownRemaining-=timeUntilNextAction;
			if(player.autoCastPoison) player.poisonCooldownRemaining-=timeUntilNextAction;
			if(player.autoCastThunder) player.thunderCooldownRemaining-=timeUntilNextAction;
			
			//add min to total fight time
			totalFightTime+=timeUntilNextAction;
			
			// call hp regen before any action
			player.hpRegen(timeUntilNextAction);
			
			//checking if ifs what action is performed
			
			//player is hitting mob
			if(playerNextAction==0) {
				double critMulti = ((Math.floor(Math.random() *(100-0+1))<player.cChance) ? player.cPower : 1);
				double playerDmg =Math.max(Math.floor(Math.random() *(player.playerMaxAttack-player.playerMinAttack+1)+player.playerMinAttack)*critMulti*(1-mob.defence/100)-mob.armor,0);
				enemyHp -= playerDmg;
				playerNextAction +=player.playerAttackTime;
				numberOfHits++;
			}
			//mob is hitting player
			if(mobNextAction==0){
				double mobDmg =Math.max(Math.floor(Math.random() *(mob.mobMaxAttack-mob.mobMinAttack+1)+mob.mobMinAttack)*(1-(player.defence+Math.sqrt(player.blockedHits))/100)-player.armor,0);
				player.remainingHealth -= mobDmg;
				mobNextAction += mob.mobAttackTime;
				player.blockedHits++;
			}
			//player is casting heal
			if(player.healCooldownRemaining==0 && player.autoCastHealing) {
				player.useHealSpell();
				playerNextAction +=player.spellCastTime;
			}
			//player is casting poison
			if(player.poisonCooldownRemaining==0 && player.autoCastPoison) {
				
			}
			//player is casting thunder
			if(player.thunderCooldownRemaining==0 && player.autoCastThunder) {
				
			}
		}
		player.blockedHits = 0;
		//returning tracked stats
		newReturn.numberOfHits = numberOfHits;
		newReturn.expGain = (player.remainingHealth>0 ? tooLongFight ? 0 : mob.exp : 0);
		newReturn.fightTime = totalFightTime;
		return newReturn;
	}
}
