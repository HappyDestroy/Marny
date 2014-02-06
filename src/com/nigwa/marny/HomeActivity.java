package com.nigwa.marny;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class HomeActivity extends SherlockActivity {

	private Hero myHero;
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	
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
		
		
		Cursor c = db.query(HeroContract.TABLE , HeroContract.COLS, "id =" ,VALUES, null,
				null, null) ;
		c.moveToFirst();
		
		do {
			String valueHealth = c.getString(
					c.getColumnIndex(HeroContract.COL_HEALTH)) ;
			String valueAttack = c.getString(
					c.getColumnIndex(HeroContract.COL_ATTACK)) ;
			String valueArmor = c.getString(
					c.getColumnIndex(HeroContract.COL_ARMOR)) ;
			String valueGold = c.getString(
					c.getColumnIndex(HeroContract.COL_GOLD)) ;
			String valueHelmet = c.getString(
					c.getColumnIndex(HeroContract.COL_HELMET)) ;
			String valueShield = c.getString(
					c.getColumnIndex(HeroContract.COL_SHIELD)) ;
			String valueWeapon = c.getString(
					c.getColumnIndex(HeroContract.COL_WEAPON)) ;
			String valuePotion = c.getString(
					c.getColumnIndex(HeroContract.COL_POTION)) ;
			
			/*myHero = new Hero(valueHealth, valueAttack, valueArmor, valueGold, 
					valueHelmet, valueShield, valueWeapon, valuePotion);*/
			
		} while ( c.moveToNext() );
		
		
	}

}
