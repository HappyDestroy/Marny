package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nigwa.marny.Weapon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopWeaponActivity extends SherlockActivity {
	
	private static SQLiteDatabase db;
	private static SQLiteOpenHelperClass dbHelper;
	private ArrayList<Weapon> myWeapons;
	private static Hero myHero;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopweapon);
		
        // affiche la petite flèche back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
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
			int isBuy = c.getInt(
					c.getColumnIndex(WeaponContract.COL_ISBUY));

			myWeapons.add(new Weapon(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice, isBuy));
			
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
					R.id.img);
			TextView valueHealth = (TextView) myView.findViewById
					(R.id.health);
			TextView valueAttack = (TextView) myView.findViewById
					(R.id.attack);
			TextView valueArmor = (TextView) myView.findViewById
					(R.id.armor);
			TextView valuePrice = (TextView) myView.findViewById
					(R.id.price);			
			Button btn_buy = (Button) myView.findViewById(R.id.btn_buy);
			
			final Weapon myWeapon = this.getItem(position);
			
			
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

			valueHealth.setText(" + "+String.valueOf(
					myWeapon.getHealthValue())+" HP");
			valueAttack.setText(" + "+String.valueOf(
					myWeapon.getAttackValue())+" Attaque");
			valueArmor.setText(" + "+String.valueOf(
					myWeapon.getArmorValue())+" Armure");
			valuePrice.setText(" "+String.valueOf(
					myWeapon.getPrice()));
			
			btn_buy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					
					new AlertDialog.Builder(v.getContext())
				    .setTitle(R.string.confirm)
				    .setMessage(v.getContext().getString(R.string.msg_buy_begin)
				    		+ myWeapon.getPrice() 
				    		+ v.getContext().getString(R.string.msg_buy_end))
				    .setPositiveButton(android.R.string.yes, 
				    		new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	//Si le héro n'est pas assez de gold on affiche 
				        	//un message
				            if(myHero.getGold() < myWeapon.getPrice()) {
				            	Toast.makeText(v.getContext(), 
				            			R.string.msg_error, 
				            			Toast.LENGTH_LONG).show();
				            } else {
				            	//Si le héros a assez de gold on le félicite
				            	Toast.makeText(v.getContext(), 
				            			R.string.msg_congrat, 
				            			Toast.LENGTH_LONG).show();
				            	//Sauvegarde en BDD
				            	
				            	db = dbHelper.getWritableDatabase();
				            	
				    			ContentValues itemHero = new ContentValues();
				    			itemHero.put("gold", myHero.getGold() - 
				    					myWeapon.getPrice());
				    			
				    			itemHero.put("weapon", myWeapon.getId());

				    			String whereClause = "id = ? ";
				    			String[] whereArgs = { "1" };
				    			db.update(HeroContract.TABLE, itemHero, 
				    					whereClause, whereArgs);
				            }
				        }
				     })
				    .setNegativeButton(android.R.string.no, 
				    		new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            //Ferme la fenetre
				        }
				     })
				    .setIcon(R.drawable.ic_info_small)
				     .show();
				}
			});
			
			return myView;
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(ShopWeaponActivity.this, ShopRoomActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
