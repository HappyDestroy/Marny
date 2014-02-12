/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 ****MainActivity****
 * Cette activity permet de créer la BDD si ce n'est pas déjà le cas
 * C'est aussi la première activity du project. Elle offre 2 choix :
 * 
 * - Démarrer le jeu
 * - Lancer le lecteur de flux RSS
 * 
 */
package com.nigwa.marny;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends SherlockActivity {

	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private MediaPlayer mp = null;
	
	
	/**
	 * Création de l'activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mp = MediaPlayer.create(MainActivity.this, R.raw.sound_game4);
		mp.start();
		mp.setLooping(true);
		SharedPreferences settings = getSharedPreferences("database",
				Context.MODE_PRIVATE);
		
		
		
		Button btnStart = (Button) findViewById(R.id.btn_start);
		Button btn_rss = (Button) findViewById(R.id.btn_rss);
		
		/**
		 * Listenner de click sur le bouton pour commencer
		 */
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, HomeActivity.class));
			}
		});

		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		//Si la database n'existe pas dans les préférences on l'ajoute
		if(settings.getBoolean("database", false)) {
			
		}
		else {
			this.fillBDD();
			
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("database", true);
			editor.commit();
		}
		
		
		btn_rss.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, 
						RSSReaderActivity.class));
				
			}
		});
		
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
	    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
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
		
		/******* HERO *********/
		ContentValues valuesHero = new ContentValues();
		// Ajout du Héro
		valuesHero.put(HeroContract.COL_HEALTH, 50);
		valuesHero.put(HeroContract.COL_ATTACK, 15);
		valuesHero.put(HeroContract.COL_ARMOR, 0);
		valuesHero.put(HeroContract.COL_GOLD, 0);
		valuesHero.put(HeroContract.COL_HELMET, 999);
		valuesHero.put(HeroContract.COL_SHIELD, 999);
		valuesHero.put(HeroContract.COL_WEAPON, 999);
		valuesHero.put(HeroContract.COL_POTION, 0);
		
		db.insert(HeroContract.TABLE, null, valuesHero);
		
		//Insertion BDD
		db.insert(HeroContract.TABLE, null, valuesHero);

		
		
		
		/******** MONSTRES ************/
		ContentValues valuesMonster1 = new ContentValues();
		//Ajout du montre lvl 1 - Larbin
		valuesMonster1.put(MonsterContract.COL_RANK, 1);
		valuesMonster1.put(MonsterContract.COL_HEALTH, 20);
		valuesMonster1.put(MonsterContract.COL_ATTACK, 5);
		valuesMonster1.put(MonsterContract.COL_ARMOR, 0);
		//Insertion BDD
		db.insert(MonsterContract.TABLE, null, valuesMonster1);		

		
		ContentValues valuesMonster2 = new ContentValues();
		//Ajout du monstre lvl 2 - Soldat
		valuesMonster2.put(MonsterContract.COL_RANK, 2);
		valuesMonster2.put(MonsterContract.COL_HEALTH, 45);
		valuesMonster2.put(MonsterContract.COL_ATTACK, 15);
		valuesMonster2.put(MonsterContract.COL_ARMOR, 10);
		//Insertion BDD
		db.insert(MonsterContract.TABLE, null, valuesMonster2);
		
		
		ContentValues valuesMonster3 = new ContentValues();
		// Ajout Monstre lvl 3 - Captain
		valuesMonster3.put(MonsterContract.COL_RANK, 3);
		valuesMonster3.put(MonsterContract.COL_HEALTH, 80);
		valuesMonster3.put(MonsterContract.COL_ATTACK, 50);
		valuesMonster3.put(MonsterContract.COL_ARMOR, 40);

		db.insert(MonsterContract.TABLE, null, valuesMonster3);
		

		ContentValues valuesMonster4 = new ContentValues();
		// Ajout Monstre lvl 4
		valuesMonster4.put(MonsterContract.COL_RANK, 4);
		valuesMonster4.put(MonsterContract.COL_HEALTH, 190);
		valuesMonster4.put(MonsterContract.COL_ATTACK, 80);
		valuesMonster4.put(MonsterContract.COL_ARMOR, 80);

		db.insert(MonsterContract.TABLE, null, valuesMonster4);
		
		
		
		
		/************ CASQUES *************/
		ContentValues valuesHelmet1 = new ContentValues();
		// Ajout Helmet lvl 1
		valuesHelmet1.put(HelmetContract.COL_HEALTHVALUE, 10);
		valuesHelmet1.put(HelmetContract.COL_ATTACKVALUE, 0);
		valuesHelmet1.put(HelmetContract.COL_ARMORVALUE, 0);
		valuesHelmet1.put(HelmetContract.COL_PRICE, 50);
		valuesHelmet1.put(HelmetContract.COL_ISBUY, 0);
		valuesHelmet1.put(HelmetContract.COL_ISEQUIP, 0);
		
		db.insert(HelmetContract.TABLE, null, valuesHelmet1);
		
		
		ContentValues valuesHelmet2 = new ContentValues();
		// Ajout Helmet lvl 2
		valuesHelmet2.put(HelmetContract.COL_HEALTHVALUE, 15);
		valuesHelmet2.put(HelmetContract.COL_ATTACKVALUE, 0);
		valuesHelmet2.put(HelmetContract.COL_ARMORVALUE, 5);
		valuesHelmet2.put(HelmetContract.COL_PRICE, 120);
		valuesHelmet2.put(HelmetContract.COL_ISBUY, 0);
		valuesHelmet2.put(HelmetContract.COL_ISEQUIP, 0);
		
		db.insert(HelmetContract.TABLE, null, valuesHelmet2);
		
		
		ContentValues valuesHelmet3 = new ContentValues();
		// Ajout Helmet lvl 3
		valuesHelmet3.put(HelmetContract.COL_HEALTHVALUE, 30);
		valuesHelmet3.put(HelmetContract.COL_ATTACKVALUE, 0);
		valuesHelmet3.put(HelmetContract.COL_ARMORVALUE, 15);
		valuesHelmet3.put(HelmetContract.COL_PRICE, 220);
		valuesHelmet3.put(HelmetContract.COL_ISBUY, 0);
		valuesHelmet3.put(HelmetContract.COL_ISEQUIP, 0);
		
		db.insert(HelmetContract.TABLE, null, valuesHelmet3);
		
		
		ContentValues valuesHelmet4 = new ContentValues();
		// Ajout Helmet lvl 4
		valuesHelmet4.put(HelmetContract.COL_HEALTHVALUE, 45);
		valuesHelmet4.put(HelmetContract.COL_ATTACKVALUE, 0);
		valuesHelmet4.put(HelmetContract.COL_ARMORVALUE, 20);
		valuesHelmet4.put(HelmetContract.COL_PRICE, 450);
		valuesHelmet4.put(HelmetContract.COL_ISBUY, 0);
		valuesHelmet4.put(HelmetContract.COL_ISEQUIP, 0);
		
		db.insert(HelmetContract.TABLE, null, valuesHelmet4);

		
		
		
		/*********** BOUCLIER ***************/
		ContentValues valuesShield1 = new ContentValues();
		// Ajout shields lvl 1
		valuesShield1.put(ShieldContract.COL_HEALTHVALUE, 0);
		valuesShield1.put(ShieldContract.COL_ATTACKVALUE, 0);
		valuesShield1.put(ShieldContract.COL_ARMORVALUE, 10);
		valuesShield1.put(ShieldContract.COL_PRICE, 50);
		valuesShield1.put(ShieldContract.COL_ISBUY, 0);
		valuesShield1.put(ShieldContract.COL_ISEQUIP, 0);
		
		db.insert(ShieldContract.TABLE, null, valuesShield1);
		
		
		ContentValues valuesShield2 = new ContentValues();
		// Ajout shields lvl 2
		valuesShield2.put(ShieldContract.COL_HEALTHVALUE, 0);
		valuesShield2.put(ShieldContract.COL_ATTACKVALUE, 5);
		valuesShield2.put(ShieldContract.COL_ARMORVALUE, 15);
		valuesShield2.put(ShieldContract.COL_PRICE, 120);
		valuesShield2.put(ShieldContract.COL_ISBUY, 0);
		valuesShield2.put(ShieldContract.COL_ISEQUIP, 0);
		
		db.insert(ShieldContract.TABLE, null, valuesShield2);
		
		
		ContentValues valuesShield3 = new ContentValues();
		// Ajout shields lvl 3
		valuesShield3.put(ShieldContract.COL_HEALTHVALUE, 0);
		valuesShield3.put(ShieldContract.COL_ATTACKVALUE, 15);
		valuesShield3.put(ShieldContract.COL_ARMORVALUE, 30);
		valuesShield3.put(ShieldContract.COL_PRICE, 220);
		valuesShield3.put(ShieldContract.COL_ISBUY, 0);
		valuesShield3.put(ShieldContract.COL_ISEQUIP, 0);
		
		db.insert(ShieldContract.TABLE, null, valuesShield3);
		
		
		ContentValues valuesShield4 = new ContentValues();
		// Ajout shields lvl 4
		valuesShield4.put(ShieldContract.COL_HEALTHVALUE, 0);
		valuesShield4.put(ShieldContract.COL_ATTACKVALUE, 20);
		valuesShield4.put(ShieldContract.COL_ARMORVALUE, 45);
		valuesShield4.put(ShieldContract.COL_PRICE, 450);
		valuesShield4.put(ShieldContract.COL_ISBUY, 0);
		valuesShield4.put(ShieldContract.COL_ISEQUIP, 0);
		
		db.insert(ShieldContract.TABLE, null, valuesShield4);
		
		
		
		/*********** ARMES ************/
		ContentValues valuesWeapon1 = new ContentValues();
		// Ajout weapon lvl 1
		valuesWeapon1.put(WeaponContract.COL_HEALTHVALUE, 0);
		valuesWeapon1.put(WeaponContract.COL_ATTACKVALUE, 10);
		valuesWeapon1.put(WeaponContract.COL_ARMORVALUE, 0);
		valuesWeapon1.put(WeaponContract.COL_PRICE, 50);
		valuesWeapon1.put(WeaponContract.COL_ISBUY, 0);
		valuesWeapon1.put(WeaponContract.COL_ISEQUIP, 0);
		
		db.insert(WeaponContract.TABLE, null, valuesWeapon1);
		
		
		ContentValues valuesWeapon2 = new ContentValues();
		// Ajout weapon lvl 2
		valuesWeapon2.put(WeaponContract.COL_HEALTHVALUE, 0);
		valuesWeapon2.put(WeaponContract.COL_ATTACKVALUE, 30);
		valuesWeapon2.put(WeaponContract.COL_ARMORVALUE, 0);
		valuesWeapon2.put(WeaponContract.COL_PRICE, 120);
		valuesWeapon2.put(WeaponContract.COL_ISBUY, 0);
		valuesWeapon2.put(WeaponContract.COL_ISEQUIP, 0);
		
		db.insert(WeaponContract.TABLE, null, valuesWeapon2);
		
		
		ContentValues valuesWeapon3 = new ContentValues();
		// Ajout weapon lvl 3
		valuesWeapon3.put(WeaponContract.COL_HEALTHVALUE, 0);
		valuesWeapon3.put(WeaponContract.COL_ATTACKVALUE, 45);
		valuesWeapon3.put(WeaponContract.COL_ARMORVALUE, 0);
		valuesWeapon3.put(WeaponContract.COL_PRICE, 220);
		valuesWeapon3.put(WeaponContract.COL_ISBUY, 0);
		valuesWeapon3.put(WeaponContract.COL_ISEQUIP, 0);
		
		db.insert(WeaponContract.TABLE, null, valuesWeapon3);
		
		
		ContentValues valuesWeapon4 = new ContentValues();
		// Ajout weapon lvl 4
		valuesWeapon4.put(WeaponContract.COL_HEALTHVALUE, 0);
		valuesWeapon4.put(WeaponContract.COL_ATTACKVALUE, 60);
		valuesWeapon4.put(WeaponContract.COL_ARMORVALUE, 0);
		valuesWeapon4.put(WeaponContract.COL_PRICE, 450);
		valuesWeapon4.put(WeaponContract.COL_ISBUY, 0);
		valuesWeapon4.put(WeaponContract.COL_ISEQUIP, 0);
		
		db.insert(WeaponContract.TABLE, null, valuesWeapon4);
	}
}
