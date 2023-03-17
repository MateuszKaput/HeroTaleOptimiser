
public class ReturnData {
	double fightTime = 0;
	double expGain = 0;
	double numberOfHits = 0;
	
	public void updateData(ReturnData fightStats) {
		this.expGain += fightStats.expGain;
		this.fightTime += fightStats.fightTime;
		this.numberOfHits += fightStats.numberOfHits;
	}
}

