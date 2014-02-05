package com.nigwa.marny;

public class Shield {

	private int id;
	private int healthValue;
	private int attackValue;
	private int armorValue;
	
	
	public Shield(int id, int healthValue, int attackValue, int armorValue) {
		this.setId(id);
		this.healthValue = healthValue;
		this.attackValue = attackValue;
		this.armorValue = armorValue;
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

	
	@Override
	public String toString() {
		return "C'est un bouclier qui apporte " 
				+ this.armorValue + " point(s) d'armure, "
				+ this.healthValue + " point(s) de vie, "
				+ this.attackValue + " point(s) d'attaque, ";
	}
}
