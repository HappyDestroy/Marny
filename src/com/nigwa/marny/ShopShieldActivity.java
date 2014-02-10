package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.nigwa.marny.Shield;
import com.nigwa.marny.R;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	
	private static SQLiteDatabase db;
	private static SQLiteOpenHelperClass dbHelper;
	private ArrayList<Shield> myShields;
	private static Hero myHero;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopshield);
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		myShields = new ArrayList<Shield>();
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(ShieldContract.TABLE , ShieldContract.COLS, null 
				,null, null, null, null);
		
		
		c.moveToFirst();
		do {
			int valueID = c.getInt(
					c.getColumnIndex(ShieldContract.COL_ID));
			int valueHealth = c.getInt(
					c.getColumnIndex(ShieldContract.COL_HEALTHVALUE));
			int valueAttack = c.getInt(
					c.getColumnIndex(ShieldContract.COL_ATTACKVALUE));
			int valueArmor = c.getInt(
					c.getColumnIndex(ShieldContract.COL_ARMORVALUE));
			int valuePrice = c.getInt(
					c.getColumnIndex(ShieldContract.COL_PRICE));
			int isBuy = c.getInt(
					c.getColumnIndex(ShieldContract.COL_ISBUY));

			myShields.add(new Shield(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice, isBuy));
			
		} while ( c.moveToNext() );
		
		
		Adapter myAdapter = new Adapter(this, R.layout.row_list, myShields);
		
		ListView myListViewShield = (ListView) findViewById(
				R.id.listViewShield);
		
		myListViewShield.setAdapter(myAdapter);
		
		
	}
	
	
	
	
	private static class Adapter extends ArrayAdapter<Shield>
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
					new AlertDialog.Builder(v.getContext())
				    .setTitle(R.string.confirm)
				    .setMessage(v.getContext().getString(R.string.msg_buy_begin)
				    		+ myShield.getPrice() 
				    		+ v.getContext().getString(R.string.msg_buy_end))
				    .setPositiveButton(android.R.string.yes, 
				    		new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	//Si le héro n'est pas assez de gold on affiche 
				        	//un message
				            if(myHero.getGold() < myShield.getPrice()) {
				            	Toast.makeText(v.getContext(), 
				            			R.string.msg_error, 
				            			Toast.LENGTH_LONG).show();
				            } else {
				            	//Si le héros a assez de gold on le félicite
				            	Toast.makeText(v.getContext(), 
				            			R.string.msg_congrat, 
				            			Toast.LENGTH_LONG).show();
				            	
				            	
				            	//Sauvegarde en BDD
				            	db = dbHelper.getWritableDatabase();
				        		
				        		//On debite le héros
				    			ContentValues itemHero = new ContentValues();
				    			itemHero.put("gold", myHero.getGold() - 
				    					myShield.getPrice());
				    			
				    			itemHero.put("shield", myShield.getId());

				    			String whereClause = "id = ? ";
				    			String[] whereArgs = { "1" };
				    			db.update(HeroContract.TABLE, itemHero, 
				    					whereClause, whereArgs);
				            }
				        }
				     })
				    .setNegativeButton(android.R.string.no, 
				    		new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            //Ferme la fenetre
				        }
				     })
				    .setIcon(R.drawable.ic_info_small)
				     .show();
				}
			});
			
			return myView;
		}
	}
}
