package de.vincenteichhorn.nnml;

import de.vincenteichhorn.math.Matrix;

public class NeuralNetwork {

	int inputUnits, outputUnits;
	int[] hiddenUnits;
	double learningRate;
	int layers;
	Matrix[] weights;
	Matrix[] bias;

	public NeuralNetwork(int inputs, int[] hiddens, int outputs, double lr) {
		this.inputUnits = inputs;
		this.hiddenUnits = hiddens;
		this.outputUnits = outputs;
		this.layers = this.hiddenUnits.length + 1;
		this.weights = new Matrix[this.layers];
		this.bias = new Matrix[this.layers];

		this.learningRate = lr;

		for (int i = 0; i < this.layers; i++) {
			if (i == 0) {
				this.weights[i] = new Matrix(this.hiddenUnits[i], this.inputUnits);
				this.bias[i] = new Matrix(this.hiddenUnits[i], 1);
			} else if (i == layers - 1) {
				this.weights[i] = new Matrix(this.outputUnits, this.hiddenUnits[i - 1]);
				this.bias[i] = new Matrix(this.outputUnits, 1);
			} else {
				this.weights[i] = new Matrix(this.hiddenUnits[i], this.hiddenUnits[i - 1]);
				this.bias[i] = new Matrix(this.hiddenUnits[i], 1);
			}
			this.weights[i].randomize(-1.0, 1.0);
			this.bias[i].randomize(-1.0, 1.0);
		}
	}

	public double[] getGuess(double[] inputArray) throws Exception {
		Matrix inputs = Matrix.fromArray(inputArray);
		Matrix[] outputs = new Matrix[this.layers];
		for (int i = 0; i < this.layers; i++) {
			if (i == 0) {
				outputs[i] = Matrix.multiply(this.weights[i], inputs);
			} else {
				outputs[i] = Matrix.multiply(this.weights[i], outputs[i - 1]);
			}
			outputs[i].add(this.bias[i]);
			outputs[i].activate();
		}
		return outputs[this.layers - 1].toArray();
	}

	public void train(double[] inputArray, double[] targetArray) throws Exception {

		Matrix target = Matrix.fromArray(targetArray);

		// feedforward
		Matrix inputs = Matrix.fromArray(inputArray);
		Matrix[] outputs = new Matrix[this.layers];
		Matrix[] copyOutputs = new Matrix[this.layers];
		for (int i = 0; i < this.layers; i++) {
			if (i == 0) {
				outputs[i] = Matrix.multiply(this.weights[i], inputs);
			} else {
				outputs[i] = Matrix.multiply(this.weights[i], outputs[i - 1]);
			}
			outputs[i].add(this.bias[i]);
			copyOutputs[i] = outputs[i].copy();
			outputs[i].activate();
		}

		// transpose
		Matrix[] transposedOutputs = new Matrix[this.layers];
		Matrix[] transposedWeights = new Matrix[this.layers];
		for (int i = 0; i < this.layers; i++) {
			transposedOutputs[i] = outputs[i].copyTransposed();
			transposedWeights[i] = this.weights[i].copyTransposed();
		}

		// feedbackward
		Matrix[] errors = new Matrix[this.layers];
		Matrix[] gradients = new Matrix[this.layers];
		Matrix[] deltaWeights = new Matrix[this.layers];

		// last neurons/weights
		errors[this.layers - 1] = Matrix.subtract(target, outputs[this.layers - 1]);
		gradients[this.layers - 1] = Matrix.gradient(copyOutputs[this.layers - 1]);
		gradients[this.layers - 1].multiply(errors[this.layers - 1]);
		gradients[this.layers - 1].multiply(this.learningRate);

		deltaWeights[this.layers - 1] = Matrix.multiply(gradients[this.layers - 1], transposedOutputs[this.layers - 2]);
		this.weights[this.layers - 1].add(deltaWeights[this.layers - 1]);
		this.bias[this.layers - 1].add(gradients[this.layers - 1]);

		// hidden neurons, more than 1 hidden layer
		if (this.layers > 2) {
			for (int i = this.layers - 2; i > 0; i--) {
				errors[i] = Matrix.multiply(transposedWeights[i + 1], errors[i + 1]);
				gradients[i] = Matrix.gradient(copyOutputs[i]);
				gradients[i].multiply(errors[i]);
				gradients[i].multiply(this.learningRate);
				
				deltaWeights[i] = Matrix.multiply(gradients[i], transposedOutputs[i - 1]);
				this.weights[i].add(deltaWeights[i]);
				this.bias[i].add(gradients[i]);
			}
		}

		// first neurons/weights nicht inputsunits

		errors[0] = Matrix.multiply(transposedWeights[1], errors[1]);
		gradients[0] = Matrix.gradient(copyOutputs[0]);
		gradients[0].multiply(errors[0]);
		gradients[0].multiply(this.learningRate);

		deltaWeights[0] = Matrix.multiply(gradients[0], Matrix.transpose(inputs));
		this.weights[0].add(deltaWeights[0]);
		this.bias[0].add(gradients[0]);
	}

	public void randomizeWeights() {
		for (int i = 0; i < this.layers; i++) {
			this.weights[i].randomize(-1.0, 1.0);
			this.bias[i].randomize(-1.0, 1.0);
		}
	}
	
	public void changeLearningrate(double lr) {
		this.learningRate = lr;
	}

}
