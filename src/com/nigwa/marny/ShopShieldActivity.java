package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;
import com.actionbarsherlock.app.SherlockActivity;
import com.nigwa.marny.Shield;
import com.nigwa.marny.R;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShopShieldActivity extends SherlockActivity {
	
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private ArrayList<Shield> myShield;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopshield);
		
		myShield = new ArrayList<Shield>();
		
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

			myShield.add(new Shield(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice));
			
		} while ( c.moveToNext() );
		
		
		Adapter myAdapter = new Adapter(this, R.layout.row_list, myShield);
		
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
			
			Shield myShield = this.getItem(position);
			
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

			valueHealth.setText(" "+String.valueOf(myShield.getHealthValue())+" HP");
			valueAttack.setText(" "+String.valueOf(myShield.getAttackValue())+" Attaque");
			valueArmor.setText(" "+String.valueOf(myShield.getArmorValue())+" Armure");
			
			return myView;
		}
	}
}
