package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nigwa.marny.Shield;
import com.nigwa.marny.R;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopShieldActivity extends SherlockActivity {
	
	private ArrayList<Shield> myShields;
	private Hero myHero;
	private Adapter myAdapter;
	private MediaPlayer soudKaching = null;
	
	/**
	 * Methode à la création de 'lactivity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopshield);
		
        // affiche la petite flèche back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		myShields = new ArrayList<Shield>();
		
		myShields = Tools.getShieldFromBDD(getApplicationContext(), null, null);
		
		myAdapter = new Adapter(this, R.layout.row_list, myShields);
		
		ListView myListViewShield = (ListView) findViewById(
				R.id.listViewShield);
		
		myListViewShield.setAdapter(myAdapter);
		
		
	}
	
	
	
	/**
	 * Classe de l'adapter pour la ListView
	 * @author HappyDestroy
	 *
	 */
	private class Adapter extends ArrayAdapter<Shield>
	{
		private Context myContext;
		private int myRessource;
		private LayoutInflater myInflater;
		
		public Adapter(Context context, int resource, List<Shield> objects) {
			super(context, resource, objects);
			this.myContext = context;
			this.myRessource = resource;
			
			this.myInflater = LayoutInflater.from(this.myContext);
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View myView = myInflater.inflate(this.myRessource, null);

			ImageView imgViewShield = (ImageView) myView.findViewById(
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
			
			final Shield myShield = this.getItem(position);
			
			switch(myShield.getId()) {
			case 1 :
				imgViewShield.setImageResource(R.drawable.sprite_shield_1);
				break;
			case 2 :
				imgViewShield.setImageResource(R.drawable.sprite_shield_2);
				break;
			case 3 :
				imgViewShield.setImageResource(R.drawable.sprite_shield_3);
				break;
			case 4 :
				imgViewShield.setImageResource(R.drawable.sprite_shield_4);
				break;
			}
			
			if (myShield.getIsEquip() == 1) {
				btn_buy.setText(getContext().getString(R.string.equip));
				btn_buy.setEnabled(false);
			} else if (myShield.getIsBuy() == 1) {
				btn_buy.setText(getContext().getString(R.string.already_buy));
				btn_buy.setEnabled(true);
			}
			valueHealth.setText(" + "+String.valueOf(
					myShield.getHealthValue())+" HP");
			valueAttack.setText(" + "+String.valueOf(
					myShield.getAttackValue())+" Attaque");
			valueArmor.setText(" + "+String.valueOf(
					myShield.getArmorValue())+" Armure");
			valuePrice.setText(" "+String.valueOf(
					myShield.getPrice()));
			
			btn_buy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					String whereClause = "id = ? ";
					final String whereClauseShieldUnequip = "isEQuip = ?";
	    			final String[] whereArgsShieldUnequip = { "1" };
					//Si l'item est déjà acheté...
					if(myShield.getIsBuy() == 1) {
		        		//On dit que l'ancien item n'est plus dans 
		    			//l'état équipé
		    			ContentValues itemShieldUnequip = 
		    					new ContentValues();
		    			
		    			itemShieldUnequip.put("isEquip", 0);
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					ShieldContract.TABLE, 
		    					itemShieldUnequip, 
		    					whereClauseShieldUnequip, 
		    					whereArgsShieldUnequip);
		    			
		        		//On ajoute l'item au héro
		    			ContentValues itemHero = new ContentValues();
		    			
		    			itemHero.put("helmet", myShield.getId());

		    			String[] whereArgsHero = { "1" };
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					HeroContract.TABLE, 
		    					itemHero, 
		    					whereClause, 
		    					whereArgsHero);
		    			
		    			//On indique a l'item qu'il est dans l'état équipé
		    			ContentValues itemShieldEquip = 
		    					new ContentValues();
		    			
		    			itemShieldEquip.put("isEquip", 1);
		    			
		    			String[] whereArgsShieldEquip = { 
		    					String.valueOf(myShield.getId()) };
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					ShieldContract.TABLE, 
		    					itemShieldEquip, 
		    					whereClause, 
		    					whereArgsShieldEquip);
		    			
		    			//On rafraichit la ListView avec les 
		    			//nouveaux items
		    			Tools.refreshListViewShopShield(
		    					getApplicationContext(), myAdapter);
		    			
		        	} else {
			        		new AlertDialog.Builder(v.getContext())
					    .setTitle(R.string.confirm)
					    .setMessage(v.getContext().getString(
					    		R.string.msg_buy_begin)
					    		+ myShield.getPrice() 
					    		+ v.getContext().getString(
					    				R.string.msg_buy_end))
					    .setPositiveButton(android.R.string.yes, 
					    		new DialogInterface.OnClickListener() {
					        public void onClick
					        (DialogInterface dialog, int which) { 
				    			String whereClause = "id = ? ";
					        	//Si le héro n'est pas assez de gold on affiche 
					        	//un message
					            if(myHero.getGold() < myShield.getPrice()) {
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
					    			ContentValues itemShieldUnequip = 
					    					new ContentValues();
					    			
					    			itemShieldUnequip.put("isEquip", 0);
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					ShieldContract.TABLE, 
					    					itemShieldUnequip, 
					    					whereClauseShieldUnequip, 
					    					whereArgsShieldUnequip);
					            	
					        		
					        		//On debite le héros
					    			ContentValues itemHero = 
					    					new ContentValues();
					    			
					    			itemHero.put("gold", myHero.getGold() - 
					    					myShield.getPrice());
					    			
					    			itemHero.put("shield", myShield.getId());
	
					    			String[] whereArgs = { "1" };
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					HeroContract.TABLE, 
					    					itemHero, 
					    					whereClause, 
					    					whereArgs);

					    			//Sauvegarde de l'état "acheté" dans la BDD
					    			ContentValues itemShield = 
					    					new ContentValues();
					    			
					    			itemShield.put("isBuy", 1);
					    			itemShield.put("isEquip", 1);
					    			
					    			String[] whereArgsShield = { 
					    					String.valueOf(myShield.getId()) };
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					ShieldContract.TABLE, 
					    					itemShield, 
					    					whereClause, 
					    					whereArgsShield);
					    			
					    			//On rafraichit la ListView avec les 
					    			//nouveaux items
					    			Tools.refreshListViewShopShield(
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
