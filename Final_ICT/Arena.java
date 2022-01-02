import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class Arena {

	public enum Row {Front, Back};	//enum for specifying the front or back row
	public enum Team {A, B};		//enum for specifying team A or B
	
	private Player[][] teamA = null;	//two dimensional array representing the players of Team A
	private Player[][] teamB = null;	//two dimensional array representing the players of Team B
	private int numRowPlayers = 0;		//number of players in each row
	
	public static final int MAXROUNDS = 100;	//Max number of turn
	public static final int MAXEACHTYPE = 3;	//Max number of players of each type, in each team.
	private final Path logFile = Paths.get("battle_log.txt");
	
	private int numRounds = 0;	//keep track of the number of rounds so far
	private static boolean Action = false;  //True = Action Team A False Action Team B
	private boolean GameEnd = false;
	/**
	 * Constructor. 
	 * @param _numRowPlayers is the number of players in each row.
	 */
	public Arena(int _numRowPlayers)
	{	
		//INSERT YOUR CODE HERE
		numRowPlayers = _numRowPlayers;
		teamA = new Player[2][numRowPlayers];
		teamB = new Player[2][numRowPlayers];
		////Keep this block of code. You need it for initialize the log file. 
		////(You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////
		
	}
	
	/**
	 * Returns true if "player" is a member of "team", false otherwise.
	 * Assumption: team can be either Team.A or Team.B
	 * @param player
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team)
	{
		//INSERT YOUR CODE HERE
		for(int i=0;i<2;i++)
			for(int j=0;j<numRowPlayers;j++) {
				if(team == Team.A) 
					if(player.getType() == teamA[i][j].getType())
						return true;
				else if(team == Team.B)
					if(player.getType() == teamB[i][j].getType())
						return true;
				}					
		return false;
	}
	
	
	
	/**
	 * This methods receives a player configuration (i.e., team, type, row, and position), 
	 * creates a new player instance, and places him at the specified position.
	 * @param team is either Team.A or Team.B
	 * @param pType is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
	 * @param row	either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
	 */
	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position)
	{	
		//INSERT YOUR CODE HERE
		if(team == Team.A) {
			if(row == Row.Front)
				teamA[0][position-1] = new Player(pType);
			else if(row == Row.Back)
				teamA[1][position-1] = new Player(pType);
		}
		if(team == Team.B) {
				if(row == Row.Front)
					teamB[0][position-1] = new Player(pType);
				else if(row == Row.Back)
					teamB[1][position-1] = new Player(pType);
			}
	}
	
	
	/**
	 * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
	 * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
	 * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * @return
	 */
	public boolean validatePlayers()
	{
		//INSERT YOUR CODE HERE
		int Hc=0,Tc=0,Sc=0,Bc=0,Pc=0,Cc=0;
		for(int i=0;i<2;i++)
			for(int j=0;j<numRowPlayers;j++){
				switch(teamA[i][j].getType()) {
				case Healer:
					if(Hc<MAXEACHTYPE) {
						Hc++;
						break;}
					else
						return false;
				case Tank:
					if(Tc<MAXEACHTYPE) {
						Tc++;
						break;}
					else
						return false;
				case Samurai:
					if(Sc<MAXEACHTYPE) {
						Sc++;
						break;}
					else
						return false;
				case BlackMage:
					if(Bc<MAXEACHTYPE) {
						Bc++;
						break;}
					else
						return false;
				case Phoenix:
					if(Pc<MAXEACHTYPE) {
						Pc++;
						break;}
					else
						return false;
				case Cherry:
					if(Cc<MAXEACHTYPE) {
						Cc++;
						break;}
					else
						return false;
				}
			}
		Hc=0;Tc=0;Sc=0;Bc=0;Pc=0;Cc=0;
		for(int i=0;i<2;i++)
			for(int j=0;j<numRowPlayers;j++){
				switch(teamB[i][j].getType()) {
				case Healer:
					if(Hc<MAXEACHTYPE) {
						Hc++;
						break;}
					else
						return false;
				case Tank:
					if(Tc<MAXEACHTYPE) {
						Tc++;
						break;}
					else
						return false;
				case Samurai:
					if(Sc<MAXEACHTYPE) {
						Sc++;
						break;}
					else
						return false;
				case BlackMage:
					if(Bc<MAXEACHTYPE) {
						Bc++;
						break;}
					else
						return false;
				case Phoenix:
					if(Pc<MAXEACHTYPE) {
						Pc++;
						break;}
					else
						return false;
				case Cherry:
					if(Cc<MAXEACHTYPE) {
						Cc++;
						break;}
					else
						return false;
				}
			}
		return true;
	}
	
	
	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team)
	{
		//INSERT YOUR CODE HERE
		double sum = 0;
		for(int i=0;i<2;i++)
			for(int j=0;j<team[i].length;j++)
				sum += team[i][j].getCurrentHP();
		return sum;
	}
	
	/**
	 * Return the team (either teamA or teamB) whose number of alive players is higher than the other. 
	 * 
	 * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
	 * players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * @return
	 */
	public Player[][] getWinningTeam()
	{
		//INSERT YOUR CODE HERE	
		if(getSumHP(teamA)>=getSumHP(teamB))
			return teamA;
		else if(getSumHP(teamA)<getSumHP(teamB))
			return teamB;
		return null;
	}
	
	/**
	 * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
	 * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
	 * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
	 * not it is teamB's turn to do the same. 
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated.
	 * 2. The number of rounds exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by getWinningTeam().
	 */
	public void startBattle()
	{
		//INSERT YOUR CODE HERE
		while(numRounds <= MAXROUNDS) {
			numRounds++;
			Action = true; //Turn TeamA
			System.out.println("@ ROUND " + (numRounds));
			for(int i=0;i<2;i++)
				for(int j=0;j<numRowPlayers;j++) {
					teamA[i][j].takeAction(this);
				}
			if(getSumHP(teamB) == 0){
				displayArea(this,true);
				logAfterEachRound();
				PrintwinningTeam();
				break;
			}
			
			Action = false;//Turn TeamB
			for(int i=0;i<2;i++)
				for(int j=0;j<numRowPlayers;j++){
					if(GameEnd == false) {
						teamB[i][j].takeAction(this);
					if(getSumHP(teamB) == 0)
						GameEnd = true;
					}
				}	
			
			displayArea(this,true);
			logAfterEachRound();
			if(GameEnd) {
			PrintwinningTeam();
			break;
			}
			
		}
	}
	
	
	
	/**
	 * This method displays the current area state, and is already implemented for you.
	 * In startBattle(), you should call this method once before the battle starts, and 
	 * after each round ends. 
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose)
	{
		StringBuilder str = new StringBuilder();
		if(verbose)
		{
			str.append(String.format("%43s   %40s","Team A","")+"\t\t"+String.format("%-38s%-40s","","Team B")+"\n");
			str.append(String.format("%43s","BACK ROW")+String.format("%43s","FRONT ROW")+"  |  "+String.format("%-43s","FRONT ROW")+"\t"+String.format("%-43s","BACK ROW")+"\n");
			for(int i = 0; i < arena.numRowPlayers; i++)
			{
				str.append(String.format("%43s",arena.teamA[1][i])+String.format("%43s",arena.teamA[0][i])+"  |  "+String.format("%-43s",arena.teamB[0][i])+String.format("%-43s",arena.teamB[1][i])+"\n");
			}
		}
	
		str.append("@ Total HP of Team A = "+getSumHP(arena.teamA)+" @ Total HP of Team B = "+getSumHP(arena.teamB)+"\n\n");
		System.out.print(str.toString());
		
		
	}
	
	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
	 * You are not to modify this method, however, this method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the output look something like:
	 * 
	 * 1	47415.0	49923.0
	 * 2	44977.0	46990.0
	 * 3	42092.0	43525.0
	 * 4	44408.0	43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively. 
	 */
	private void logAfterEachRound()
	{
		try {
			Files.write(logFile, Arrays.asList(new String[]{numRounds+"\t"+getSumHP(teamA)+"\t"+getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void PrintwinningTeam() {
		if(getWinningTeam() == teamA)
			System.out.println("Team A won!");
		else
			System.out.println("Team B won!");
			
	}
	
	public int getNumrow(){
		return numRowPlayers;
	}
	
	public Player[][] getTeam(){
		if(Action == true)
			return teamA;
		
		return teamB;
	}

	public Player[][] getOpTeam(){
		if(Action == true)
			return teamB;
		
		return teamA;
	}
	
	public Player getTarget() {
			if(getTaunt() == null)
				return getLow();
			else
				return getTaunt();
	}
	
	public Player getTaunt() {
		if(Action == false) {
			for(int i=0;i<2;i++)
				for(int j=0;j<numRowPlayers;j++)
					if(teamA[i][j].isTaunting())
						return teamA[i][j];
		}
		else {
			for(int i=0;i<2;i++)
				for(int j=0;j<numRowPlayers;j++)
					if(teamB[i][j].isTaunting())
						return teamB[i][j];
			}
		
		return null;
		
	}
	
	public Player getLow() {
		Player FirstAlive = null;
		double health = 10000;
		int n=2;
		if(Action == true) {
			for(int i=0;i<n;i++)	
				for(int j=0;j<numRowPlayers;j++) {
					if(FirstAlive == null) {
						if(teamB[i][j].isAlive()) {
							FirstAlive = teamB[i][j];
							health = FirstAlive.getCurrentHP();
							if(i==0)
								n = i-1;
						}
					}
					else 
						if(teamB[i][j].isAlive() && teamB[i][j].getCurrentHP()<health) {
							FirstAlive = teamB[i][j];
							health = FirstAlive.getCurrentHP();
							}
				}
			return FirstAlive;
		}
		
		else {
			for(int i=0;i<n;i++)	
				for(int j=0;j<numRowPlayers;j++) {
					if(FirstAlive == null) {
						if(teamA[i][j].isAlive()) {
							FirstAlive = teamA[i][j];
							health = FirstAlive.getCurrentHP();
							if(i==0)
								n = i-1;
						}
					}
					else 
						if(teamA[i][j].isAlive() && teamA[i][j].getCurrentHP()<health) {
							FirstAlive = teamA[i][j];
							health = FirstAlive.getCurrentHP();}
				}
			return FirstAlive;
		}		
	}	
}
