package de.vincenteichhorn.math;

import java.util.Random;

public class Rand {
	
	public static int random(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public static double random(double min, double max) {
	    Random rand = new Random();
	    double randomNum = min + (max - min) * rand.nextDouble();
	    return randomNum;
	}
	
	public static int randomBit() {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((1 - 0) + 1);
	    return randomNum;
	}

}
