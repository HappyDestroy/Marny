package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nigwa.marny.Helmet;
import com.nigwa.marny.R;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class ShopHelmetActivity extends SherlockActivity {
	
	private static SQLiteDatabase db;
	private static SQLiteOpenHelperClass dbHelper;
	private ArrayList<Helmet> myHelmets;
	private static Hero myHero;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shophelmet);
		
        // affiche la petite flèche back
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		myHelmets = new ArrayList<Helmet>();
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(HelmetContract.TABLE , HelmetContract.COLS, null 
				,null, null, null, null);
		
		
		c.moveToFirst();
		do {
			int valueID = c.getInt(
					c.getColumnIndex(HelmetContract.COL_ID));
			int valueHealth = c.getInt(
					c.getColumnIndex(HelmetContract.COL_HEALTHVALUE));
			int valueAttack = c.getInt(
					c.getColumnIndex(HelmetContract.COL_ATTACKVALUE));
			int valueArmor = c.getInt(
					c.getColumnIndex(HelmetContract.COL_ARMORVALUE));
			int valuePrice = c.getInt(
					c.getColumnIndex(HelmetContract.COL_PRICE));
			int isBuy = c.getInt(
					c.getColumnIndex(HelmetContract.COL_ISBUY));
			int isEquip = c.getInt(
					c.getColumnIndex(HelmetContract.COL_ISEQUIP));

			myHelmets.add(new Helmet(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice, isBuy, isEquip));
			
		} while ( c.moveToNext() );
		
		
		final Adapter myAdapter = new Adapter(this, R.layout.row_list, myHelmets);
		
		ListView myListViewHelmet = (ListView) findViewById(
				R.id.listViewHelmet);
		
		myListViewHelmet.setAdapter(myAdapter);
	}
	
	
	
	private static class Adapter extends ArrayAdapter<Helmet>
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
			
			
			btn_buy.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(final View v) {
					
	    			String whereClause = "id = ? ";
	    			final String whereClauseHelmetUnequip = "isEQuip = ?";
	    			final String[] whereArgsHelmetUnequip = { "1" };
	    			
					//Si le casque est déjà acheté...
					if(myHelmet.getIsBuy() == 1) {
		        		//On dit que l'ancien casque n'est plus dans 
		    			//l'état équipé
		    			ContentValues itemHelmetUnequip = 
		    					new ContentValues();
		    			
		    			itemHelmetUnequip.put("isEquip", 0);
		    			
		    			
		    			db.update(HelmetContract.TABLE, 
		    					itemHelmetUnequip, 
		    					whereClauseHelmetUnequip, 
		    					whereArgsHelmetUnequip);
		            	
		        		//On ajoute le casque au héro
		    			ContentValues itemHero = new ContentValues();
		    			
		    			itemHero.put("helmet", myHelmet.getId());

		    			String[] whereArgsHero = { "1" };
		    			db.update(HeroContract.TABLE, itemHero, 
		    					whereClause, whereArgsHero);
		    			
		    			//On indique au casque qu'il est dans l'état 
		    			//équipé
		    			ContentValues itemHelmetEquip = 
		    					new ContentValues();
		    			
		    			itemHelmetEquip.put("isEquip", 1);
		    			
		    			String[] whereArgsHelmetEquip = { 
		    					String.valueOf(myHelmet.getId()) };
		    			
		    			db.update(HelmetContract.TABLE, itemHelmetEquip, 
		    					whereClause, 
		    					whereArgsHelmetEquip);
		    			
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
					        	//Si le héro n'est pas assez de gold on affiche 
					        	//un message
					            if(myHero.getGold() < myHelmet.getPrice()) {
					            	Toast.makeText(v.getContext(), 
					            			R.string.msg_error, 
					            			Toast.LENGTH_LONG).show();
					            } else {
					            	//Si le héros a assez de gold on le félicite
					            	Toast.makeText(v.getContext(), 
					            			R.string.msg_congrat, 
					            			Toast.LENGTH_LONG).show();
					            	
					            	//On dit que l'ancien casque n'est plus dans 
					    			//l'état équipé
					    			ContentValues itemHelmetUnequip = 
					    					new ContentValues();
					    			
					    			itemHelmetUnequip.put("isEquip", 0);
					    			
					    			db.update(HelmetContract.TABLE, 
					    					itemHelmetUnequip, 
					    					whereClauseHelmetUnequip, 
					    					whereArgsHelmetUnequip);
					            	
					            	//Sauvegarde des info du héro en BDD
					    			ContentValues itemHero = 
					    					new ContentValues();
					    			
					    			itemHero.put("gold", myHero.getGold() - 
					    					myHelmet.getPrice());
					    			
					    			itemHero.put("helmet", myHelmet.getId());
					    			
					    			String[] whereArgsHero = { "1" };
					    			db.update(HeroContract.TABLE, itemHero, 
					    					whereClause, whereArgsHero);
					    			
					    			//Sauvegarde de l'état "acheté" en BDD
					    			ContentValues itemHelmet = 
					    					new ContentValues();
					    			
					    			itemHelmet.put("isBuy", 1);
					    			itemHelmet.put("isEquip", 1);
	
					    			String[] whereArgsHelmet = { 
					    					String.valueOf(myHelmet.getId()) };
					    			
					    			db.update(HelmetContract.TABLE, itemHelmet, 
					    					whereClause, whereArgsHelmet);
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
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(ShopHelmetActivity.this, ShopRoomActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
