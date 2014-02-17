package com.nigwa.marny;

import java.util.ArrayList;
import java.util.Random;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

/**
 * Classe d'outils accessible depuis partout
 * @author HappyDestroy
 *
 */
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
	
	
	/**
	 * Retourne un objet hero depuis la BDD
	 * @param myContext - Le context de l'application
	 * @return Le héro stocké en BDD
	 */
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
		
		
		Cursor c = db.query(HeroContract.TABLE, HeroContract.COLS, "id LIKE ?",
				VALUES, null, null, null);
		c.moveToFirst();
		
		//On récupère le Hero depuis la BDD
		do {
			int valueHealth = c.getInt(
					c.getColumnIndex(HeroContract.COL_HEALTH));
			int valueAttack = c.getInt(
					c.getColumnIndex(HeroContract.COL_ATTACK));
			int valueArmor = c.getInt(
					c.getColumnIndex(HeroContract.COL_ARMOR));
			int valueGold = c.getInt(
					c.getColumnIndex(HeroContract.COL_GOLD));
			int valueHelmet = c.getInt(
					c.getColumnIndex(HeroContract.COL_HELMET));
			int valueShield = c.getInt(
					c.getColumnIndex(HeroContract.COL_SHIELD));
			int valueWeapon = c.getInt(
					c.getColumnIndex(HeroContract.COL_WEAPON));
			int valuePotion = c.getInt(
					c.getColumnIndex(HeroContract.COL_POTION));
	
			//999 est l'id des equipements sans caractéritiques
			if(valueHelmet == 999) {
				myHelmet = new Helmet(999, 0, 0, 0, 0, 0, 1);
			} else if(valueHelmet == 1) {
				String[] whereArgs = { "1" };
				myHelmet = getHelmetFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueHelmet == 2) {
				String[] whereArgs = { "2" };
				myHelmet = getHelmetFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueHelmet == 3) {
				String[] whereArgs = { "3" };
				myHelmet = getHelmetFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueHelmet == 4) {
				String[] whereArgs = { "4" };
				myHelmet = getHelmetFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			}
			
			
			if(valueShield == 999) {
				myShield = new Shield(999, 0, 0, 0, 0, 0, 1);
			} else if(valueShield == 1) {
				String[] whereArgs = { "1" };
				myShield = getShieldFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueShield == 2) {
				String[] whereArgs = { "2" };
				myShield = getShieldFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueShield == 3) {
				String[] whereArgs = { "3" };
				myShield = getShieldFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueShield == 4) {
				String[] whereArgs = { "4" };
				myShield = getShieldFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			}
			
			if(valueWeapon == 999) {
				myWeapon = new Weapon(999, 0, 0, 0, 0, 0, 1);
			} else if(valueWeapon == 1) {
				String[] whereArgs = { "1" };
				myWeapon = getWeaponFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueWeapon == 2) {
				String[] whereArgs = { "2" };
				myWeapon = getWeaponFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueWeapon == 3) {
				String[] whereArgs = { "3" };
				myWeapon = getWeaponFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			} else if (valueWeapon == 4) {
				String[] whereArgs = { "4" };
				myWeapon = getWeaponFromBDD(
						myContext, "id = ?", whereArgs).get(0);
			}
				
				myHero = new Hero(valueHealth, valueAttack, valueArmor, 
						valueGold, myHelmet, myShield, myWeapon, valuePotion);
				
			} while ( c.moveToNext() );
		
		return myHero;
	}
	
	/**
	 * Methode qui retourne une collection de casque depuis la BDD
	 * @param myContext - Le context de l'application
	 * @param whereClause - La clause WHERE
	 * @param whereArgs - Les arguments pour le WHERE
	 * @return une collection de casque
	 */
	public static ArrayList<Helmet> getHelmetFromBDD(Context myContext, 
			String whereClause, String[] whereArgs) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		ArrayList<Helmet> myHelmets = new ArrayList<Helmet>();
		
		dbHelper = new SQLiteOpenHelperClass(myContext, "myDB", null, 1);
		
		db = dbHelper.getWritableDatabase();
		
		Cursor c = db.query(
				HelmetContract.TABLE, HelmetContract.COLS, whereClause, 
				whereArgs, null, null, null);
		
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
			
			int isEquip = c.getInt(c.getColumnIndex(
					HelmetContract.COL_ISEQUIP));
			
			//On ajoute le casque dans la collection
			myHelmets.add(new Helmet(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice,isBuy, isEquip));
		} while ( c.moveToNext() );
		
		return myHelmets;
	}
	
	/**
	 * Methode qui retrourne une collection de bouclier
	 * @param myContext - Le context de l'application
	 * @param whereClause - La clause WHERE
	 * @param whereArgs - Les arguments pour la clause WHERE
	 * @return une collection de bouclier
	 */
	public static ArrayList<Shield> getShieldFromBDD(Context myContext, 
			String whereClause, String[] whereArgs) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		ArrayList<Shield> myShields = new ArrayList<Shield>();
		
		dbHelper = new SQLiteOpenHelperClass(
				myContext, 
				"myDB", 
				null, 
				1);
		
		db = dbHelper.getWritableDatabase();
		
		Cursor c = db.query(
				ShieldContract.TABLE, ShieldContract.COLS, whereClause
				, whereArgs, null, null, null);
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
			
			int isEquip = c.getInt(c.getColumnIndex(
					ShieldContract.COL_ISEQUIP));
		
			//On ajoute le bouclier dans la collection
			myShields.add(new Shield(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice,isBuy, isEquip));
		} while ( c.moveToNext() );
		
		
		return myShields;
	}
	
	/**
	 * Methode qui permet de retourne une collection d'armes
	 * @param myContext - Le context de l'application
	 * @param whereClause - La clause WHERE
	 * @param whereArgs - Les arguments pour la clause WHERE
	 * @return une collection d'armes
	 */
	public static ArrayList<Weapon> getWeaponFromBDD(Context myContext, 
			String whereClause, String[] whereArgs) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		ArrayList<Weapon> myWeapons = new ArrayList<Weapon>();
		
		dbHelper = new SQLiteOpenHelperClass(
				myContext, 
				"myDB", 
				null, 
				1);
	
		db = dbHelper.getWritableDatabase();
		
		Cursor c = db.query(
				WeaponContract.TABLE, WeaponContract.COLS, whereClause
				, whereArgs, null, null, null);
		
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
			
			int isEquip = c.getInt(c.getColumnIndex(
					ShieldContract.COL_ISEQUIP));
			
			//On ajoute l'arme dans la collection
			myWeapons.add(new Weapon(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice, isBuy, isEquip));
		} while ( c.moveToNext() );
		
		return myWeapons;
	}
	
	
	public static ArrayList<Monster> getMonsterFromBDD(Context myContext,
			String whereClause, String[] whereArgs) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		ArrayList<Monster> myMonsters = new ArrayList<Monster>();
		
		
		dbHelper = new SQLiteOpenHelperClass(myContext, "myDB", null, 1);
		
		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(MonsterContract.TABLE , MonsterContract.COLS, 
				whereClause ,whereArgs , null, null, null);
		
		c.moveToFirst();
		
		do {
			int valueRank = c.getInt(
					c.getColumnIndex(MonsterContract.COL_RANK));
			
			int valueHealth = c.getInt(
					c.getColumnIndex(MonsterContract.COL_HEALTH));
			
			int valueAttack = c.getInt(
					c.getColumnIndex(MonsterContract.COL_ATTACK));
			
			int valueShielsd = c.getInt(
					c.getColumnIndex(MonsterContract.COL_ARMOR));
			
			
			myMonsters.add(new Monster(valueRank, valueHealth, valueAttack, 
					valueShielsd));
			
		} while ( c.moveToNext() );
		
		return myMonsters;
	}
	
	/**
	 * Permet de mettre à jour la BDD.
	 * @param myContext - Le context de l'application
	 * @param TABLE - La table a mettre à jour
	 * @param ContentValues - Les valeurs à changer
	 * @param whereClause - La clause "WHERE"
	 * @param whereArgs - Les arguments pour la clause "WHERE"
	 */
	public static void updateBDD(Context myContext, String TABLE, 
			ContentValues ContentValues, String whereClause, 
			String[] whereArgs ) {
		
		SQLiteDatabase db;
		SQLiteOpenHelperClass dbHelper;
		
		dbHelper = new SQLiteOpenHelperClass(myContext, "myDB", null, 1);
	
		db = dbHelper.getWritableDatabase();
		
		db.update(TABLE, ContentValues, whereClause, whereArgs);
	}
	
	/**
	 * Permet de rafraichir la listView des casques
	 * @param myContext - Le context d l'application
	 * @param myAdapter - L'arrayAdapter des casques
	 */
	public static void refreshListViewShopHelmet(Context myContext, 
			ArrayAdapter<Helmet> myAdapter) {
		
		ArrayList<Helmet> myHelmets = new ArrayList<Helmet>();
		
		myAdapter.clear();
		
		myHelmets = Tools.getHelmetFromBDD(myContext, null, null);
		
		myAdapter.addAll(myHelmets);
		myAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Permet de rafraichir la listView des boucliers
	 * @param myContext - Le context de l'application
	 * @param myAdapter - L'arrayAdapter des boucliers
	 */
	public static void refreshListViewShopShield(Context myContext, 
			ArrayAdapter<Shield> myAdapter) {
		
		ArrayList<Shield> myShields = new ArrayList<Shield>();
		
		myAdapter.clear();
		
		myShields = Tools.getShieldFromBDD(myContext, null, null);
		
		myAdapter.addAll(myShields);
		myAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Permet de rafraichir les listView des armes
	 * @param myContext - Le context de l'application
	 * @param myAdapter - L'arrayAdapter des armes
	 */
	public static void refreshListViewShopWeapon(Context myContext, 
			ArrayAdapter<Weapon> myAdapter) {
		
		ArrayList<Weapon> myWeapons = new ArrayList<Weapon>();
		
		myAdapter.clear();
		
		myWeapons = Tools.getWeaponFromBDD(myContext, null, null);
		
		myAdapter.addAll(myWeapons);
		myAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Permet de choisir aléatoirement la prochaine salle à visiter
	 * @param myActivity - L'activité courante
	 * @param myHero - l'objet hero
	 * @param nb_room - le nombre de pièce visité
	 * @param health_left - la vie restante du héro
	 */
	public static void getNextRoom(SherlockActivity myActivity, Hero myHero, int nb_room, int health_left) {
		int myRandom = Tools.random(21);
		if(myRandom >= 0 && myRandom <= 13) {
			Intent intentMonsterRoom = new Intent(myActivity,
					MonsterRoomActivity.class);
			
			intentMonsterRoom.putExtra("hero", myHero);
			intentMonsterRoom.putExtra("nb_room", nb_room);
			intentMonsterRoom.putExtra("health_left", health_left);
			
			myActivity.startActivity(intentMonsterRoom);
		} else if(myRandom >= 14 && myRandom <= 17 ) {
			Intent intentGoldRoom = new Intent(myActivity,
					GoldRoomActivity.class);
			
			intentGoldRoom.putExtra("hero", myHero);
			intentGoldRoom.putExtra("nb_room", nb_room);
			intentGoldRoom.putExtra("health_left", health_left);
			
			myActivity.startActivity(intentGoldRoom);
		} else if (myRandom >= 18 && myRandom <= 19) {
			Intent intentRestRoom = new Intent(myActivity,
					RestRoomActivity.class);
			
			intentRestRoom.putExtra("hero", myHero);
			intentRestRoom.putExtra("nb_room", nb_room);
			intentRestRoom.putExtra("health_left", health_left);
			
			myActivity.startActivity(intentRestRoom);
		} else {
			Intent intentShakeRoom = new Intent(myActivity,
					ShakeRoomActivity.class);
			
			intentShakeRoom.putExtra("hero", myHero);
			intentShakeRoom.putExtra("nb_room", nb_room);
			intentShakeRoom.putExtra("health_left", health_left);
			
			myActivity.startActivity(intentShakeRoom);
		}
	}
}
