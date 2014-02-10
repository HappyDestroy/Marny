package com.nigwa.marny;


import java.util.ArrayList;
import java.util.List;
import com.actionbarsherlock.app.SherlockActivity;
import com.nigwa.marny.Helmet;
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

public class ShopHelmetActivity extends SherlockActivity {
	
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private ArrayList<Helmet> myHelmet;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shophelmet);
		
		myHelmet = new ArrayList<Helmet>();
		
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

			myHelmet.add(new Helmet(valueID, valueHealth, valueAttack, 
					valueArmor, valuePrice));
			
		} while ( c.moveToNext() );
		
		
		Adapter myAdapter = new Adapter(this, R.layout.row_list, myHelmet);
		
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
			
			Helmet myHelmet = this.getItem(position);
			
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

			valueHealth.setText(" + "+String.valueOf(
					myHelmet.getHealthValue())+" HP");
			valueAttack.setText(" + "+String.valueOf(
					myHelmet.getAttackValue())+" Attaque");
			valueArmor.setText(" + "+String.valueOf(
					myHelmet.getArmorValue())+" Armure");
			valuePrice.setText(" "+String.valueOf(
					myHelmet.getPrice()));
			
			return myView;
		}
	}
}
