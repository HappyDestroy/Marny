package com.nigwa.marny;


import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ShopRoomActivity extends SherlockActivity {

	private Hero myHero;
	private int nb_room;
	private int health_left;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoproom);
		
		
		//On récupère les extras depuis l'intent
		nb_room = getIntent().getIntExtra("nb_room", 1);
		nb_room++;
		
		health_left = getIntent().getIntExtra("health_left", 1); 
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		
		Button btn_next = (Button) findViewById(R.id.shoproom_btn_next);
		
		ListView listViewShopRoom = (ListView) findViewById(
				R.id.listViewShopRoom);
		
		String[] listeStrings = {"Armes","Boucliers","Casques", "Potions'"};
		
		listViewShopRoom.setAdapter(new ArrayAdapter<String>
				(this, android.R.layout.simple_list_item_1, listeStrings));
		
		listViewShopRoom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(arg2 == 0) {
					//Affichage Armes
					startActivity(new Intent(ShopRoomActivity.this, 
							ShopWeaponActivity.class));
				} else if(arg2 == 1) {
					//Affichage Boucliers
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				} else if(arg2 == 2) {
					//Affichage Casques
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				} else {
					//Affichage Potions
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				}
			}
		});
		
		
		
		
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 11) {
					Intent intentMonsterRoom = new Intent(ShopRoomActivity.this,
							MonsterActivity.class);
					
					intentMonsterRoom.putExtra("hero", myHero);
					intentMonsterRoom.putExtra("nb_room", nb_room);
					intentMonsterRoom.putExtra("health_left", health_left);
					
					startActivity(intentMonsterRoom);
				} else if(myRandom >= 12 && myRandom <= 14 ) {
					Intent intentGoldRoom = new Intent(ShopRoomActivity.this,
							GoldRoomActivity.class);
					
					intentGoldRoom.putExtra("hero", myHero);
					intentGoldRoom.putExtra("nb_room", nb_room);
					intentGoldRoom.putExtra("health_left", health_left);
					
					startActivity(intentGoldRoom);
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					Intent intentShopRoom = new Intent(ShopRoomActivity.this,
							ShopRoomActivity.class);
					
					intentShopRoom.putExtra("hero", myHero);
					intentShopRoom.putExtra("nb_room", nb_room);
					intentShopRoom.putExtra("health_left", health_left);
					
					startActivity(intentShopRoom);
				} else {
					Intent intentRestRoom = new Intent(ShopRoomActivity.this,
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
