package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.nigwa.marny.Helmet;
import com.nigwa.marny.R;

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
 * Classe g�rant l'achat de bouclier
 * @author HappyDestroy
 *
 */
public class ShopHelmetActivity extends SherlockActivity {
	
	private ArrayList<Helmet> myHelmets;
	private Hero myHero;
	private Adapter myAdapter;
	private MediaPlayer soudKaching = null;
	
	/**
	 * Methode � la cr�ation de l'activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shophelmet);
		
        // affiche la fl�che back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		myHelmets = new ArrayList<Helmet>();
		
		myHelmets = Tools.getHelmetFromBDD(getApplicationContext(), null, null);
		
		myAdapter = new Adapter(this, R.layout.row_list, myHelmets);
		
		ListView myListViewHelmet = (ListView) findViewById(
				R.id.listViewHelmet);
		
		myListViewHelmet.setAdapter(myAdapter);
	}
	
	
	/**
	 * Classe de l'adapter pour la ListView
	 * @author HappyDestroy
	 *
	 */
	private class Adapter extends ArrayAdapter<Helmet>
	{
		private Context myContext;
		private int myRessource;
		private LayoutInflater myInflater;
		
		public Adapter(Context context, int resource, List<Helmet> objects) {
			super(context, resource, objects);
			this.myContext = context;
			this.myRessource = resource;
			
			this.myInflater = LayoutInflater.from(this.myContext);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View myView = myInflater.inflate(this.myRessource, null);

			ImageView imgViewHelmet = (ImageView) myView.findViewById(
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
			
			final Helmet myHelmet = this.getItem(position);
			
			//Choix de l'image
			switch(myHelmet.getId()) {
			case 1 :
				imgViewHelmet.setImageResource(R.drawable.helmet_miner);
				break;
			case 2 :
				imgViewHelmet.setImageResource(R.drawable.helmet_light);
				break;
			case 3 :
				imgViewHelmet.setImageResource(R.drawable.helmet_arcane);
				break;
			case 4 :
				imgViewHelmet.setImageResource(R.drawable.helmet_dark);
				break;
			}
			
			//Changement de l'�tat des boutons
			if (myHelmet.getIsEquip() == 1) {
				btn_buy.setText(getContext().getString(R.string.equip));
				btn_buy.setEnabled(false);
			} else if (myHelmet.getIsBuy() == 1) {
				btn_buy.setText(getContext().getString(R.string.already_buy));
				btn_buy.setEnabled(true);
			}

			valueHealth.setText(" + "+String.valueOf(
					myHelmet.getHealthValue())+" HP");
			valueAttack.setText(" + "+String.valueOf(
					myHelmet.getAttackValue())+" Attaque");
			valueArmor.setText(" + "+String.valueOf(
					myHelmet.getArmorValue())+" Armure");
			valuePrice.setText(" "+String.valueOf(
					myHelmet.getPrice()));
			
			/**
			 * Listenner sur le click du bouton pour acheter / �quiper
			 */
			btn_buy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					
	    			String whereClause = "id = ? ";
	    			final String whereClauseHelmetUnequip = "isEQuip = ?";
	    			final String[] whereArgsHelmetUnequip = { "1" };
	    			
					//Si le casque est d�j� achet�...
					if(myHelmet.getIsBuy() == 1) {
		        		//On dit que l'ancien casque n'est plus dans 
		    			//l'�tat �quip�
		    			ContentValues itemHelmetUnequip = 
		    					new ContentValues();
		    			
		    			itemHelmetUnequip.put("isEquip", 0);
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					HelmetContract.TABLE, 
		    					itemHelmetUnequip, 
		    					whereClauseHelmetUnequip, 
		    					whereArgsHelmetUnequip);
		            	
		        		//On ajoute le casque au h�ro
		    			ContentValues itemHero = new ContentValues();
		    			
		    			itemHero.put("helmet", myHelmet.getId());

		    			String[] whereArgsHero = { "1" };
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					HeroContract.TABLE, 
		    					itemHero, 
		    					whereClause,
		    					whereArgsHero);
		    			
		    			//On indique au casque qu'il est dans l'�tat 
		    			//�quip�
		    			ContentValues itemHelmetEquip = 
		    					new ContentValues();
		    			
		    			itemHelmetEquip.put("isEquip", 1);
		    			
		    			String[] whereArgsHelmetEquip = { 
		    					String.valueOf(myHelmet.getId()) };
		    			
		    			Tools.updateBDD(getApplicationContext(), 
		    					HelmetContract.TABLE, 
		    					itemHelmetEquip, 
		    					whereClause,
		    					whereArgsHelmetEquip);
		    			
		    			//On rafraichit la ListView avec les nouveaux items
						/*ArrayList<Helmet> myNewHelmets = 
								new ArrayList<Helmet>();
						
		    			myAdapter.clear();
		    			
		    			myNewHelmets = Tools.getHelmetFromBDD(
		    					getApplicationContext(), null, null);
		    			
		    			myAdapter.addAll(myNewHelmets);
		    			myAdapter.notifyDataSetChanged();*/
		    			
		    			Tools.refreshListViewShopHelmet(
		    					getApplicationContext(), myAdapter);
		    			
		        	} else {
						new AlertDialog.Builder(v.getContext())
					    .setTitle(R.string.confirm)
					    .setMessage(v.getContext().getString(
					    		R.string.msg_buy_begin)
					    		+ myHelmet.getPrice() 
					    		+ v.getContext().getString(
					    				R.string.msg_buy_end))
					    .setPositiveButton(android.R.string.yes, 
					    		new DialogInterface.OnClickListener() {
					        public void onClick(
					        		DialogInterface dialog, int which) { 
					        	
				    			String whereClause = "id = ? ";
					        	//Si le h�ro n'est pas assez de gold on affiche 
					        	//un message
					            if(myHero.getGold() < myHelmet.getPrice()) {
					            	Toast.makeText(v.getContext(), 
					            			R.string.msg_error, 
					            			Toast.LENGTH_LONG).show();
					            } else {
					            	//Si le h�ros a assez de gold 
					            	//on confirme son achat
					            	Toast.makeText(v.getContext(), 
					            			R.string.msg_congrat, 
					            			Toast.LENGTH_LONG).show();
					            	
					            	//Son achat
					            	soudKaching = MediaPlayer.create(
					            			v.getContext(),
											 R.raw.ka_chingsound);
									soudKaching.start();
									
					            	//On dit que l'ancien casque n'est plus dans 
					    			//l'�tat �quip�
					    			ContentValues itemHelmetUnequip = 
					    					new ContentValues();
					    			
					    			itemHelmetUnequip.put("isEquip", 0);
					    			
					    			Tools.updateBDD(getApplicationContext(),
					    					HelmetContract.TABLE, 
					    					itemHelmetUnequip, 
					    					whereClauseHelmetUnequip,
					    					whereArgsHelmetUnequip);
					            	
					            	//Sauvegarde des info du h�ro en BDD
					    			ContentValues itemHero = 
					    					new ContentValues();
					    			
					    			itemHero.put("gold", myHero.getGold() - 
					    					myHelmet.getPrice());
					    			
					    			itemHero.put("helmet", myHelmet.getId());
					    			
					    			String[] whereArgsHero = { "1" };
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					HeroContract.TABLE, 
					    					itemHero, 
					    					whereClause, 
					    					whereArgsHero);
					    			
					    			
					    			//Sauvegarde de l'�tat "achet�" en BDD
					    			ContentValues itemHelmet = 
					    					new ContentValues();
					    			
					    			itemHelmet.put("isBuy", 1);
					    			itemHelmet.put("isEquip", 1);
	
					    			String[] whereArgsHelmet = { 
					    					String.valueOf(myHelmet.getId()) };
					    			
					    			Tools.updateBDD(getApplicationContext(), 
					    					HelmetContract.TABLE, 
					    					itemHelmet, 
					    					whereClause, 
					    					whereArgsHelmet);
					    			
					    			//On rafraichit la ListView avec les 
					    			//nouveaux items
					    			Tools.refreshListViewShopHelmet(
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
	 * Cr�ation du menu de l'actionBar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.gold_info, menu);
		MenuItem myMenu = menu.findItem(R.id.gold_info);
		myMenu.setTitle(String.valueOf(myHero.getGold()) + getApplication().
				getString(R.string.gold_text_info));
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
