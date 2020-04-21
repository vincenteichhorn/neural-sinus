package de.vincenteichhorn.math;

public class Arrays {

	public static double[] push(double[] array, double push) {
		double[] longer = new double[array.length + 1];
		for (int i = 0; i < array.length; i++)
			longer[i] = array[i];
		longer[array.length] = push;
		return longer;
	}

	public static int[] push(int[] array, int push) {
		int[] longer = new int[array.length + 1];
		for (int i = 0; i < array.length; i++)
			longer[i] = array[i];
		longer[array.length] = push;
		return longer;
	}

	public static float[] push(float[] array, float push) {
		float[] longer = new float[array.length + 1];
		for (int i = 0; i < array.length; i++)
			longer[i] = array[i];
		longer[array.length] = push;
		return longer;
	}

	public static String[] push(String[] array, String push) {
		String[] longer = new String[array.length + 1];
		for (int i = 0; i < array.length; i++)
			longer[i] = array[i];
		longer[array.length] = push;
		return longer;
	}

	public static Boolean[] push(Boolean[] array, Boolean push) {
		Boolean[] longer = new Boolean[array.length + 1];
		for (int i = 0; i < array.length; i++)
			longer[i] = array[i];
		longer[array.length] = push;
		return longer;
	}

	public static void print(double[] arr) {
		String s = "[ ";
		for (int i = 0; i < arr.length; i++) {
			s += arr[i] + ", ";
		}
		s += "]";
		System.out.println(s);
	}

	public static double getMax(double[] inputArray) {
		double maxIndex = 0;
		for (int i = 1; i < inputArray.length; i++) {
			if (inputArray[i] > inputArray[i - 1]) {
				maxIndex = i;
				
			}
		}
		return maxIndex;
	}

}
