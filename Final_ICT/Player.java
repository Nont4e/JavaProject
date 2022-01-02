
public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;	//Attack power of this player
	private boolean Sleep,Curse,Taunt;
	private Player Cursetarget;
	private int NumSpT;
	private int Turn;
	
	//private Player target;
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public Player(PlayerType _type)
	{	
		switch(_type) {
		case Healer:
			type = PlayerType.Healer;
			maxHP = 4790;
			currentHP = maxHP;
			atk = 238;
			Sleep = false;
			Curse = false;
			Taunt = false;
			NumSpT = 4;
			Turn = 1;
			break;
		case Tank:
			type = PlayerType.Tank;
			maxHP = 5340;
			currentHP = maxHP;
			atk = 255;
			Sleep = false;
			Curse = false;
			Taunt = false;
			NumSpT = 4;
			Turn = 1;
			break;
		case Samurai:
			type = PlayerType.Samurai;
			maxHP = 4005;
			currentHP = maxHP;
			atk = 368;
			Sleep = false;
			Curse = false;
			Taunt = false;
			NumSpT = 3;
			Turn = 1;
			break;
		case BlackMage:
			type = PlayerType.BlackMage;
			maxHP = 4175;
			currentHP = maxHP;
			atk = 303;
			Sleep = false;
			Curse = false;
			Taunt = false;
			NumSpT = 4;
			Turn = 1;
			break;
		case Phoenix:
			type = PlayerType.Phoenix;
			maxHP = 4175;
			currentHP = maxHP;
			atk = 209;
			Sleep = false;
			Curse = false;
			Taunt = false;
			NumSpT = 8;
			Turn = 1;
			break;
		case Cherry:
			type = PlayerType.Cherry;
			maxHP = 3560;
			currentHP = maxHP;
			atk = 198;
			Sleep = false;
			Curse = false;
			Taunt = false;
			NumSpT = 4;
			Turn = 1;
			break;
		}
			
	}
	
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return currentHP;
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return type;
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		return maxHP;
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		if(Sleep==true)
			return true;
		
		return false;
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		if(Curse == true)
			return true;
		
		return false;
	}
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(currentHP <= 0)
			return false;
		
		return true;
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		if(Taunt == true)
			return true;
		
		return false;
	}
	
	public void attack(Player target) {
			if(target!=null) {
			target.currentHP -= this.atk;
			if(target.currentHP < 0) {
				target.currentHP = 0;
				ClearStatus(target);
				}
			}
	}
	
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		Player target;
		switch(this.type) {
		case Healer:
			target = getInjured(myTeam);
			if(target.isCursed() == false) {
				target.currentHP += target.maxHP*25/100;
				if(target.currentHP > target.maxHP)
					target.currentHP = target.maxHP;
			}
			break;
		case Tank:
			this.Taunt = true;
			break;
		case Samurai:
			target = getTarget(theirTeam);
			attack(target);
			attack(target);
			break;
		case BlackMage:
			target = getLow(theirTeam);
			if(target!=null) {
			target.Curse = true;
			this.Cursetarget = target;
			}
			break;
		case Phoenix:
			target = getDead(myTeam);
			if(target != null)
			target.currentHP = target.maxHP*30/100;
			break;
		case Cherry:
			for(int i=0;i<2;i++)
				for(int j=0;j<theirTeam[i].length;j++)
					if(theirTeam[i][j].isAlive())
						theirTeam[i][j].Sleep = true;
			break;	
		}
	}
	
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		if(this.isAlive()) {
				if(this.isSleeping() == false) {
					if(this.type == PlayerType.BlackMage && this.Cursetarget != null) {
						this.Cursetarget.Curse = false;
						this.Cursetarget = null;
						}
					if(this.type == PlayerType.Tank) {
						this.Taunt = false;}
					if(this.Turn % this.NumSpT != 0) {
						attack(arena.getTarget());
						this.Turn++;
					}
				else {
					useSpecialAbility(arena.getTeam(),arena.getOpTeam());
					this.Turn++;
					}
				}
				else
					this.Sleep = false;
		}
	}
	
	public Player getTarget(Player[][] team) {
		for(int i=0;i<2;i++)
			for(int j=0;j<team[i].length;j++)
				if(team[i][j].isTaunting())
					return team[i][j];
		
		return getLow(team);
	}

	public Player getLow(Player[][] team) {
		Player FirstAlive = null;
		double health = 10000;
		int n=2;
		for(int i=0;i<n;i++)	
			for(int j=0;j<team[i].length;j++) {
				if(FirstAlive == null) {
					if(team[i][j].isAlive()) {
						FirstAlive = team[i][j];
						health = FirstAlive.getCurrentHP();
						if(i==0)
							n = i-1;
					}
				}
					else 
						if(team[i][j].isAlive() && team[i][j].getCurrentHP()<health) {
							FirstAlive = team[i][j];
							health = FirstAlive.getCurrentHP();
						}	
			}	
		return FirstAlive;
	}
	
	public Player getInjured(Player[][] team) {
		Player FirstAlive = null;
		double health = 100;
		int n=2;
		for(int i=0;i<n;i++)	
			for(int j=0;j<team[i].length;j++) {
				if(FirstAlive == null) {
					if(team[i][j].isAlive()) {
						FirstAlive = team[i][j];
						health = FirstAlive.currentHP / FirstAlive.maxHP * 100;
						if(i==0)
							n = i-1;
					}
				}
					else 
						if(team[i][j].isAlive() && team[i][j].currentHP/team[i][j].maxHP*100<health) {
							FirstAlive = team[i][j];
							health = team[i][j].currentHP / team[i][j].maxHP * 100;
						}	
			}	
		return FirstAlive;
	}
	
	public Player getDead(Player[][] team) {
		for(int i=0;i<2;i++)
				for(int j=0;j<team[i].length;j++)
					if(team[i][j].isAlive() == false)
						return team[i][j];
		return null;	
	}
	
	public void ClearStatus(Player target) {
		if(this.isAlive()) {
		target.Turn = 1;
		target.Sleep = false;
		target.Taunt = false;
		target.Curse = false;
		}
	}
	
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	
	
}
