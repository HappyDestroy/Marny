package com.nigwa.marny;

import java.util.Random;

public class Tools {
	
	/**
	 * Retourne un nombre aléatoire entre 0 et le nombre en paramètre
	 * @param length - La valuer max du nombre aléatoire
	 * @return int - Un nombre aléatoire
	 */
	public static int random(int length) {
		Random r = new Random();
		return r.nextInt(length);
	}
	
	/**
	 * Retourne un nombre aléatoire entre les 2 paramètres
	 * @param min - Le nombre minimum
	 * @param max - Le nombre maximum
	 * @return int - Un nombre aléatoire
	 */
	public static int random(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}
}
