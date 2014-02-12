/*
 * Réalisé par Martin Cesbron et Nicolas Saboureau à l'IIA de Laval
 * 
 * Création des objets Armes
 * int id : Rang de l'arme (1-4)
 * int healthValue : Santé apportée par l'arme
 * int attackValue : Attack apportée par l'arme
 * int armorValue : Armure apportée par l'arme
 * int price : 		Prix de l'arme
 * int isBuy :		Indique si l'arme est acheté (1) ou non (0)
 * int isEquip :	Indique si l'arme est équipé (1) ou non (0)
 * 
 */
package com.nigwa.marny;

import java.io.Serializable;

public class Weapon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int healthValue;
	private int attackValue;
	private int armorValue;
	private int price;
	private int isBuy;
	private int isEquip;
	
	public Weapon(int id, int healthValue, int attackValue, int armorValue, 
			int price, int isBuy, int isEquip) {
		this.id = id;
		this.healthValue = healthValue;
		this.attackValue = attackValue;
		this.armorValue = armorValue;
		this.price = price;
		this.isBuy = isBuy;
		this.isEquip = isEquip;
	}
	
	
	public int getHealthValue() {
		return healthValue;
	}
	public void setHealthValue(int healthValue) {
		this.healthValue = healthValue;
	}
	public int getAttackValue() {
		return attackValue;
	}
	public void setAttackValue(int attackValue) {
		this.attackValue = attackValue;
	}
	public int getArmorValue() {
		return armorValue;
	}
	public void setArmorValue(int armorValue) {
		this.armorValue = armorValue;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}

	public int getIsBuy() {
		return isBuy;
	}


	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}

	public int getIsEquip() {
		return isEquip;
	}

	public void setIsEquip(int isEquip) {
		this.isEquip = isEquip;
	}


	@Override
	public String toString() {
		return "+ " + this.healthValue + " point(s) de vie \n "
				+ "+ " + this.attackValue + " point(s) d'attaque \n"
				+ "+ " + this.armorValue + " point(s) d'armure.\n";
	}
}
