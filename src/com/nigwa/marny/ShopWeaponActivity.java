package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.nigwa.marny.Weapon;
import com.nigwa.marny.R;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShopWeaponActivity extends SherlockActivity {
	
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private ArrayList<Weapon> myWeapons;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopweapon);
		
		myWeapons = new ArrayList<Weapon>();
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(WeaponContract.TABLE , WeaponContract.COLS, null 
				,null, null, null, null);
		
		
		c.moveToFirst();
		do {
			int valueID = c.getInt(
					c.getColumnIndex(WeaponContract.COL_ID));
			int valueHealth = c.getInt(
					c.getColumnIndex(WeaponContract.COL_HEALTHVALUE));
			int valueAttack = c.getInt(
					c.getColumnIndex(WeaponContract.COL_ATTACKVALUE));
			int valueArmor = c.getInt(
					c.getColumnIndex(WeaponContract.COL_ARMORVALUE));
			int valuePrice = c.getInt(
					c.getColumnIndex(WeaponContract.COL_PRICE));

			myWeapons.add(new Weapon(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice));
			
		} while ( c.moveToNext() );
		
		
		Adapter myAdapter = new Adapter(this, R.layout.row_list, myWeapons);
		
		ListView myListViewWeapon = (ListView) findViewById(
				R.id.listViewWeapon);
		
		myListViewWeapon.setAdapter(myAdapter);
	}
	
	private static class Adapter extends ArrayAdapter<Weapon>
	{
		private Context myContext;
		private int myRessource;
		private LayoutInflater myInflater;
		
		public Adapter(Context context, int resource, List<Weapon> objects) {
			super(context, resource, objects);
			this.myContext = context;
			this.myRessource = resource;
			
			this.myInflater = LayoutInflater.from(this.myContext);
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View myView = myInflater.inflate(this.myRessource, null);

			ImageView imgViewWeapon = (ImageView) myView.findViewById(
					R.id.img_weapon);
			TextView valueHealth = (TextView) myView.findViewById
					(R.id.health_weapon);
			TextView valueAttack = (TextView) myView.findViewById
					(R.id.attack_weapon);
			TextView valueArmor = (TextView) myView.findViewById
					(R.id.armor_weapon);
			
			Weapon myWeapon = this.getItem(position);
			
			switch(myWeapon.getId()) {
			case 1 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_1);
				break;
			case 2 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_2);
				break;
			case 3 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_3);
				break;
			case 4 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_4);
				break;
			}

			valueHealth.setText(String.valueOf(myWeapon.getHealthValue()));
			valueAttack.setText(String.valueOf(myWeapon.getAttackValue()));
			valueArmor.setText(String.valueOf(myWeapon.getArmorValue()));
			
			return myView;
		}
	}
}
