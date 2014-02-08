package com.nigwa.marny;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class GoldRoomActivity extends SherlockActivity {
	private MediaPlayer sound_coins = null;

	private Hero myHero;
	private int nb_room;
	private int health_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goldroom);
		//Ajout Son
		sound_coins = MediaPlayer.create(GoldRoomActivity.this, R.raw.coins);
		sound_coins.start();
		//TextViex infos recompense
		final TextView label_gold = (TextView) findViewById(
			R.id.gold_label_gold);
		
		//Génération des golds trouvés
		int myRandom = Tools.random(10,500);
		label_gold.setText("Vous venez de trouver " + myRandom + 
				" Golds !");
		
		//Ajout des golds aux héros
		myHero.setGold(myHero.getGold() + myRandom);
		Button btn_next = (Button) findViewById(R.id.goldroom_btn_next);
		
		//On récupère les extras depuis l'intent
		nb_room = getIntent().getIntExtra("nb_room", 1);
		nb_room++;
		
		health_left = getIntent().getIntExtra("health_left", 1); 
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 11) {
					Intent intentMonsterRoom = new Intent(GoldRoomActivity.this,
							MonsterActivity.class);
					
					intentMonsterRoom.putExtra("hero", myHero);
					intentMonsterRoom.putExtra("nb_room", nb_room);
					intentMonsterRoom.putExtra("health_left", health_left);
					
					startActivity(intentMonsterRoom);
				} else if(myRandom >= 12 && myRandom <= 14 ) {
					Intent intentGoldRoom = new Intent(GoldRoomActivity.this,
							GoldRoomActivity.class);
					
					intentGoldRoom.putExtra("hero", myHero);
					intentGoldRoom.putExtra("nb_room", nb_room);
					intentGoldRoom.putExtra("health_left", health_left);
					
					startActivity(intentGoldRoom);
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					Intent intentShopRoom = new Intent(GoldRoomActivity.this,
							ShopRoomActivity.class);
					
					intentShopRoom.putExtra("hero", myHero);
					intentShopRoom.putExtra("nb_room", nb_room);
					intentShopRoom.putExtra("health_left", health_left);
					
					startActivity(intentShopRoom);
				} else {
					Intent intentRestRoom = new Intent(GoldRoomActivity.this,
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
