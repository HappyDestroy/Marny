/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 ****MonsterActivity****
 * Cette activity permet d'entrer dans la pièce "MonsterRoom"
 *  Elle gére les combats en fonction des caractèristiques du héro
 *  et de son équipement.
 *  
 *  Permet de gérer les monstres à combattre
 *  Permet d'utiliser les potions de vie.
 *  Permet de gérer les coups du héro (echec, réussite, critique)
 *  
 *  Permet de faire revenir le héro à l'entrée du chateau s'il est mort
 *  Permet de faire mourir le héro si lui et le monstre ne font pas de dégat
 *  Permet de passer à une autre pièce
 * 
 */
package com.nigwa.marny;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MonsterRoomActivity extends SherlockActivity {

	private Hero myHero;
	private Monster myMonster;
	private int health_left;
	private int nb_room;
	private int random_gold;
	private MediaPlayer soudHurt = null;
	private MediaPlayer soudDeath = null;
	private MediaPlayer soudFail = null;
	private MediaPlayer soudCritic = null;
	private int noDammageM; //Variable si aucun dommage du monstre
	private int noDammageH; //Variable si aucun dommage du hero
	
	
	/**
	 * Methode à la création de l'activity
	 */
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
		//initialisation de la variable noDammage
		noDammageM = 0;
		noDammageH = 0;
		hero_health.getProgress();
		//Listener du click sur l'image de la potion
		img_potion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(myHero.getPotion() > 0) {
					if(hero_health.getProgress() == hero_health.getMax()) {
						Toast.makeText(MonsterRoomActivity.this, 
								"Ta vie est déjà au maximum", 
								Toast.LENGTH_SHORT).show();
					} else {
						//Il prend une potion
						 if ((hero_health.getMax() - health_left) < 50) {
								health_left = hero_health.getMax();
								
								hero_health.setProgress(health_left);
						 } else {
							health_left += 50;
							hero_health.setProgress(health_left);
						 }

						myHero.setPotion(myHero.getPotion() - 1);
						label_monster.setText("Tu as pris une potion (+50 HP) "
								+ "\n Au tour du monstre ...");
						 
						//Le monstre attaque
						final int valueDefendHero = (myHero.getArmor() 
								+ myHero.getHelmet().getArmorValue() 
								+ myHero.getShield().getArmorValue() 
								+ myHero.getWeapon().getArmorValue());
						 
						final int health_lose;
						
						if(myMonster.getAttack() - valueDefendHero <= 0) {
							health_lose = 0;
							noDammageM = 1;
						} else {
							health_lose = myMonster.getAttack() 
									- valueDefendHero;
						}
						
						//On réduit la progressBar de vie du héro
						hero_health.setProgress(hero_health.getProgress() 
								- health_lose);
						
						
						//On change la valeur des points de vie qu'il nous reste
						health_left = hero_health.getProgress();
						
						soudHurt = MediaPlayer.create(MonsterRoomActivity.this,
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
									 MonsterRoomActivity.this, R.raw.death);
							 soudDeath.start();
							 
							 heroKO();
						 }
					}
				} else {
					Toast.makeText(MonsterRoomActivity.this, 
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
					Intent intentMonsterRoom = new Intent(MonsterRoomActivity.this,
							MonsterRoomActivity.class);
					
					intentMonsterRoom.putExtra("hero", myHero);
					intentMonsterRoom.putExtra("nb_room", nb_room);
					intentMonsterRoom.putExtra("health_left", health_left);
					
					startActivity(intentMonsterRoom);
				} else if(myRandom >= 15 && myRandom <= 18 ) {
					Intent intentGoldRoom = new Intent(MonsterRoomActivity.this,
							GoldRoomActivity.class);
					
					intentGoldRoom.putExtra("hero", myHero);
					intentGoldRoom.putExtra("nb_room", nb_room);
					intentGoldRoom.putExtra("health_left", health_left);
					
					startActivity(intentGoldRoom);
				} else {
					Intent intentRestRoom = new Intent(MonsterRoomActivity.this,
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
					noDammageH = 1;
				}
				
				int randomHurt = Tools.random(10);
				//coup critique / echec
				if (randomHurt == 0) {//FAIL
					valueAttack = 0;
					label_monster.setText("Le monstre a esquivé "
							+ "\n À son tour ...");
					soudFail = MediaPlayer.create(MonsterRoomActivity.this,
							 R.raw.missed);
					soudFail.start();
				} else if (randomHurt >= 9){//CRITIC
					valueAttack = valueAttack * 2;
					label_monster.setText("Coup critique de " + valueAttack + 
							" points de dégats \n Au tour du monstre ...");
					
					soudCritic = MediaPlayer.create(MonsterRoomActivity.this,
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
				
				if(noDammageM == 1 && noDammageH == 1)
				{
					heroKO();
				}
				//On vérifie qu'il reste de la vie au monstre, et il attaque
				if(monster_health.getProgress() > 0) {
					
					final int valueDefendHero = (myHero.getArmor() 
							+ myHero.getHelmet().getArmorValue() 
							+ myHero.getShield().getArmorValue() 
							+ myHero.getWeapon().getArmorValue());
					 
					final int health_lose;
					
					if(myMonster.getAttack() - valueDefendHero <= 0) {
						health_lose = 0;
						noDammageM = 1;
					} else {
						health_lose = myMonster.getAttack() - valueDefendHero;
					}
					
					//On réduit la progressBar de vie du héro
					hero_health.setProgress(hero_health.getProgress() 
							- health_lose);
					
					
					//On change la valeur des points de vie qu'il nous reste
					health_left = hero_health.getProgress();
					
					
					soudHurt = MediaPlayer.create(MonsterRoomActivity.this,
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
						 
						 soudDeath = MediaPlayer.create(MonsterRoomActivity.this,
								 R.raw.death);
						 soudDeath.start();
						 
						 
						 heroKO();
					 }
					 
				} else { //Sinon le monstre est K.O.
					
					label_monster.postDelayed(new Runnable() {
						@Override
						public void run() {
							label_monster.setText(getApplication()
									.getString(R.string.monster_ko) 
									+ "\n Tu as gagné " + random_gold 
									+ " golds !" );
							}
					}, 600);
					
					
					//On active les boutons et on cache le sprite du monstre
					btn_next.setEnabled(true);
					btn_attack.setEnabled(false);
					img_potion.setEnabled(false);
					img_potion.setVisibility(View.INVISIBLE);
					img_monster.setVisibility(View.INVISIBLE);
					myHero.setGold(myHero.getGold() + random_gold);
				}
			}
		});
		if(health_left <= 0) { 
			 soudDeath = MediaPlayer.create(MonsterRoomActivity.this,
					 R.raw.death);
			 soudDeath.start();
			 heroKO();
		 }
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
		String monsterName;
		if(myMonster.getRank() == 1) {
			monsterName = "Larbin";
		} else if(myMonster.getRank() == 2) {
			monsterName = "Soldat";
			
		}else if(myMonster.getRank() == 3) {
			monsterName = "Capitaine";
		}else {
			monsterName = "Général";
		}
		new AlertDialog.Builder(this)
	    .setTitle(R.string.menu_info)
	    .setMessage("Tu affrontes un "+monsterName+"\n"
	    		+ getApplication().getString(R.string.health)+ " "
	    		+ myMonster.getHealth() + "\n" 
	    		+ getApplication().getString(R.string.attack) + " " 
	    		+ myMonster.getAttack() + "\n" 
	    		+ getApplication().getString(R.string.armor) + " " 
	    		+ myMonster.getShield())
	    .setPositiveButton(android.R.string.ok,
	    		new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // Fermeture de la fenêtre
	        }
	     })
	    .setIcon(R.drawable.ic_info_small)
	     .show();
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Genère un monstre
	 */
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
		
		switch(random) {
		case 1 :
			random_gold = Tools.random(1, 5);
			break;
		case 2 :
			random_gold = Tools.random(5, 15);
			break;
		case 3 :
			random_gold = Tools.random(10, 20);
			break;
		case 4 :
			random_gold = 100;
		}
		
		String[] whereArgs = { String.valueOf(random) };
		
		myMonster = Tools.getMonsterFromBDD(getApplicationContext(), 
				"id = ?", whereArgs).get(0);
		
	}
	
	
	/**
	 * Gère la mort du héro
	 */
	private void heroKO() {
		new AlertDialog.Builder(MonsterRoomActivity.this)
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
							
							Tools.updateBDD(getApplicationContext(), 
									HeroContract.TABLE, 
									itemHero, 
									whereClause, 
									whereArgs);
			        	
			        		MonsterRoomActivity.this.finish();
			        }
	     })
	    .setIcon(R.drawable.ic_info_small)
	    .setCancelable(false)
	    .show();
		
	}
	

	/**
	 * Lors du click sur le bouton Back (Pour le desactiver)
	 */
	@Override
	public void onBackPressed() {
		Toast.makeText(this, getApplication().getString(R.string.btn_back), 
				Toast.LENGTH_LONG).show();
		return;
	}

}
