package com.nigwa.marny;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class MonsterActivity extends SherlockActivity {

	private Hero myHero;
	private Monster myMonster;
	private SQLiteDatabase db;
	private SQLiteOpenHelperClass dbHelper;
	private int health_left;
	private int nb_room;
	private MediaPlayer soudHurt = null;
	private MediaPlayer soudDeath = null;
	private MediaPlayer soudFail = null;
	private MediaPlayer soudCritic = null;
	private int valeurProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monster);
		
		//bouton pour changer de pièce
		final Button btn_next = (Button) findViewById(
				R.id.monster_btn_next);
		
		//Bouton pour attaquer
		final Button btn_attack = (Button) findViewById(
				R.id.monster_btn_attack);
		
		//ProgressBar de la vie du monstre
		final ProgressBar monster_health = (ProgressBar) findViewById(
				R.id.progressBar_monsterhealth);
		
		//ProgressBar de la vie du héro
		final ProgressBar hero_health = (ProgressBar) findViewById(
				R.id.progressBar_herohealth);
		
		//TextViex des infos
		final TextView label_monster = (TextView) findViewById(
				R.id.monster_label_monster);
		
		//ImageView du monstre
		final ImageView img_monster = (ImageView) findViewById(
				R.id.monster_imgview);
		
		final ImageView img_potion = (ImageView) findViewById(
				R.id.img_potion);

		//On récupère le héro passé depuis le bundle
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		health_left = getIntent().getIntExtra("health_left", 1); 
		
		nb_room = getIntent().getIntExtra("nb_room", 1);
		nb_room++;
		
		hero_health.setMax(myHero.getHealth() 
				+ myHero.getHelmet().getHealthValue()
				+ myHero.getShield().getHealthValue()
				+ myHero.getWeapon().getHealthValue());
		
		hero_health.setProgress(health_left);

		this.createMonster();
		//On affiche l'image qui correspond au rang du monstre
		switch(myMonster.getRank()) {
		case 1:
			img_monster.setImageResource(R.drawable.sprite_larbin);
			break;
		case 2 :
			img_monster.setImageResource(R.drawable.sprite_soldat);
			break;
		case 3 :
			img_monster.setImageResource(R.drawable.sprite_capitaine);
			break;
		case 4 :
			img_monster.setImageResource(R.drawable.sprite_general);
			break;
		}
		
		//On met le max pour la progressBar de vie du monstre
		monster_health.setMax(myMonster.getHealth());
		monster_health.setProgress(myMonster.getHealth());
		
		
		//Si le monstre est vivant à la création (Et il l'est !) on desactive 
		//le bouton pour avancer
		if(monster_health.getProgress() > 0) {
			btn_next.setEnabled(false);
		}
		
		hero_health.getProgress();
		//Listener du click sur l'image de la potion
		img_potion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(myHero.getPotion() > 0) {
					if(hero_health.getProgress() == hero_health.getMax()) {
						Toast.makeText(MonsterActivity.this, 
								"Ta vie est déjà au maximum", 
								Toast.LENGTH_SHORT).show();
					} else {
						
						//Il prend une potion
						 if ((hero_health.getMax() - health_left)
								 < 25) {
								health_left = myHero.getHealth() 
										+ myHero.getHelmet().getHealthValue()
										+ myHero.getShield().getHealthValue()
										+ myHero.getWeapon().getHealthValue();
								
								hero_health.setProgress(hero_health.getMax());
								
							 valeurProgress = hero_health.getMax() - health_left;
							 hero_health.setProgress(hero_health.getMax());
							 /*Toast.makeText(MonsterActivity.this, 
									 health_left+"--"+String.valueOf(hero_health.getProgress())
									 +"--"+String.valueOf(hero_health.getMax()+"--"+valeurProgress), 
										Toast.LENGTH_SHORT).show();*/
						 } else {

							health_left += 25;
							 /*Toast.makeText(MonsterActivity.this, 
									 "vie restante : "+health_left, 
										Toast.LENGTH_SHORT).show();*/
							hero_health.setProgress(
									hero_health.getProgress() + 25);
						 }

						myHero.setPotion(myHero.getPotion() - 1);
						label_monster.setText("Tu as pris une potion (+25 HP)");
						 
						//Le monstre attaque
						final int valueDefendHero = (myHero.getArmor() 
								+ myHero.getHelmet().getArmorValue() 
								+ myHero.getShield().getArmorValue() 
								+ myHero.getWeapon().getArmorValue());
						 
						final int health_lose;
						
						if(myMonster.getAttack() - valueDefendHero <= 0) {
							health_lose = 0;
						} else {
							health_lose = myMonster.getAttack() 
									- valueDefendHero;
						}
						
						//On réduit la progressBar de vie du héro
						hero_health.setProgress(hero_health.getProgress() 
								- health_lose);
						
						
						//On change la valeur des points de vie qu'il nous reste
						health_left = hero_health.getProgress();
						
						
						soudHurt = MediaPlayer.create(MonsterActivity.this,
									 R.raw.coup);
						soudHurt.start();
						
						
						//Le monstre nous enlève de la vie
						label_monster.postDelayed(new Runnable() {
							@Override
							public void run() {
								label_monster.setText("Il fait " + health_lose 
										+ " point de dégâts.");
							}
						}, 1000);
						
						//Si le héro n'a plus de vie
						 if(health_left <= 0) { 
							 
							 soudDeath = MediaPlayer.create(
									 MonsterActivity.this, R.raw.death);
							 soudDeath.start();
							 
							 
							 heroKO();
						 }
					}
				} else {
					Toast.makeText(MonsterActivity.this, 
							"Malheuresement,  tu n'as pas de potions", 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		
		//Listener sur le click du bouton pour changer de salle
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Choix aléatoire du type de la prochaine pièce
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 14) {
					Intent intentMonsterRoom = new Intent(MonsterActivity.this,
							MonsterActivity.class);
					
					intentMonsterRoom.putExtra("hero", myHero);
					intentMonsterRoom.putExtra("nb_room", nb_room);
					intentMonsterRoom.putExtra("health_left", health_left);
					
					startActivity(intentMonsterRoom);
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					Intent intentGoldRoom = new Intent(MonsterActivity.this,
							GoldRoomActivity.class);
					
					intentGoldRoom.putExtra("hero", myHero);
					intentGoldRoom.putExtra("nb_room", nb_room);
					intentGoldRoom.putExtra("health_left", health_left);
					
					startActivity(intentGoldRoom);
				} else {
					Intent intentRestRoom = new Intent(MonsterActivity.this,
							RestRoomActivity.class);
					
					intentRestRoom.putExtra("hero", myHero);
					intentRestRoom.putExtra("nb_room", nb_room);
					intentRestRoom.putExtra("health_left", health_left);
					
					startActivity(intentRestRoom);
				}
			}
		});
		
		//Listener sur le click du bouton pour attaquer
		btn_attack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				//On calcul les dégats que peux faire le héro face au bouclier 
				//du monstre
				int valueAttack = (myHero.getAttack() 
						+ myHero.getWeapon().getAttackValue() 
						+ myHero.getShield().getAttackValue())
						- myMonster.getShield();
				if(valueAttack < 0) {
					valueAttack = 0;
				}
				
				int randomHurt = Tools.random(10);
				//FAIL, SUCCES, CRITIC
				if (randomHurt == 0) {//FAIL
					valueAttack = 0;
					label_monster.setText("Le monstre a esquivé "
							+ "\n À son tour ...");
					soudFail = MediaPlayer.create(MonsterActivity.this,
							 R.raw.missed);
					soudFail.start();
				} else if (randomHurt >= 9){//CRITIC
					valueAttack = valueAttack * 2;
					label_monster.setText("Coûp critique de " + valueAttack + 
							" points de dégats \n Au tour du monstre ...");
					soudCritic = MediaPlayer.create(MonsterActivity.this,
							 R.raw.critic);
					soudCritic.start();
				}else {//NORMAL
					label_monster.setText("Votre attaque à fait " + valueAttack + 
							" points de dégats \n Au tour du monstre ...");
					
				}
				//On enlève de la vie au monstre (On attaque toujours en 1er)
				//(Mais c'est parce qu'on est trop fort!)
				monster_health.setProgress(
						monster_health.getProgress() - valueAttack);
				

				
				//On vérifie qu'il reste de la vie au monstre, et il attaque
				if(monster_health.getProgress() > 0) {
					
					final int valueDefendHero = (myHero.getArmor() 
							+ myHero.getHelmet().getArmorValue() 
							+ myHero.getShield().getArmorValue() 
							+ myHero.getWeapon().getArmorValue());
					 
					final int health_lose;
					
					if(myMonster.getAttack() - valueDefendHero <= 0) {
						health_lose = 0;
					} else {
						health_lose = myMonster.getAttack() - valueDefendHero;
					}
					
					//On réduit la progressBar de vie du héro
					hero_health.setProgress(hero_health.getProgress() 
							- health_lose);
					
					
					//On change la valeur des points de vie qu'il nous reste
					health_left = hero_health.getProgress();
					
					
					soudHurt = MediaPlayer.create(MonsterActivity.this,
								 R.raw.coup);
					soudHurt.start();
					
					
					//Le monstre nous enlève de la vie
					label_monster.postDelayed(new Runnable() {
						@Override
						public void run() {
							label_monster.setText("Il fait " + health_lose 
									+ " point de dégâts.");
						}
					}, 1000);
					
					//Si le héro n'a plus de vie
					 if(health_left <= 0) { 
						 
						 soudDeath = MediaPlayer.create(MonsterActivity.this,
								 R.raw.death);
						 soudDeath.start();
						 
						 
						 heroKO();
					 }
					 
				} else { //Sinon le monstre est K.O.
					
					label_monster.postDelayed(new Runnable() {
						@Override
						public void run() {
							label_monster.setText(getApplication()
									.getString(R.string.monster_ko));
							}
					}, 600);
					
					
					//On active les boutons et on cache le sprite du monstre
					btn_next.setEnabled(true);
					btn_attack.setEnabled(false);
					img_monster.setVisibility(View.INVISIBLE);
				}
			}
		});
		if(health_left <= 0) { 
			 soudDeath = MediaPlayer.create(MonsterActivity.this,
					 R.raw.death);
			 soudDeath.start();
			 heroKO();
		 }
	}
	
	
	private void createMonster() {
		//On tire un nombre entre 1 et 4 pour choisir le rang du monstre.
		int random = 1;
		if(nb_room < 10) {
			random = 1;
		} else if (nb_room >= 10 && nb_room < 17)  {
			random = Tools.random(1, 2);
		} else if (nb_room >= 17 && nb_room < 23) {
			random = Tools.random(1, 3);
		} else if (nb_room >= 23) {
			random = Tools.random(2, 4);
		}
		
		String[] VALUES = { String.valueOf(random) };
		
		dbHelper = new SQLiteOpenHelperClass(
				this, 
				"myDB", 
				null, 
				1);

		db = dbHelper.getWritableDatabase();
		
		
		Cursor c = db.query(MonsterContract.TABLE , MonsterContract.COLS, 
				"id LIKE ?" ,VALUES, null, null, null);
		
		c.moveToFirst();
		
		do {
			int valueRank = c.getInt(
					c.getColumnIndex(MonsterContract.COL_RANK));
			
			int valueHealth = c.getInt(
					c.getColumnIndex(MonsterContract.COL_HEALTH));
			
			int valueAttack = c.getInt(
					c.getColumnIndex(MonsterContract.COL_ATTACK));
			
			int valueShielsd = c.getInt(
					c.getColumnIndex(MonsterContract.COL_ARMOR));
			
			
			myMonster = new Monster(valueRank, valueHealth, valueAttack, 
					valueShielsd);
			
		} while ( c.moveToNext() );
	}
	
	private void heroKO() {
		
		
		
		new AlertDialog.Builder(MonsterActivity.this)
	    .setTitle(getApplication().getString(R.string.heroKO_title))
	    .setMessage(getApplication().getString(R.string.heroKO_msg))
	    .setPositiveButton(R.string.heroKO_valid, 
	    		new DialogInterface.OnClickListener() {
			        public void onClick(
			        		DialogInterface dialog, int which) {
			        		//On arrête l'activity pour quitter
			        		
				        	//Sauvegarde en BDD
							ContentValues itemHero = new ContentValues();
							itemHero.put("gold", myHero.getGold());
							itemHero.put("potion", myHero.getPotion());
	
							String whereClause = "id =? ";
							String[] whereArgs = { "1" };
							db.update(HeroContract.TABLE, itemHero, 
									whereClause, whereArgs);
			        	
			        		MonsterActivity.this.finish();
			        }
	     })
	    .setIcon(R.drawable.ic_info_small)
	    .setCancelable(false)
	    .show();
		
	}
	

	//Empêcher l'utilisation du bouton retour
	@Override
	public void onBackPressed() {
		Toast.makeText(this, getApplication().getString(R.string.btn_back), 
				Toast.LENGTH_LONG).show();
		return;
	}

}
