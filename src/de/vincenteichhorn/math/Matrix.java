package de.vincenteichhorn.math;

import de.vincenteichhorn.math.Rand;

public class Matrix {

	private int rows;
	private int cols;
	private double[][] data;

	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.data = new double[this.rows][this.cols];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				data[i][j] = 0;
			}
		}
	}

	public void activate() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = Activation.sigmoid(data[i][j]);
			}
		}
	}

	public static Matrix gradient(Matrix m) {
		Matrix res = new Matrix(m.rows, m.cols);
		for (int i = 0; i < res.rows; i++) {
			for (int j = 0; j < res.cols; j++) {
				res.data[i][j] = Activation.dsigmoid(m.data[i][j]);
			}
		}
		return res;
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}

	public void randomize(int min, int max) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = Rand.random(min, max);
			}
		}
	}

	public void randomize(double min, double max) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] = Rand.random(min, max);
			}
		}
	}
	
	public void randomizeBit(double bit1, double bit2) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int r = Rand.randomBit();
				if(r == 0) {
					data[i][j] = bit1;
				} else {
					data[i][j] = bit2;
				}
			}
		}
	}
	
	public void setData(int x, int y, double val) {
		this.data[x][y] = val;
	}
	
	public double getData(int x, int y) {
		return this.data[x][y];
	}

	public double[] toArray() {
		double[] arr = new double[0];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				arr = Arrays.push(arr, data[i][j]);
			}
		}
		return arr;
	}

	public static Matrix fromArray(double[] arr) {
		Matrix m = new Matrix(arr.length, 1);
		for (int i = 0; i < arr.length; i++) {
			m.data[i][0] = arr[i];
		}
		return m;
	}

	public void printAsArray() {
		double[] arr = this.toArray();
		String s = "[ ";
		for (int i = 0; i < arr.length; i++) {
			s += arr[i] + ", ";
		}
		System.out.println(s + " ]");
	}

	public void printAsMatrix() {
		String row;
		for (int i = 0; i < rows; i++) {
			row = "";
			for (int j = 0; j < cols; j++) {
				row += " " + String.valueOf(data[i][j] + " : ");
			}
			System.out.println("| " + row + " |");
		}
	}

	public Matrix copy() {
		Matrix m = new Matrix(rows, cols);
		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.cols; j++) {
				m.data[i][j] += data[i][j];
			}
		}
		return m;
	}

	public void transpose() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] = this.data[j][i];
			}
		}
	}

	public static Matrix transpose(Matrix m) {
		Matrix res = new Matrix(m.cols, m.rows);
		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.cols; j++) {
				res.data[j][i] = m.data[i][j];
			}
		}
		return res;
	}

	public Matrix copyTransposed() {
		Matrix res = new Matrix(this.cols, this.rows);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				res.data[j][i] = this.data[i][j];
			}
		}
		return res;
	}

	public void add(Matrix other) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] += other.data[i][j];
			}
		}
	}

	public void add(double other) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] += other;
			}
		}
	}

	public void multiply(double other) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] *= other;
			}
		}
	}

	public void subtract(Matrix other) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] -= other.data[i][j];
			}
		}
	}

	public void subtract(double other) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i][j] -= other;
			}
		}
	}

	public static Matrix subtract(Matrix a, Matrix b) throws Exception {
		Matrix res = new Matrix(a.rows, a.cols);
		// System.out.println(a.rows + " - " + a.cols + "| " + b.rows + " - " + b.cols);
		for (int i = 0; i < a.rows; i++) {
			for (int j = 0; j < a.cols; j++) {
				res.data[i][j] = a.data[i][j] - b.data[i][j];
			}
		}
		return res;
	}

	public void multiply(Matrix other) throws Exception {
		if (this.cols != other.cols) {
			throw new Exception("Incompatible data sizes");
		} else {
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < other.cols; j++) {
					double sum = 0;
					for (int k = 0; k < this.cols; k++) {
						sum += this.data[i][k] * other.data[i][k];
					}
					this.data[i][j] = sum;
				}
			}
		}
	}

	public static Matrix multiply(Matrix a, Matrix b) throws Exception {
		// System.out.println(a.rows + " - " + a.cols + "| " + b.rows + " - " + b.cols);
		if (a.cols != b.rows) {
			throw new Exception("Multiply: Incompatible data sizes");
		} else {
			Matrix res = new Matrix(a.rows, b.cols);
			for (int i = 0; i < a.rows; i++) {
				for (int j = 0; j < b.cols; j++) {
					double sum = 0;
					for (int k = 0; k < a.cols; k++) {
						sum += a.data[i][k] * b.data[k][j];
					}
					res.data[i][j] = sum;
				}
			}
			return res;
		}
	}
	
	public static Matrix multiplyEachCell(Matrix a, Matrix b) throws Exception {
		if (a.cols != b.cols && a.rows != b.rows) {
			throw new Exception("MultyplyEachCell: Sizes not intendically >>" + a.rows + "x" + a.cols + " and " + b.rows + "x" + b.cols);
		} else {
			Matrix res = new Matrix(a.rows, a.cols);
			for (int i = 0; i < a.rows; i++) {
				for (int j = 0; j < a.cols; j++) {
					res.setData(i, j, a.getData(i, j) * b.getData(i, j));
				}
			}
			return res;
		}
	}

	public Matrix getSeparatePart(int x, int y, int width, int height) {
		Matrix res = new Matrix(height, width);
		int nx = 0;
		int ny = 0;
		for (int i = x; i < x + height; i++) {
			for (int j = y; j < y + width; j++) {
				try {
					res.setData(nx, ny, this.getData(i, j));
				} catch (Exception e) {
					res.setData(nx, ny, 0);
				}
				ny++;
			}
			ny = 0;
			nx++;
		}
		return res;
	}

	public double getAverage() {
		double sum = 0;
		double n = 0;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				sum = sum + this.data[i][j];
				n++;
			}
		}
		return sum / n;
	}
	
	public double getSum() {
		double sum = 0;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				sum += this.data[i][j];
			}
		}
		return sum;
	}

}
