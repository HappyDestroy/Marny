package com.nigwa.marny;


import com.actionbarsherlock.app.SherlockActivity;
import com.nigwa.marny.Weapon;
import com.nigwa.marny.R;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class ShopWeaponActivity extends SherlockActivity {
	private Hero myHero;
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private Weapon myWeapon;
	private ListView myListViewWeapon;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoproom);
	
		String[] VALUES = { "1" };
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(WeaponContract.TABLE , WeaponContract.COLS, null 
				,null, null,
				null, null) ;
		c.moveToFirst();
		//ArrayList<value> myListPerso = new ArrayList<value>();
		
		do {
			int valueHealth = c.getInt(
					c.getColumnIndex(WeaponContract.COL_HEALTHVALUE)) ;
			int valueAttack = c.getInt(
					c.getColumnIndex(WeaponContract.COL_ATTACKVALUE)) ;
			int valueArmor = c.getInt(
					c.getColumnIndex(WeaponContract.COL_ARMORVALUE)) ;
			int valuePrice = c.getInt(
					c.getColumnIndex(WeaponContract.COL_PRICE)) ;
			//myAdapter myAdapter = new myAdapter(this, R.layout.activity_shopweapon, valueHealth,valueAttack,valueArmor,valuePrice);
			
			//ListView myList = (ListView) findViewById(R.id.listViewWeapon);
			
			//myList.setAdapter(myAdapter);

			/*myHero = new Hero(valueHealth, valueAttack, valueArmor, valueGold, 
					myHelmet, myShield, myWeapon, valuePotion);*/
			
		} while ( c.moveToNext() );

	}
}
