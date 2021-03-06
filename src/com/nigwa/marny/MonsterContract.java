/*
 * R�alis� par Martin Cesbron et Nicolas Saboureau � l'IIA de Laval
 * 
 *			***MonsterContract***
 * Permet de sch�matiser la table du monstre dans la BDD
 * 
 */
package com.nigwa.marny;

/**
 * Schema de la table monstre dans la BDD
 * @author HappyDestroy
 *
 */
public class MonsterContract {

	public final static String TABLE = "monster";
	
	public final static String COL_ID = "id";
	public final static String COL_RANK = "rank";
	public final static String COL_HEALTH = "health";
	public final static String COL_ATTACK = "attack";
	public final static String COL_ARMOR = "shield";
	
	public final static String[] COLS = { 
		COL_ID, 
		COL_RANK, 
		COL_HEALTH, 
		COL_ATTACK, 
		COL_ARMOR };
	
	public final static String SCHEMA = "CREATE TABLE " + TABLE + " ("
			+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_RANK + " INTEGER NOT NULL,"
			+ COL_HEALTH + " INTEGER NOT NULL,"
			+ COL_ATTACK + " INTEGER NOT NULL,"
			+ COL_ARMOR + " INTEGER NOT NULL"
			+ ")";
}
