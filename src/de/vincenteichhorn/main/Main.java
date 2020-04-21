package de.vincenteichhorn.main;

import java.awt.Color;
import java.awt.Font;

import de.vincenteichhorn.main.util.Variables;
import de.vincenteichhorn.math.Util;
import de.vincenteichhorn.nnml.NeuralNetwork;
import de.vincenteichhorn.window.Window;

public class Main {

	public static Window window;
	public static NeuralNetwork brain;
	public static int generations = 0;
	public static int[] errorNetoutput = new int[Variables.width];
	public static int[] errorValue = new int[Variables.width];
	public static double[] errorDifference = new double[Variables.width];
	public static int smallestErrorNetoutput = 1000;
	public static int smallestErrorValues = 1000;
	public static int bestGenerationNetoutput = 0;
	public static int bestGenerationValues = 0;
	public static Color target = Color.BLACK;
	public static Color guess = Color.RED;

	public static void main(String[] args) throws Exception {
		int[] hidden = {50};
		brain = new NeuralNetwork(1, hidden, 1, 0.01);
		for (int i = 0; i < errorNetoutput.length; i++) {
			errorNetoutput[i] = 1200;
			errorValue[i] = 1200;
			errorDifference[i] = 1200;
		}
		errorNetoutput[Variables.width - 1] = 1000;
		errorValue[Variables.width - 1] = 1000;
		window = new Window();

	}

	public static void update() {
		window.coordSys.update();
	}

	public static void startCalculation() throws Exception {
		drawFunction(target);
		drawBrainGuess(guess);
		trainStep();

		generations++;
		window.coordSys.g.setColor(Color.DARK_GRAY);
		window.coordSys.g.drawString("Generations: " + String.valueOf(generations), 5, Variables.height - 5);

	}

	public static void trainStep() throws Exception {
		double sumDiff = 0;
		for (int j = 0; j < Variables.width; j++) {
			double xval = Util.map(j, 0, Variables.width, Variables.coordStartX, Variables.coordEndX);
			double yval = function(xval);
			double[] input = { Util.map(xval, Variables.coordStartX, Variables.coordEndX, 0, 1) };
			double[] target = { Util.map(yval, Variables.coordStartY, Variables.coordEndY, 0, 1) };
			brain.train(input, target);
			if (Util.round(brain.getGuess(input)[0], 2) == Util.round(target[0], 2)) {
				errorNetoutput[Variables.width - 1] -= 1;
			}
			if (Util.round(Util.map(brain.getGuess(input)[0], 0, 1, Variables.coordStartY, Variables.coordEndY),
					1) == Util.round(xval, 1)) {
				errorValue[Variables.width - 1] -= 8;
			}
			sumDiff += Math.abs(brain.getGuess(input)[0] - target[0]);
		}
		errorDifference[Variables.width - 1] = sumDiff * 2;
	}

	public static void drawBrainGuess(Color c) throws Exception {
		int[] x = new int[Variables.width];
		int[] y = new int[Variables.width];
		for (int i = 0; i < Variables.width; i++) {
			double xval = Util.map(i, 0, Variables.width, 0, 1);
			double[] input = { xval };
			double[] output = brain.getGuess(input);

			x[i] = i;
			y[i] = (int) Util.map(output[0], 0, 1, Variables.height, 0);
		}
		drawGraph(x, y, c, 3);
	}

	public static void drawFunction(Color c) {
		for (int i = 0; i < Variables.width; i++) {
			double x = Util.map(i, 0, Variables.width, Variables.coordStartX, Variables.coordEndX);
			double y = function(x);
			drawDot(i, (int) Util.map(y, Variables.coordStartY, Variables.coordEndY, Variables.height, 0), c);
		}
	}

	public static void drawDot(int x, int y, Color c) {
		window.coordSys.drawDot((int) x, (int) y, c);
	}

	private static void drawGraph(int[] x, int[] y, Color c, int t) {
		for (int i = 0; i < y.length - 1; i++) {
			window.coordSys.drawHorizontalLine(x[i], y[i], x[i + 1], y[i + 1], c, t);
		}
	}

	private static double function(double x) {
		return 3 * Math.sin(x);
	}

	public static void drawErrors() {
		for (int i = 0; i < errorNetoutput.length; i++) {
			if (smallestErrorValues > errorValue[i]) {
				smallestErrorValues = errorValue[i];
				bestGenerationValues = generations;
			}
			if (smallestErrorNetoutput > errorNetoutput[i]) {
				smallestErrorNetoutput = errorNetoutput[i];
				bestGenerationNetoutput = generations;
			}
		}
		Font f = window.errorSys.g.getFont();
		window.errorSys.g.setFont(new Font("Arial", 20, 18));
		window.errorSys.g.setColor(Color.BLUE);
		window.errorSys.g.drawString(
				"BY NETWORK ACTIVITY | current error: " + String.valueOf(errorNetoutput[Variables.width - 1])
						+ ", smallest error: " + String.valueOf(smallestErrorNetoutput) + ", best generation: "
						+ String.valueOf(bestGenerationNetoutput),
				5, Variables.height - 5);
		window.errorSys.g.setColor(Color.GREEN);
		window.errorSys.g.drawString("BY FUNCTION VALUES | current error: "
				+ String.valueOf(errorValue[Variables.width - 1]) + ", smallest error: "
				+ String.valueOf(smallestErrorValues) + ", best generation: " + String.valueOf(bestGenerationValues), 5,
				Variables.height - 25);
		window.errorSys.g.setColor(Color.RED);
		window.errorSys.g.drawString(
				"BY AVERAGE DIFFERENCE | current error: " + String.valueOf(Math.round(errorDifference[Variables.width - 1])), 5,
				Variables.height - 45);

		window.errorSys.g.setColor(Color.BLACK);
		window.errorSys.g.setFont(f);

		for (int i = 0; i < errorNetoutput.length; i++) {
			try {
				errorNetoutput[i] = errorNetoutput[i + 1];
				errorValue[i] = errorValue[i + 1];
				errorDifference[i] = errorDifference[i + 1];
			} catch (Exception e) {
				errorNetoutput[i] = 1000;
				errorValue[i] = 1000;
				errorDifference[i] = 0;
			}
		}

		for (int i = 0; i < errorNetoutput.length; i++) {
			window.errorSys.drawHorizontalLine(i, Variables.errorHeight - errorNetoutput[i] / 4 + 5, i + 2,
					Variables.errorHeight - errorNetoutput[i + 1] / 4 + 5, Color.BLUE, 4);
			window.errorSys.drawHorizontalLine(i, Variables.errorHeight - errorValue[i] / 4 + 5, i + 2,
					Variables.errorHeight - errorValue[i + 1] / 4 + 5, Color.GREEN, 4);
			window.errorSys.drawHorizontalLine(i, (int) (Variables.errorHeight - errorDifference[i]), i + 2,
					(int) (Variables.errorHeight - errorDifference[i + 1]), Color.RED, 4);
		}

	}
}
