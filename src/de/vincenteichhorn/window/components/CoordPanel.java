package de.vincenteichhorn.window.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;

import de.vincenteichhorn.main.Main;
import de.vincenteichhorn.main.util.Variables;
import de.vincenteichhorn.math.Util;

public class CoordPanel extends JLabel {

	private static final long serialVersionUID = 1L;

	private int minX, minY, maxX, maxY, width, height;
	private boolean drawAxis, drawMarkers;
	private Color axisColor, makerColor;
	public Graphics2D g = (Graphics2D) this.getGraphics();

	public CoordPanel() {
		this.minX = Variables.coordStartX;
		this.maxX = Variables.coordEndX;
		this.minY = Variables.coordStartY;
		this.maxY = Variables.coordEndY;

		this.setBackground(Variables.coordinateSystemBackgroundColor);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		g = (Graphics2D) graphics;

		if (drawAxis) {
			g.setColor(this.axisColor);
			g.drawString("X", width - 20, height / 2 + 20);
			g.drawString("Y", 20, 20);

			drawHorizontalLine(0, height / 2, width, height / 2, this.axisColor, 1); // xAx
			drawVerticalLine(0, 0, 0, height, this.axisColor, 1); // yAx

			drawHorizontalLine(width - 5, height / 2 - 5, width, height / 2, this.axisColor, 1); // x
			drawHorizontalLine(width - 5, height / 2 + 5, width, height / 2, this.axisColor, 1);// x
			drawVerticalLine(0, 5, 0, 0, this.axisColor, 1);// y
			drawVerticalLine(5, 5, 0, 0, this.axisColor, 1);// y
		}
		if (drawMarkers) {
			for (int i = this.minX; i < this.maxX + 1; i++) {
				int x = (int) Util.map(i, this.minX, this.maxX, 0, this.width);
				drawVerticalLine(x, height / 2 - 5, x, height / 2 + 5, this.makerColor, 1);
			}

			for (int i = this.minY; i < this.maxY + 1; i++) {
				int y = (int) Util.map(i, this.minY, this.maxY, 0, this.height);
				drawHorizontalLine(0, y, 5, y, this.makerColor, 1);
			}
		}

		try {
			Main.startCalculation();
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaint();
	}

	public void update() {
		this.repaint();
	}

	public void drawDot(int x, int y, Color drawColor) {
		g.setColor(drawColor);
		g.drawRect(x, y, 1, 1);
	}

	public void drawAxis(boolean draw, Color drawColor) {
		this.drawAxis = draw;
		this.axisColor = drawColor;
	}

	public void drawMarkers(boolean draw, Color drawColor) {
		this.drawMarkers = draw;
		this.makerColor = drawColor;
	}

	public void drawHorizontalLine(int x, int y, int x2, int y2, Color c, int t) {
		g.setColor(c);
		for (int i = 0; i < t; i++) {
			g.drawLine(x, y - i, x2, y2 - i);
		}
	}

	public void drawVerticalLine(int x, int y, int x2, int y2, Color c, int t) {
		g.setColor(c);
		for (int i = 0; i < t; i++) {
			g.drawLine(x + i, y, x2 + i, y2);
		}
	}

}
