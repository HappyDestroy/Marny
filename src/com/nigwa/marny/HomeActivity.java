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

import com.actionbarsherlock.app.SherlockActivity;

public class HomeActivity extends SherlockActivity {

	private Hero myHero;
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private Helmet myHelmet;
	private Shield myShield;
	private Weapon myWeapon;
	private int nb_room;
	private int health_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button btn_shop = (Button) findViewById(R.id.home_btn_shop);
		Button btn_info_hero = (Button) findViewById(R.id.home_btn_info_hero);
		Button btn_go = (Button) findViewById(R.id.home_btn_go);
		
		//On remet le nombre de pièce visité a 0 
		nb_room = 0;
		
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

			//999 est l'id des equipements sans caractéritiques
			if(valueHelmet == 999) {
				myHelmet = new Helmet(999, 0, 0, 0, 0);
			}
			if(valueShield == 999) {
				myShield = new Shield(999, 0, 0, 0, 0);
			}
			if(valueWeapon == 999) {
				myWeapon = new Weapon(999, 0, 0, 0, 0);
			}
			
			myHero = new Hero(valueHealth, valueAttack, valueArmor, valueGold, 
					myHelmet, myShield, myWeapon, valuePotion);
			
		} while ( c.moveToNext() );
		
		//On met la vie restante de héro au maximum
		health_left = myHero.getHealth();
		
		//Button Shop
		btn_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HomeActivity.this, ShopRoomActivity.class));
				
			}
		});
		
		//Button Hero
		btn_info_hero.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//AlertDialog avec les informations sur le héro
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
					        		//La fenetre se ferme
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
				//Tirage a usort de la prochaine salle à visiter
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 11) {
					Intent intentMonsterRoom = new Intent(HomeActivity.this,
							MonsterActivity.class);
					
					intentMonsterRoom.putExtra("hero", myHero);
					intentMonsterRoom.putExtra("nb_room", nb_room);
					intentMonsterRoom.putExtra("health_left", health_left);
					
					startActivity(intentMonsterRoom);
				} else if(myRandom >= 12 && myRandom <= 14 ) {
					Intent intentGoldRoom = new Intent(HomeActivity.this,
							GoldRoomActivity.class);
					
					intentGoldRoom.putExtra("hero", myHero);
					intentGoldRoom.putExtra("nb_room", nb_room);
					intentGoldRoom.putExtra("health_left", health_left);
					
					startActivity(intentGoldRoom);
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					Intent intentShopRoom = new Intent(HomeActivity.this,
							ShopRoomActivity.class);
					
					intentShopRoom.putExtra("hero", myHero);
					intentShopRoom.putExtra("nb_room", nb_room);
					intentShopRoom.putExtra("health_left", health_left);
					
					startActivity(intentShopRoom);
				} else {
					Intent intentRestRoom = new Intent(HomeActivity.this,
							RestRoomActivity.class);
					
					intentRestRoom.putExtra("hero", myHero);
					intentRestRoom.putExtra("nb_room", nb_room);
					intentRestRoom.putExtra("health_left", health_left);
					
					startActivity(intentRestRoom);
				}
			}
		});
	}
}
