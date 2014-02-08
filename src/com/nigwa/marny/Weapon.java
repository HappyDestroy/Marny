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
	
	
	public Weapon(int id, int healthValue, int attackValue, int armorValue, int price) {
		this.id = id;
		this.healthValue = healthValue;
		this.attackValue = attackValue;
		this.armorValue = armorValue;
		this.price = price;
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


	@Override
	public String toString() {
		return "C'est une arme qui apporte " 
				+ this.armorValue + " point(s) d'armure, "
				+ this.healthValue + " point(s) de vie, "
				+ this.attackValue + " point(s) d'attaque.";
	}
}
