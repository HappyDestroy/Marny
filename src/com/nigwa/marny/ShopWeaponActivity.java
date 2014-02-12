package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nigwa.marny.Weapon;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity pour gérer l'achat d'armes
 * @author HappyDestroy
 *
 */
public class ShopWeaponActivity extends SherlockActivity {
	
	private ArrayList<Weapon> myWeapons;
	private static Hero myHero;
	private MediaPlayer soudKaching = null;
	private Adapter myAdapter;
	
	/**
	 * Methode à la création de l'activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopweapon);
		
        // affiche la petite flèche back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		myWeapons = new ArrayList<Weapon>();
		myWeapons = Tools.getWeaponFromBDD(getApplicationContext(), null, null);
		
		
		myAdapter = new Adapter(this, R.layout.row_list, myWeapons);
		
		ListView myListViewWeapon = (ListView) findViewById(
				R.id.listViewWeapon);
		
		myListViewWeapon.setAdapter(myAdapter);
	}
	
	/**
	 * Classe de l'adapter pour la ListView
	 * @author HappyDestroy
	 *
	 */
	private class Adapter extends ArrayAdapter<Weapon>
	{
		private Context myContext;
		private int myRessource;
		private LayoutInflater myInflater;
		
		public Adapter(Context context, int resource, List<Weapon> objects) {
			super(context, resource, objects);
			this.myContext = context;
			this.myRessource = resource;
			
			this.myInflater = LayoutInflater.from(this.myContext);
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View myView = myInflater.inflate(this.myRessource, null);

			ImageView imgViewWeapon = (ImageView) myView.findViewById(
					R.id.img);
			TextView valueHealth = (TextView) myView.findViewById
					(R.id.health);
			TextView valueAttack = (TextView) myView.findViewById
					(R.id.attack);
			TextView valueArmor = (TextView) myView.findViewById
					(R.id.armor);
			TextView valuePrice = (TextView) myView.findViewById
					(R.id.price);			
			Button btn_buy = (Button) myView.findViewById(R.id.btn_buy);
			
			final Weapon myWeapon = this.getItem(position);
			
			
			switch(myWeapon.getId()) {
			case 1 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_1);
				break;
			case 2 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_2);
				break;
			case 3 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_3);
				break;
			case 4 :
				imgViewWeapon.setImageResource(R.drawable.sprite_blade_4);
				break;
			}
			
			if (myWeapon.getIsEquip() == 1) {
				btn_buy.setText(getContext().getString(R.string.equip));
				btn_buy.setEnabled(false);
			} else if (myWeapon.getIsBuy() == 1) {
				btn_buy.setText(getContext().getString(R.string.already_buy));
				btn_buy.setEnabled(true);
			}

			valueHealth.setText(" + "+String.valueOf(
					myWeapon.getHealthValue())+" HP");
			valueAttack.setText(" + "+String.valueOf(
					myWeapon.getAttackValue())+" Attaque");
			valueArmor.setText(" + "+String.valueOf(
					myWeapon.getArmorValue())+" Armure");
			valuePrice.setText(" "+String.valueOf(
					myWeapon.getPrice()));
			
			btn_buy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					String whereClause = "id = ? ";
					final String whereClauseWeaponUnequip = "isEQuip = ?";
	    			final String[] whereArgsWeaponUnequip = { "1" };
					//Si l'item est déjà acheté...
					if(myWeapon.getIsBuy() == 1) {
		        		//On dit que l'ancien item n'est plus dans 
		    			//l'état équipé
		    			ContentValues itemWeaponUnequip = 
		    					new ContentValues();
		    			
		    			itemWeaponUnequip.put("isEquip", 0);
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					WeaponContract.TABLE, 
		    					itemWeaponUnequip, 
		    					whereClauseWeaponUnequip, 
		    					whereArgsWeaponUnequip);
		            	
		        		//On ajoute l'item au héro
		    			ContentValues itemHero = new ContentValues();
		    			
		    			itemHero.put("weapon", myWeapon.getId());

		    			String[] whereArgsHero = { "1" };
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					HeroContract.TABLE, 
		    					itemHero, 
		    					whereClause, 
		    					whereArgsHero);
		    			
		    			//On indique a l'item qu'il est dans l'état équipé
		    			ContentValues itemWeaponEquip = 
		    					new ContentValues();
		    			
		    			itemWeaponEquip.put("isEquip", 1);
		    			
		    			String[] whereArgsWeaponEquip = { 
		    					String.valueOf(myWeapon.getId()) };
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					WeaponContract.TABLE, 
		    					itemWeaponEquip, 
		    					whereClause, 
		    					whereArgsWeaponEquip);
		    			
		    			//On rafraichit la ListView avec les 
		    			//nouveaux items
		    			Tools.refreshListViewShopWeapon(
		    					getApplicationContext(), myAdapter);
		    			
		        	} else {
						new AlertDialog.Builder(v.getContext())
					    .setTitle(R.string.confirm)
					    .setMessage(v.getContext().getString(
					    		R.string.msg_buy_begin)
					    		+ myWeapon.getPrice() 
					    		+ v.getContext().getString(
					    				R.string.msg_buy_end))
					    .setPositiveButton(android.R.string.yes, 
					    		new DialogInterface.OnClickListener() {
					        public void onClick(
					        		DialogInterface dialog, int which) { 
					        	//Si le héro n'est pas assez de gold on affiche 
					        	//un message
					            if(myHero.getGold() < myWeapon.getPrice()) {
					            	Toast.makeText(v.getContext(), 
					            			R.string.msg_error, 
					            			Toast.LENGTH_LONG).show();
					            } else {
					            	//Si le héros a assez de gold 
					            	//on confirme son achat
					            	Toast.makeText(v.getContext(), 
					            			R.string.msg_congrat, 
					            			Toast.LENGTH_LONG).show();
					            	//Son achat
					            	soudKaching = MediaPlayer.create(
					            			v.getContext(),
											 R.raw.ka_chingsound);
									soudKaching.start();
					            	//On dit que l'ancien item n'est plus dans 
					    			//l'état équipé
					    			ContentValues itemWeaponUnequip = 
					    					new ContentValues();
					    			
					    			itemWeaponUnequip.put("isEquip", 0);
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					WeaponContract.TABLE, 
					    					itemWeaponUnequip, 
					    					whereClauseWeaponUnequip, 
					    					whereArgsWeaponUnequip);
					            	
					    			
					    			//On débite le héro
					    			ContentValues itemHero =new ContentValues();
					    			itemHero.put("gold", myHero.getGold() - 
					    					myWeapon.getPrice());
					    			
					    			itemHero.put("weapon", myWeapon.getId());
	
					    			String whereClause = "id = ? ";
					    			String[] whereArgs = { "1" };
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					HeroContract.TABLE, 
					    					itemHero, 
					    					whereClause, 
					    					whereArgs);
					    			
					    			//Sauvegarde de l'état "acheté" dans la BDD
					    			ContentValues itemWeapon = 
					    					new ContentValues();
					    			
					    			itemWeapon.put("isBuy", 1);
					    			itemWeapon.put("isEquip", 1);
					    			
					    			String[] whereArgsWeapon = { 
					    					String.valueOf(myWeapon.getId()) };
					    			
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					WeaponContract.TABLE, 
					    					itemWeapon, 
					    					whereClause, 
					    					whereArgsWeapon);
					    			
					    			//On rafraichit la ListView avec les 
					    			//nouveaux items
					    			Tools.refreshListViewShopWeapon(
					    					getApplicationContext(), myAdapter);
					            }
					        }
					     })
					    .setNegativeButton(android.R.string.no, 
					    		new DialogInterface.OnClickListener() {
					        public void onClick(
					        		DialogInterface dialog, int which) { 
					            //Ferme la fenetre
					        }
					     })
					    .setIcon(R.drawable.ic_info_small)
					     .show();
					}
				}
			});
			
			return myView;
		}
	}
	
	/**
	 * Création du menu de l'actionBar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.gold_info, menu);
		MenuItem myMenu = menu.findItem(R.id.gold_info);
		myMenu.setTitle(String.valueOf(myHero.getGold()) 
				+ getApplication().getString(R.string.gold_text_info));
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

