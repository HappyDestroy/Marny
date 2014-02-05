package com.nigwa.marny;

public class HeroContract {

	public final static String TABLE = "hero";
	
	public final static String COL_ID = "id";
	public final static String COL_HEALTH = "health";
	public final static String COL_ATTACK = "attack";
	public final static String COL_ARMOR = "armor";
	public final static String COL_GOLD = "gold";
	public final static String COL_HELMET = "helmet";
	public final static String COL_SHIELD = "shield";
	public final static String COL_WEAPON = "weapon";
	
	public final static String[] COLS = { 
		COL_ID, 
		COL_HEALTH, 
		COL_ATTACK, 
		COL_ARMOR,
		COL_GOLD,
		COL_HELMET,
		COL_SHIELD,
		COL_WEAPON };
	
	public final static String SCHEMA = "CREATE TABLE " + TABLE + " ("
			+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_HEALTH + " INTEGER NOT NULL,"
			+ COL_ATTACK + " INTEGER NOT NULL,"
			+ COL_ARMOR + " INTEGER NOT NULL,"
			+ COL_GOLD + " INTEGER NOT NULL,"
			+ COL_HELMET + " INTEGER NOT NULL,"	
			+ COL_WEAPON + " INTEGER NOT NULL,"
			+ COL_SHIELD + " INTEGER NOT NULL"
			+ ")";
}

