import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


class Handler{
	public static void main(String[] args) throws IOException{
		Arm.setMultiplier(1);
		Arm.setDefaultSpeed(1);

		new Arm(50, 1, 90);
		new Arm(50, 1, 90);
		new Arm(50, 1, 90);

		Window drawFrame = new Window(Arm.makeImage());
		drawFrame.setTitle("Spirograph");
		
		drawFrame.pack();
		drawFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawFrame.setVisible(true);
	}
 }