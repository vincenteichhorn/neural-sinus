package de.vincenteichhorn.window;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import de.vincenteichhorn.main.Main;
import de.vincenteichhorn.main.util.Variables;
import de.vincenteichhorn.window.components.CoordPanel;
import de.vincenteichhorn.window.components.ErrorPanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window {

	private JFrame frame;
	public CoordPanel coordSys;
	public ErrorPanel errorSys;

	public Window() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();

		frame.setResizable(false);
		frame.setBounds(100, 100, 1030, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Function Predictor | Neural Network");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);

		coordSys = new CoordPanel();
		coordSys.setBounds(10, 10, Variables.width, Variables.height);
		coordSys.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		coordSys.drawAxis(true, Color.BLACK);
		coordSys.drawMarkers(true, Color.BLACK);
		frame.getContentPane().add(coordSys);
		
		
		errorSys = new ErrorPanel();
		errorSys.setBounds(10, Variables.height + 20, Variables.errorWidth, Variables.errorHeight);
		errorSys.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		errorSys.drawAxis(true, Color.BLACK);
		frame.getContentPane().add(errorSys);

	}
}
