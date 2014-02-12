/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 * Création de l'objets Héro
* int health : 	Santé du Héro
* int attack : Attaque du Héro
* int armor :  Armur du Héro
* int gold :   Gold du Héro
* int potion : Nombre de potions du Héro
* Helmet helmet : Casque du Héro
* Shield shield : Bouclier du Héro
* Weapon weapon : Arme du Hero
 */
package com.nigwa.marny;

import java.io.Serializable;


public class Hero extends Object implements Serializable {

	/**
	 * Sériel ID
	 */
	private static final long serialVersionUID = 1L;
	private int health;
	private int attack;
	private int armor;
	private int gold;
	private int potion;
	private Helmet helmet;
	private Shield shield;
	private Weapon weapon;
	

	/**
	 * Constructeur du héro
	 * @param health - Sa vie de base
	 * @param attack - Son attaque de base
	 * @param armor - Son armure de base
	 * @param gold - Ses golds de base
	 * @param helmet - Son casque de base
	 * @param shield - Son bouclier de base
	 * @param weapon - Son arme de base
	 * @param potion - Ses potions de base
	 */
	public Hero(int health, int attack, int armor, int gold, Helmet helmet, Shield shield, Weapon weapon, int potion) {
		this.health = health;
		this.attack = attack;
		this.armor = armor;
		this.gold = gold;
		this.helmet = helmet;
		this.shield = shield;
		this.weapon = weapon;
		this.potion = potion;
	}


	
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return the armor
	 */
	public int getArmor() {
		return armor;
	}

	/**
	 * @param armor the armor to set
	 */
	public void setArmor(int armor) {
		this.armor = armor;
	}

	/**
	 * @return the gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * @param gold the gold to set
	 */
	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the potion
	 */
	public int getPotion() {
		return potion;
	}

	/**
	 * @param potion the potion to set
	 */
	public void setPotion(int potion) {
		this.potion = potion;
	}

	/**
	 * @return the helmet
	 */
	public Helmet getHelmet() {
		return helmet;
	}

	/**
	 * @param helmet the helmet to set
	 */
	public void setHelmet(Helmet helmet) {
		this.helmet = helmet;
	}

	/**
	 * @return the shield
	 */
	public Shield getShield() {
		return shield;
	}

	/**
	 * @param shield the shield to set
	 */
	public void setShield(Shield shield) {
		this.shield = shield;
	}

	/**
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * @param weapon the weapon to set
	 */
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Override de la methode toString
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Votre héro a " + this.health + " point(s) de vie, " + 
				this.attack + " points d'attaque, " + 
				this.armor + " points d'armure et " + 
				this.gold + " gold.";
	}
}
