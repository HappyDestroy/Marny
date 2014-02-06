package com.nigwa.marny;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SherlockActivity {

	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	
	/**
	 * Création de l'activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		SharedPreferences settings = getSharedPreferences("database",
				Context.MODE_PRIVATE);
		
		
		
		Button btnStart = (Button) findViewById(R.id.btn_start);
		
		/**
		 * Listenner de click sur le bouton pour commencer
		 */
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});

		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		this.fillBDD();
		
		//Si la database n'existe pas dans les préférences ont en fait la créer
		/*if(settings.getBoolean("database", false)) {

		}
		else {
			this.fillBDD();
			SharedPreferences.Editor editor = settings.edit();
			
			editor.putBoolean("database", true);
			editor.commit();
		}*/
		
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
		
		ContentValues valuesHero = new ContentValues();
		// Ajout du Héro
		valuesHero.put(HeroContract.COL_HEALTH, 50);
		valuesHero.put(HeroContract.COL_ATTACK, 15);
		valuesHero.put(HeroContract.COL_ARMOR, 0);
		valuesHero.put(HeroContract.COL_GOLD, 0);
		valuesHero.put(HeroContract.COL_HELMET, 0);
		valuesHero.put(HeroContract.COL_SHIELD, 0);
		valuesHero.put(HeroContract.COL_WEAPON, 0);
		
		db.insert(HeroContract.TABLE, null, valuesHero);
		

		ContentValues valuesMonster1 = new ContentValues();
		//Ajout du montre lvl 1 - Larbin
		valuesMonster1.put(MonsterContract.COL_RANK, 1);
		valuesMonster1.put(MonsterContract.COL_HEALTH, 20);
		valuesMonster1.put(MonsterContract.COL_ATTACK, 5);
		valuesMonster1.put(MonsterContract.COL_ARMOR, 0);

		db.insert(MonsterContract.TABLE, null, valuesMonster1);
		

		ContentValues valuesMonster2 = new ContentValues();
		//Ajout du monstre lvl 2 - Soldat
		valuesMonster2.put(MonsterContract.COL_RANK, 2);
		valuesMonster2.put(MonsterContract.COL_HEALTH, 45);
		valuesMonster2.put(MonsterContract.COL_ATTACK, 10);
		valuesMonster2.put(MonsterContract.COL_ARMOR, 10);

		db.insert(MonsterContract.TABLE, null, valuesMonster2);
		
	}
}
