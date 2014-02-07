package com.nigwa.marny;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.nigwa.marny.Weapon;
import com.nigwa.marny.R;


import android.content.ContentValues;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.SimpleAdapter;
import android.widget.ArrayAdapter;

public class ShopWeaponActivity extends Activity {
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
			/*myHero = new Hero(valueHealth, valueAttack, valueArmor, valueGold, 
					myHelmet, myShield, myWeapon, valuePotion);*/
			
		} while ( c.moveToNext() );
	
	}
}
