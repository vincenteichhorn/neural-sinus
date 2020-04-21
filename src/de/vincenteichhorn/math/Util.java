package de.vincenteichhorn.math;

import java.io.File;

public class Util {
	public static int getMaxIndex(double[] arr) {
		int maxIndex = 0;
		double max = 1;
		for (int i = 0; i < arr.length; i++) {
		    if (arr[i] > max) {
		        max = arr[i];
		        maxIndex = i;
		    }
		}
		return maxIndex;
	}
	
	public static int getFileNumber(File dir) {

	    File[] files = dir.listFiles();
	    if (files != null) {
	        return files.length;
	    } else {
	    	return 0;
	    }
	}
	
	public static float map(float x, float in_min, float in_max, float out_min, float out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	public static double map(double x, double in_min, double in_max, double out_min, double out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	public static double round(double val, int digits) {
		return Math.round(val * Math.pow(10, digits)) / Math.pow(10, digits);
	}
}
