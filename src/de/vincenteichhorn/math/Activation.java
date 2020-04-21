package de.vincenteichhorn.math;

public class Activation {

	public static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}
	
	public static double dsigmoid(double y) {
		return sigmoid(y) * (1 - sigmoid(y));
	}
}
