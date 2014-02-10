package com.nigwa.marny;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

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
		
        // affiche la petite fl�che back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		
		ListView listViewShopRoom = (ListView) findViewById(
				R.id.listViewShopRoom);
		
		String[] listeStrings = {"Armes","Boucliers","Casques", "Potions"};
		
		listViewShopRoom.setAdapter(new ArrayAdapter<String>
				(this, android.R.layout.simple_list_item_1, listeStrings));
		
		listViewShopRoom.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(arg2 == 0) {
					//Affichage Armes
					Intent myIntentWeapon = new Intent(ShopRoomActivity.this, 
							ShopWeaponActivity.class);
					
					myIntentWeapon.putExtra("hero", myHero);
					
					startActivity(myIntentWeapon);
				} else if(arg2 == 1) {
					//Affichage Boucliers
					Intent myIntentShield = new Intent(ShopRoomActivity.this, 
							ShopShieldActivity.class);
					
					myIntentShield.putExtra("hero", myHero);
					
					startActivity(myIntentShield);
				} else if(arg2 == 2) {
					//Affichage Casques
					Intent myIntentHelmet = new Intent(ShopRoomActivity.this, 
							ShopHelmetActivity.class);
					
					myIntentHelmet.putExtra("hero", myHero);
					
					startActivity(myIntentHelmet);
				} else {
					//Affichage Potions
					Intent myIntentPotion = new Intent(ShopRoomActivity.this, 
							ShopPotionActivity.class);
					
					myIntentPotion.putExtra("hero", myHero);
					
					startActivity(myIntentPotion);
				}
			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(ShopRoomActivity.this, HomeActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
