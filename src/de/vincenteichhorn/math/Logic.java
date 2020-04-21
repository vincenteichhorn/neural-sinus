package de.vincenteichhorn.math;

public class Logic {
	
	public static int xor(int x, int y) {
		if(x == 0 && y == 0) {
			return 0;
		} else if(x == 0 && y == 1) {
			return 1;
		} else if(x == 1 && y == 0){
			return 1;
		} else {
			return 0;
		}
	}
	
	
	public static int xor(double[] data) {
		if(data[0] == 0 && data[1] == 0) {
			return 0;
		} else if(data[0] == 0 && data[1] == 1) {
			return 1;
		} else if(data[0] == 1 && data[1] == 0){
			return 1;
		} else {
			return 0;
		}
	}

}
