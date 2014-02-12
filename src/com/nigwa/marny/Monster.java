/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 * Création des Monstres
* int rank : 	Rang du Monstre
* int health : 	Santé du Monstre
* int attack : Attaque du Monstre
* int armor :  Armur du Monstre
*
 */
package com.nigwa.marny;

public class Monster {

	private int rank;
	private int health;
	private int attack;
	private int shield;
	
	/**
	 * Constructeur du monstre
	 * @param rank - Son rang
	 * @param health - Sa vie
	 * @param attack - Son attaque
	 * @param shield - Son armure
	 */
	public Monster(int rank, int health, int attack, int shield) {
		this.rank = rank;
		this.health = health;
		this.attack = attack;
		this.shield = shield;
	}

	

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
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
	 * @return the shield
	 */
	public int getShield() {
		return shield;
	}

	/**
	 * @param shield the shield to set
	 */
	public void setShield(int shield) {
		this.shield = shield;
	}

	
	
	/**
	 * Override de la methode toString
	 */
	@Override
	public String toString() {
		return "Un monstre de rang " + this.rank + " avec " + this.health + " points de vie apparait !";
	}
	
	
	
}
