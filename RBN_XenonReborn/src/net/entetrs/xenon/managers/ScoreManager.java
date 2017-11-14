package net.entetrs.xenon.managers;

public class ScoreManager
{
	private static ScoreManager sm = new ScoreManager();
	
	public static ScoreManager getInstance()
	{
		return sm;
	}
	
	private int score = 0;
	
	public synchronized void add(int points)
	{
		score += points;
	}
	
	public int getScore()
	{
		return score;
	}

}
