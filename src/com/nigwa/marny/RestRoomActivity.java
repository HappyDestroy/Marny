package com.nigwa.marny;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class RestRoomActivity extends SherlockActivity {

	private Hero myHero;
	private int nb_room;
	private int health_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restroom);
		
		
		//On récupère les extras depuis l'intent
		nb_room = getIntent().getIntExtra("nb_room", 1);
		nb_room++;
		
		health_left = getIntent().getIntExtra("health_left", 1); 
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		Button btn_next = (Button) findViewById(R.id.restroom_btn_next);
		
		//Comme c'est une chambre de repos, on remet toute la vie du héro
		health_left = myHero.getHealth();
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 11) {
					Intent intentMonsterRoom = new Intent(RestRoomActivity.this,
							MonsterActivity.class);
					
					intentMonsterRoom.putExtra("hero", myHero);
					intentMonsterRoom.putExtra("nb_room", nb_room);
					intentMonsterRoom.putExtra("health_left", health_left);
					
					startActivity(intentMonsterRoom);
				} else if(myRandom >= 12 && myRandom <= 14 ) {
					Intent intentGoldRoom = new Intent(RestRoomActivity.this,
							GoldRoomActivity.class);
					
					intentGoldRoom.putExtra("hero", myHero);
					intentGoldRoom.putExtra("nb_room", nb_room);
					intentGoldRoom.putExtra("health_left", health_left);
					
					startActivity(intentGoldRoom);
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					Intent intentShopRoom = new Intent(RestRoomActivity.this,
							ShopRoomActivity.class);
					
					intentShopRoom.putExtra("hero", myHero);
					intentShopRoom.putExtra("nb_room", nb_room);
					intentShopRoom.putExtra("health_left", health_left);
					
					startActivity(intentShopRoom);
				} else {
					Intent intentRestRoom = new Intent(RestRoomActivity.this,
							RestRoomActivity.class);
					
					intentRestRoom.putExtra("hero", myHero);
					intentRestRoom.putExtra("nb_room", nb_room);
					intentRestRoom.putExtra("health_left", health_left);
					
					startActivity(intentRestRoom);
				}
			}
		});
	}
	

	//Empêcher l'utilisation du bouton retour
	@Override
	public void onBackPressed() {
		Toast.makeText(this, getApplication().getString(R.string.btn_back), 
				Toast.LENGTH_LONG).show();
		return;
	}
}