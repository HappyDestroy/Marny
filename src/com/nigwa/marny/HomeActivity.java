/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 ****HomeActivity****
 * Cette activity permet de commencer le jeu
 * C'est la deuxième activity du project. Elle offre 3 choix :
 * 
 * - Ouvrir une porte
 * - Accèder à la boutique
 * - Visualiser informations du Héro
 * 
 */
package com.nigwa.marny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class HomeActivity extends SherlockActivity {

	private Hero myHero;
	private int nb_room;
	private int health_left;
	private MediaPlayer sound_grincement;
	
	/**
	 * Methode à la création de l'activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Button btn_shop = (Button) findViewById(R.id.home_btn_shop);
		Button btn_info_hero = (Button) findViewById(R.id.home_btn_info_hero);
		Button btn_go = (Button) findViewById(R.id.home_btn_go);
		
		//On remet le nombre de pièce visité a 0 
		nb_room = 0;
		
		myHero = Tools.getHeroFromBDD(getApplicationContext());
		
		//On met la vie restante de héro au maximum
		health_left = myHero.getHealth() 
				+ myHero.getHelmet().getHealthValue()
				+ myHero.getShield().getHealthValue()
				+ myHero.getWeapon().getHealthValue();
		
		
		/**
		 * Listener sur le click du bouton de la boutique
		 */
		btn_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sound_grincement = MediaPlayer.create(
						HomeActivity.this, R.raw.grincement);
				sound_grincement.start();
				
				Intent myIntentShopRoom = new Intent(
						HomeActivity.this, ShopRoomActivity.class);
				
				myIntentShopRoom.putExtra("hero", myHero);
				
				startActivity(myIntentShopRoom);
			}
		});
		
		/**
		 * Listener sur le click du bouton info du héro
		 */
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
		
		/**
		 * Listener sur le click du bouton GO
		 */
		btn_go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tools.getNextRoom(HomeActivity.this, myHero, nb_room, health_left);
			}
		});
	}

	/**
	 * Lorsque l'on retourne sur cette activity (A la mort du héro)
	 */
	@Override
	protected void onResume() {
		
		myHero = Tools.getHeroFromBDD(this);
		
		//On met la vie restante de héro au maximum
		health_left = myHero.getHealth() 
				+ myHero.getHelmet().getHealthValue()
				+ myHero.getShield().getHealthValue()
				+ myHero.getWeapon().getHealthValue();
		
		super.onResume();
	}

}
