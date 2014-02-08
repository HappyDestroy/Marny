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
		/*Hero.setGold = Hero.getGold() + myRandom;*/
		Button btn_next = (Button) findViewById(R.id.goldroom_btn_next);
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 12) {
					startActivity(new Intent(GoldRoomActivity.this, MonsterActivity.class));
				} else if(myRandom >= 13 && myRandom <= 16 ) {
					startActivity(new Intent(GoldRoomActivity.this, GoldRoomActivity.class));
				} else if(myRandom >= 17 && myRandom <= 18 ) {
					startActivity(new Intent(GoldRoomActivity.this, ShopRoomActivity.class));
				} else {
					startActivity(new Intent(GoldRoomActivity.this, RestRoomActivity.class));
				}
			}
		});
	}


}
