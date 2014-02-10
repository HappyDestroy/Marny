package com.nigwa.marny;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.ContentValues;


public class ShopPotionActivity extends SherlockActivity {
	private static SQLiteDatabase db;
	private static SQLiteOpenHelperClass dbHelper;
	private Hero myHero;
	private MediaPlayer soudKaching = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppotion);
		
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);
		db = dbHelper.getWritableDatabase();
		Button btn_buy = (Button) findViewById(R.id.potion_btn_buy);
		
			btn_buy.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//On vérifie que le héros a assez d'argent
					if(myHero.getGold() >= 25 ){
						myHero.setPotion(myHero.getPotion() + 1);
						myHero.setGold(myHero.getGold() - 25);
						Toast.makeText(ShopPotionActivity.this, 
		            			R.string.succes_potion, 
		            			Toast.LENGTH_LONG).show();
						soudKaching = MediaPlayer.create(ShopPotionActivity.this,
								 R.raw.ka_chingsound);
						soudKaching.start();
						//On debite le Hero
		    			ContentValues itemHero = new ContentValues();
		    			itemHero.put("gold", myHero.getGold() - 
		    					25);
		    			//On incremente le nbr de potions
		    			itemHero.put("potion", myHero.getPotion() + 
		    					1);
		    			String whereClause = "id = ? ";
		    			String[] whereArgs = { "1" };
		    			db.update(HeroContract.TABLE, itemHero, 
		    					whereClause, whereArgs);
					}
					else {
						Toast.makeText(ShopPotionActivity.this, 
		            			R.string.msg_error, 
		            			Toast.LENGTH_LONG).show();
					}
				}
				
			});
	}
}

