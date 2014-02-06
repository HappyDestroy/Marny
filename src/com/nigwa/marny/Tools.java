package com.nigwa.marny;

import java.util.Random;

public class Tools {
	
	public static int random(int lenght) {
		Random r = new Random();
		return r.nextInt(lenght);
	}
}
