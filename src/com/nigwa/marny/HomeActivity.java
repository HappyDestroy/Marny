package com.nigwa.marny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		Button btn_shop = (Button) findViewById(R.id.home_btn_shop);
		Button btn_info_hero = (Button) findViewById(R.id.home_btn_info_hero);
		Button btn_go = (Button) findViewById(R.id.home_btn_go);
		
		String[] VALUES = { "1" };
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(HeroContract.TABLE , HeroContract.COLS, "id LIKE ?" 
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
		
		//Button Shop
		btn_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, ShopRoomActivity.class));
				
			}
		});
		//Toast.makeText(this, myHero.toString(),Toast.LENGTH_LONG).show();
		//Button Hero
		btn_info_hero.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new AlertDialog.Builder(HomeActivity.this)
			    .setTitle("Informations du héro")
			    .setMessage(getApplicationContext().getString(R.string.health) 
			    			+ myHero.getHealth() + "\n"
			    			+ getApplicationContext().getString(R.string.attack)
			    			+ myHero.getAttack()+"\n"
			    			+ getApplicationContext().getString(R.string.armor)
			    			+ myHero.getArmor()+"\n"
			    			+ getApplicationContext().getString(R.string.gold)
			    			+ myHero.getGold() + "\n"
			    			+ getApplicationContext().getString(R.string.helmet)
			    			+ myHero.getHelmet() + "\n"
			    			+ getApplicationContext().getString(R.string.shield)
			    			+ myHero.getShield() + "\n"
			    			+ getApplicationContext().getString(R.string.weapon)
			    			+ myHero.getWeapon() + "\n"
			    			+ getApplicationContext().getString(R.string.potion)
			    			+ myHero.getPotion())
			    .setPositiveButton(android.R.string.ok, 
			    		new DialogInterface.OnClickListener() {
					        public void onClick(
					        		DialogInterface dialog, int which) { 
					            // continue with delete
					        }
			     })
			    .setIcon(R.drawable.ic_info_small)
			     .show();
			}
		});
		
		//Button GO
		btn_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 11) {
					startActivity(new Intent(HomeActivity.this, MonsterActivity.class));
				} else if(myRandom >= 12 && myRandom <= 14 ) {
					startActivity(new Intent(HomeActivity.this, GoldRoomActivity.class));
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					startActivity(new Intent(HomeActivity.this, ShopRoomActivity.class));
				} else {
					startActivity(new Intent(HomeActivity.this, RestRoomActivity.class));
				}
				//Toast.makeText(HomeActivity.this, String.valueOf(myRandom),Toast.LENGTH_SHORT).show();
			
		}
	});
}
}
