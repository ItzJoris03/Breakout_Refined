package com.breakout.utils;

import java.util.Date;

public class ScoreADT {
    // Fields
	private String user;
	private int score;
	private Date date;
	
	public ScoreADT(String user, int score, Date date) { initFields(user, score, date); }
	public ScoreADT(String user, int score) { initFields(user, score, new Date()); }
	
	private void initFields(String user, int score, Date date) {
		this.user = user;
		this.score = score;
		this.date = date;
	}
	
	public String getUser() { return this.user; }
	public int getScore() { return this.score; }
	public Date getDate() { return this.date; }
	
	public void setUser(String user) { this.user = user; }
	public void setScore(int score) { this.score = score; }
	public void setDate(Date date) { this.date = date; }
}
