package com.nigwa.marny;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SherlockActivity {

	private ActionBar _actionBar;
	
	/**
	 * Création de l'activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		_actionBar = getSupportActionBar();
		
		
		Button btnStart = (Button) findViewById(R.id.btn_start);
		
		/**
		 * Listenner de click sur le bouton pour commencer
		 */
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		
		SQLiteOpenHelperClass dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);
		
		final SQLiteDatabase db = dbHelper.getWritableDatabase();
		this.fillBDD();
	}

	/**
	 * Création du menu de l'actionBar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		return true;
	}

	/**
	 * Évènement d'un click sur un item de l'actionBar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		new AlertDialog.Builder(this)
	    .setTitle(R.string.menu_info)
	    .setMessage(R.string.info)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // Fermeture de la fenêtre
	        }
	     })
	    .setIcon(R.drawable.ic_info_small)
	     .show();
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Rempli la BDD avec les infos du jeu
	 */
	private void fillBDD() {
		ContentValues values = new ContentValues();
		
		// Ajout du Héro
		values.put(HeroContract.COL_HEALTH, 50);
		values.put(HeroContract.COL_ATTACK, 50);
		values.put(HeroContract.COL_ARMOR, 50);
		values.put(HeroContract.COL_GOLD, 50);
		values.put(HeroContract.COL_HELMET, 50);
		values.put(HeroContract.COL_SHIELD, 50);
		values.put(HeroContract.COL_WEAPON, 50);
	}
}
