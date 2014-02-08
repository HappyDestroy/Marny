package com.nigwa.marny;


import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShopRoomActivity extends SherlockActivity {

	private Hero myHero;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoproom);
		
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		
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
							ShopShieldActivity.class));
				} else if(arg2 == 2) {
					//Affichage Casques
					startActivity(new Intent(ShopRoomActivity.this, 
							ShopHelmetActivity.class));
				} else {
					//Affichage Potions
					startActivity(new Intent(ShopRoomActivity.this, 
							ShopWeaponActivity.class));
				}
			}
		});
	}
}
