package com.nigwa.marny;

public class Helmet {

	private int id;
	private int healthValue;
	private int attackValue;
	private int armorValue;
	private int price;
	
	
	public Helmet(int id, int healthValue, int attackValue, int armorValue, int price) {
		this.setId(id);
		this.healthValue = healthValue;
		this.attackValue = attackValue;
		this.armorValue = armorValue;
		this.setPrice(price);
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
		return "C'est un casque qui apporte " 
				+ this.armorValue + " point(s) d'armure, "
				+ this.healthValue + " point(s) de vie, "
				+ this.attackValue + " point(s) d'attaque, ";
	}
}
