package com.nigwa.marny;

public class ShieldContract {

	public final static String TABLE = "shield";
	
	public final static String COL_ID = "id";
	public final static String COL_HEALTHVALUE = "healthValue";
	public final static String COL_ATTACKVALUE = "attackValue";
	public final static String COL_ARMORVALUE = "armorValue";
	public final static String COL_PRICE = "price";
	public final static String COL_ISBUY = "isBuy";
	public final static String COL_ISEQUIP = "isEquip";
	
	public final static String[] COLS = { 
		COL_ID, 
		COL_HEALTHVALUE, 
		COL_ATTACKVALUE, 
		COL_ARMORVALUE,
		COL_PRICE,
		COL_ISBUY, 
		COL_ISEQUIP };
	
	public final static String SCHEMA = "CREATE TABLE " + TABLE + " ("
			+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ COL_HEALTHVALUE + " INTEGER NOT NULL,"
			+ COL_ATTACKVALUE + " INTEGER NOT NULL,"
			+ COL_ARMORVALUE + " INTEGER NOT NULL,"
			+ COL_PRICE + " INTEGER NOT NULL,"
			+ COL_ISBUY + " INTEGER NOT NULL,"
			+ COL_ISEQUIP + " INTEGER NOT NULL"
			+ ")";
}