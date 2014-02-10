package com.nigwa.marny;

import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Tools {
	
	/**
	 * Retourne un nombre aléatoire entre 0 et le nombre en paramètre
	 * @param length - La valuer max du nombre aléatoire
	 * @return int - Un nombre aléatoire
	 */
	public static int random(int length) {
		Random r = new Random();
		return r.nextInt(length);
	}
	
	/**
	 * Retourne un nombre aléatoire entre les 2 paramètres
	 * @param min - Le nombre minimum
	 * @param max - Le nombre maximum
	 * @return int - Un nombre aléatoire
	 */
	public static int random(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}
	
	
	public static Hero getHeroFromBDD(Context myContext) {
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		Helmet myHelmet = null;
		Shield myShield = null;
		Weapon myWeapon = null;
		Hero myHero;
		
		String[] VALUES = { "1" };
		
		dbHelper = new SQLiteOpenHelperClass(
				myContext, 
				"myDB", 
				null, 
				1);
		
		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(HeroContract.TABLE , HeroContract.COLS, "id LIKE ?" 
				,VALUES, null, null, null);
		c.moveToFirst();
		
		//On récupère le Hero depuis la BDD
		do {
			int valueHealth = c.getInt(
					c.getColumnIndex(HeroContract.COL_HEALTH)) ;
			int valueAttack = c.getInt(
					c.getColumnIndex(HeroContract.COL_ATTACK)) ;
			int valueArmor = c.getInt(
					c.getColumnIndex(HeroContract.COL_ARMOR)) ;
			int valueGold = c.getInt(
					c.getColumnIndex(HeroContract.COL_GOLD)) ;
			int valueHelmet = c.getInt(
					c.getColumnIndex(HeroContract.COL_HELMET)) ;
			int valueShield = c.getInt(
					c.getColumnIndex(HeroContract.COL_SHIELD)) ;
			int valueWeapon = c.getInt(
					c.getColumnIndex(HeroContract.COL_WEAPON)) ;
			int valuePotion = c.getInt(
					c.getColumnIndex(HeroContract.COL_POTION)) ;
	
			//999 est l'id des equipements sans caractéritiques
			if(valueHelmet == 999) {
				myHelmet = new Helmet(999, 0, 0, 0, 0,0);
			} else if(valueHelmet == 1) {
				myHelmet = getHelmetFromBDD(myContext, "1");
			} else if (valueHelmet == 2) {
				myHelmet = getHelmetFromBDD(myContext, "2");
			} else if (valueHelmet == 3) {
				myHelmet = getHelmetFromBDD(myContext, "3");
			} else if (valueHelmet == 4) {
				myHelmet = getHelmetFromBDD(myContext, "4");
			}
			
			
			if(valueShield == 999) {
				myShield = new Shield(999, 0, 0, 0, 0,0);
			} else if(valueShield == 1) {
				myShield = getShieldFromBDD(myContext, "1");
			} else if (valueShield == 2) {
				myShield = getShieldFromBDD(myContext, "2");
			} else if (valueShield == 3) {
				myShield = getShieldFromBDD(myContext, "3");
			} else if (valueShield == 4) {
				myShield = getShieldFromBDD(myContext, "4");
			}
			
			if(valueWeapon == 999) {
				myWeapon = new Weapon(999, 0, 0, 0, 0,0);
			} else if(valueWeapon == 1) {
				myWeapon = getWeaponFromBDD(myContext, "1");
			} else if (valueWeapon == 2) {
				myWeapon = getWeaponFromBDD(myContext, "2");
			} else if (valueWeapon == 3) {
				myWeapon = getWeaponFromBDD(myContext, "3");
			} else if (valueWeapon == 4) {
				myWeapon = getWeaponFromBDD(myContext, "4");
			}
				
				myHero = new Hero(valueHealth, valueAttack, valueArmor, 
						valueGold, myHelmet, myShield, myWeapon, valuePotion);
				
			} while ( c.moveToNext() );
		
		return myHero;
	}
	
	
	public static Helmet getHelmetFromBDD(Context myContext, String idHelmet) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		Helmet myHelmet;
		
		dbHelper = new SQLiteOpenHelperClass(
				myContext, 
				"myDB", 
				null, 
				1);
	
		db = dbHelper.getWritableDatabase();
		
		String[] VALUES = { idHelmet };
		Cursor c = db.query(
				HelmetContract.TABLE, HelmetContract.COLS, "id LIKE ?"
				, VALUES, null, null, null);
		c.moveToFirst();
		do {
			int valueID = c.getInt(c.getColumnIndex(HelmetContract.COL_ID));
			
			int valueHealth = c.getInt(c.getColumnIndex(
					HelmetContract.COL_HEALTHVALUE));
			
			int valueArmor = c.getInt(c.getColumnIndex(
					HelmetContract.COL_ARMORVALUE));
			
			int valueAttack = c.getInt(c.getColumnIndex(
					HelmetContract.COL_ATTACKVALUE));
			
			int valuePrice = c.getInt(c.getColumnIndex(
					HelmetContract.COL_PRICE));
			int isBuy = c.getInt(c.getColumnIndex(
					HelmetContract.COL_ISBUY));
			
			
			myHelmet = new Helmet(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice,isBuy);
		} while ( c.moveToNext() );
		
		return myHelmet;
	}
	
	
	public static Shield getShieldFromBDD(Context myContext, String idShield) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		Shield myShield;
		
		dbHelper = new SQLiteOpenHelperClass(
				myContext, 
				"myDB", 
				null, 
				1);
	
		db = dbHelper.getWritableDatabase();
		
		String[] VALUES = { idShield };
		Cursor c = db.query(
				ShieldContract.TABLE, ShieldContract.COLS, "id LIKE ?"
				, VALUES, null, null, null);
		c.moveToFirst();
		do {
			int valueID = c.getInt(c.getColumnIndex(ShieldContract.COL_ID));
			
			int valueHealth = c.getInt(c.getColumnIndex(
					ShieldContract.COL_HEALTHVALUE));
			
			int valueArmor = c.getInt(c.getColumnIndex(
					ShieldContract.COL_ARMORVALUE));
			
			int valueAttack = c.getInt(c.getColumnIndex(
					ShieldContract.COL_ATTACKVALUE));
			
			int valuePrice = c.getInt(c.getColumnIndex(
					ShieldContract.COL_PRICE));
			int isBuy = c.getInt(c.getColumnIndex(
					ShieldContract.COL_ISBUY));
			
			
			myShield = new Shield(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice,isBuy);
		} while ( c.moveToNext() );
		
		
		return myShield;
	}
	
	
	public static Weapon getWeaponFromBDD(Context myContext, String idWeapon) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		Weapon myWeapon;
		
		dbHelper = new SQLiteOpenHelperClass(
				myContext, 
				"myDB", 
				null, 
				1);
	
		db = dbHelper.getWritableDatabase();
		
		String[] VALUES = { idWeapon };
		Cursor c = db.query(
				WeaponContract.TABLE, WeaponContract.COLS, "id LIKE ?"
				, VALUES, null, null, null);
		
		c.moveToFirst();
		do {
			int valueID = c.getInt(c.getColumnIndex(WeaponContract.COL_ID));
			
			int valueHealth = c.getInt(c.getColumnIndex(
					WeaponContract.COL_HEALTHVALUE));
			
			int valueArmor = c.getInt(c.getColumnIndex(
					WeaponContract.COL_ARMORVALUE));
			
			int valueAttack = c.getInt(c.getColumnIndex(
					WeaponContract.COL_ATTACKVALUE));
			
			int valuePrice = c.getInt(c.getColumnIndex(
					WeaponContract.COL_PRICE));
			int isBuy = c.getInt(c.getColumnIndex(
					ShieldContract.COL_ISBUY));
			
			
			myWeapon = new Weapon(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice, isBuy);
		} while ( c.moveToNext() );
		
		return myWeapon;
	}
}
