/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 ****ShopRoomActivity****
 * Cette activity permet d'entrer dans la pièce "ShopRoom"
 *  Elle gére la boutique des équipements
 *  
 *  Permet d'accèder à la boutique :
 *  - Arme
 *  - Bouclier
 *  - Casque
 *  - Potion
 * 
 */
package com.nigwa.marny;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity pour gérer l'achat de potion
 * @author HappyDestroy
 *
 */
public class ShopRoomActivity extends SherlockActivity {

	private Hero myHero;
	
	/**
	 * Methode à la création de l'activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoproom);
		
        // affiche la petite flèche back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		
		ListView listViewShopRoom = (ListView) findViewById(
				R.id.listViewShopRoom);
		
		String[] listeStrings = {"Armes","Boucliers","Casques", "Potions"};
		
		listViewShopRoom.setAdapter(new ArrayAdapter<String>
				(this, android.R.layout.simple_list_item_1, listeStrings));
		
		/**
		 * Listenner sur un item de la listView
		 */
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
	
	
	/**
	 * Création du menu de l'actionBar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.gold_info, menu);
		MenuItem myMenu = menu.findItem(R.id.gold_info);
		myMenu.setTitle(String.valueOf(myHero.getGold()) + getApplication()
				.getString(R.string.gold_text_info));
		return true;
	}
	

	/**
	 * Sur le click d'un item de l'actionBar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	this.finish();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    
	}
}
