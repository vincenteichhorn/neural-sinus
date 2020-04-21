package de.vincenteichhorn.window.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import de.vincenteichhorn.main.Main;

public class ErrorPanel extends JLabel {
	private static final long serialVersionUID = 1L;

	private int width, height;
	private boolean drawAxis;
	private Color axisColor;
	public Graphics2D g = (Graphics2D) this.getGraphics();

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
		
		try {
			Main.drawErrors();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (drawAxis) {
			g.setColor(this.axisColor);
			g.drawString("Generations", width - 80, height - 20);
			g.drawString("Errors", 20, 20);

			drawHorizontalLine(0, height - 2, width, height - 2, this.axisColor, 1); // xAx
			drawVerticalLine(1, 0, 1, height, this.axisColor, 1); // yAx

			drawHorizontalLine(width - 5, height - 5, width, height, this.axisColor, 1); // x
			drawVerticalLine(5, 5, 0, 0, this.axisColor, 1);// y
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
