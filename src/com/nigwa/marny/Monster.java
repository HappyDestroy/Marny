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
	
	public Monster(int rank, int health, int attack, int shield) {
		this.rank = rank;
		this.health = health;
		this.attack = attack;
		this.shield = shield;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	@Override
	public String toString() {
		return "Un monstre de rang " + this.rank + " avec " + this.health + " points de vie apparait !";
	}
	
	
	
}
