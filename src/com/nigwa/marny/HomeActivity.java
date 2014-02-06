package com.nigwa.marny;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class HomeActivity extends SherlockActivity {

	private Hero myHero;
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private Helmet myHelmet;
	private Shield myShield;
	private Weapon myWeapon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		String[] VALUES = { "1" };
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(HeroContract.TABLE , HeroContract.COLS, "id LIKE" 
				,VALUES, null,
				null, null) ;
		c.moveToFirst();
		
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

			if(valueHelmet == 0) {
				myHelmet = new Helmet(0, 0, 0, 0, 0);
			}
			if(valueShield == 0) {
				myShield = new Shield(0, 0, 0, 0, 0);
			}
			if(valueWeapon == 0) {
				myWeapon = new Weapon(0, 0, 0, 0, 0);
			}
			
			myHero = new Hero(valueHealth, valueAttack, valueArmor, valueGold, 
					myHelmet, myShield, myWeapon, valuePotion);
			
		} while ( c.moveToNext() );
		
		Toast.makeText(this, myHero.toString(),Toast.LENGTH_LONG).show();
	}

}
