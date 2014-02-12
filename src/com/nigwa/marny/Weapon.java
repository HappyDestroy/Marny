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
	 * Sériel ID
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int healthValue;
	private int attackValue;
	private int armorValue;
	private int price;
	private int isBuy;
	private int isEquip;
	
	/**
	 * Constructeur de l'armes
	 * @param id - Son ID
	 * @param healthValue - Sa valeur de HP
	 * @param attackValue - Sa valeur d'attaque
	 * @param armorValue - Sa valeur d'armure
	 * @param price - Son prix
	 * @param isBuy - Si il est acheté
	 * @param isEquip - Si il est équipé
	 */
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
	
	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the healthValue
	 */
	public int getHealthValue() {
		return healthValue;
	}

	/**
	 * @param healthValue the healthValue to set
	 */
	public void setHealthValue(int healthValue) {
		this.healthValue = healthValue;
	}

	/**
	 * @return the attackValue
	 */
	public int getAttackValue() {
		return attackValue;
	}

	/**
	 * @param attackValue the attackValue to set
	 */
	public void setAttackValue(int attackValue) {
		this.attackValue = attackValue;
	}

	/**
	 * @return the armorValue
	 */
	public int getArmorValue() {
		return armorValue;
	}

	/**
	 * @param armorValue the armorValue to set
	 */
	public void setArmorValue(int armorValue) {
		this.armorValue = armorValue;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the isBuy
	 */
	public int getIsBuy() {
		return isBuy;
	}

	/**
	 * @param isBuy the isBuy to set
	 */
	public void setIsBuy(int isBuy) {
		this.isBuy = isBuy;
	}

	/**
	 * @return the isEquip
	 */
	public int getIsEquip() {
		return isEquip;
	}

	/**
	 * @param isEquip the isEquip to set
	 */
	public void setIsEquip(int isEquip) {
		this.isEquip = isEquip;
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
		return "+ " + this.healthValue + " point(s) de vie \n "
				+ "+ " + this.attackValue + " point(s) d'attaque \n"
				+ "+ " + this.armorValue + " point(s) d'armure.\n";
	}
}
