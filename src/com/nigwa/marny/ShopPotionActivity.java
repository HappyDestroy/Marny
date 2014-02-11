package com.nigwa.marny;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Intent;


public class ShopPotionActivity extends SherlockActivity {
	private static SQLiteDatabase db;
	private static SQLiteOpenHelperClass dbHelper;
	private Hero myHero;
	private MediaPlayer soudKaching = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppotion);
		
        // affiche la petite flèche back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
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
	            			Toast.LENGTH_SHORT).show();
					
					soudKaching = MediaPlayer.create(ShopPotionActivity.this,
							 R.raw.ka_chingsound);
					soudKaching.start();
					
					//On debite le Hero
	    			ContentValues itemHero = new ContentValues();
	    			
	    			itemHero.put("gold", myHero.getGold());
	    			itemHero.put("potion", myHero.getPotion());
	    			
	    			String whereClause = "id = ? ";
	    			String[] whereArgs = { "1" };
	    			
	    			db.update(HeroContract.TABLE, itemHero, whereClause, 
	    					whereArgs);
				}
				else {
					Toast.makeText(ShopPotionActivity.this, 
	            			R.string.msg_error, 
	            			Toast.LENGTH_SHORT).show();
				}
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.gold_info, menu);
		MenuItem myMenu = menu.findItem(R.id.gold_info);
		myMenu.setTitle(String.valueOf(myHero.getGold()) + getApplication().getString(R.string.gold_text_info));
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(ShopPotionActivity.this, ShopRoomActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
