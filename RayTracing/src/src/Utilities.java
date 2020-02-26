package src;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Utilities {
	
	private static double resolution = 1.5;
	public static double[] normal(double[] vec){
		double[] newVec = new double[3];
		newVec[0] = vec[0];
		newVec[1] = vec[1];
		newVec[2] = vec[2];
		
		double mag = mag(vec);
		
		for(int i = 0; i < 3; i++)
			newVec[i]/=mag;
		
		return newVec;
		
	}
	public static double mag(double[] vec){
		return Math.sqrt(dot(vec,vec));
	}
	public static double addEntries(double[] vec){
		return vec[0]+vec[1]+vec[2];
	}
	public static double[] add(double[] vec, double[] vec2){
		double[] newVec = new double[3];
		newVec[0] = vec[0]+vec2[0];
		newVec[1] = vec[1]+vec2[1];
		newVec[2] = vec[2]+vec2[2];
		
		return newVec;
	}
	public static double[] subtract(double[] vec, double[] vec2){
		double[] newVec = new double[3];
		newVec[0] = vec[0]-vec2[0];
		newVec[1] = vec[1]-vec2[1];
		newVec[2] = vec[2]-vec2[2];
		
		return newVec;
		
	}
	public static double[] reflected(double[] rayVec, double[] norm){
		double[] normedNorm = Utilities.normal(norm);
		double[] resizedNorm = Utilities.multiply(normedNorm, Utilities.dot(normedNorm, rayVec));
		double[] difference = Utilities.subtract(rayVec, resizedNorm);
		double[] reflected = Utilities.subtract(difference, resizedNorm);
		return reflected;
		
		
	}
	public static double dot(double[] vec, double[] vec2){
		double[] newVec = new double[3];
		newVec[0] = vec[0]*vec2[0];
		newVec[1] = vec[1]*vec2[1];
		newVec[2] = vec[2]*vec2[2];
		
		return addEntries(newVec);
		
	}
	public static double[] multiply(double[] vec, double mag){
		double[] newVec = new double[3];
		newVec[0] = vec[0]*mag;
		newVec[1] = vec[1]*mag;
		newVec[2] = vec[2]*mag;
		
		return newVec;
		
	}
	public static double[] cross(double[] vec, double[] vec2){
		double[] newVec = new double[3];
		newVec[0] = vec[1]*vec2[2]-vec[2]*vec2[1];
		newVec[1] = vec[2]*vec2[0]-vec[0]*vec2[2];
		newVec[2] = vec[0]*vec2[1]-vec[1]*vec2[0];
		
		return newVec;
		
	}
	public static void print(double[] v){
		for(int i = 0; i < v.length; i++)
			System.out.print(v[i]+", ");
		System.out.println();
	}
	public static void runDisplay(){

		JPanel dummy = new JPanel();
		JFrame frame = new JFrame();
		Display Game = new Display(dummy,frame);
		
		// Console console = new Console();
		
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.add(dummy);
		frame.add(Game);
		dummy.requestFocusInWindow();
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setTitle("Ray");
		Game.start();
	}


}
