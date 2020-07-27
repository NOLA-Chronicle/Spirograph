import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;

class Window extends JFrame implements ActionListener, ChangeListener{
	ImageIcon image;
	JLabel imageLabel;
	JLabel arm1length;
	JLabel arm2length;
	JLabel arm3length;
	JSpinner length1spinner;
	JSpinner length2spinner;
	JSpinner length3spinner;
	JSlider speed1slider;
	JSlider speed2slider;
	JSlider speed3slider;
	JTextField speed1txtbx;
	JTextField speed2txtbx;
	JTextField speed3txtbx;
	JButton drawButton;


	public Window(BufferedImage bi){
		//Displays Image
		image = new ImageIcon();
		imageLabel = new JLabel(image);
		image.setImage(bi);

		//Labels
		arm1length = new JLabel("Arm 1:");
		arm2length = new JLabel("Arm 2:");
		arm3length = new JLabel("Arm 3:");
		
		//Length Spinners
		SpinnerNumberModel lengthModel1 = new SpinnerNumberModel(50, 10, 50, 5);
		SpinnerNumberModel lengthModel2 = new SpinnerNumberModel(50, 10, 50, 5);
		SpinnerNumberModel lengthModel3 = new SpinnerNumberModel(50, 10, 50, 5);
		length1spinner = new JSpinner(lengthModel1);
		length1spinner.addChangeListener(this);
		length2spinner = new JSpinner(lengthModel2);
		length2spinner.addChangeListener(this);
		length3spinner = new JSpinner(lengthModel3);
		length3spinner.addChangeListener(this);

		//Speed Sliders
		//Actual value uses (slider.getValue() / 10)
		speed1slider = new JSlider(-100, 100, 10);
		speed1slider.addChangeListener(this);
		speed2slider = new JSlider(-100, 100, 10);
		speed2slider.addChangeListener(this);
		speed3slider = new JSlider(-100, 100, 10);
		speed3slider.addChangeListener(this);

		//Textbox Displays
		speed1txtbx = new JTextField(5);
		speed1txtbx.setEditable(false);
		speed1txtbx.setText(speed1slider.getValue() / 10.0 + "");
		speed2txtbx = new JTextField(5);
		speed2txtbx.setEditable(false);
		speed2txtbx.setText(speed2slider.getValue() / 10.0 + "");
		speed3txtbx = new JTextField(5);
		speed3txtbx.setEditable(false);
		speed3txtbx.setText(speed3slider.getValue() / 10.0 + "");

		//Button to start
		// drawButton = new JButton("DRAW");
		// drawButton.addActionListener(this);

		//Layout
		setLayout(new GridBagLayout());
		GridBagConstraints layoutConst = new GridBagConstraints();

		//Arm 1
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 1;
		layoutConst.gridy = 0;
		add(arm1length, layoutConst);
		
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 2;
		layoutConst.gridy = 0;
		add(length1spinner, layoutConst);

		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 2;
		layoutConst.gridy = 1;
		add(speed1slider, layoutConst);

		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 3;
		layoutConst.gridy = 1;
		add(speed1txtbx, layoutConst);

		//Arm 2
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;
		add(arm2length, layoutConst);
		
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 2;
		layoutConst.gridy = 2;
		add(length2spinner, layoutConst);

		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 2;
		layoutConst.gridy = 3;
		add(speed2slider, layoutConst);

		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 3;
		layoutConst.gridy = 3;
		add(speed2txtbx, layoutConst);
		
		//Arm 3		
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 1;
		layoutConst.gridy = 4;
		add(arm3length, layoutConst);
		
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 2;
		layoutConst.gridy = 4;
		add(length3spinner, layoutConst);

		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 2;
		layoutConst.gridy = 5;
		add(speed3slider, layoutConst);

		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridx = 3;
		layoutConst.gridy = 5;
		add(speed3txtbx, layoutConst);
		
		//Button
		// layoutConst.insets = new Insets(5, 5, 5, 5);
		// layoutConst.gridheight = 2;
		// layoutConst.gridx = 2;
		// layoutConst.gridy = 6;
		// add(drawButton, layoutConst);

		//Image
		layoutConst.insets = new Insets(5, 5, 5, 5);
		layoutConst.gridheight = 7;
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		add(imageLabel, layoutConst);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// System.out.println("Action Performed!");
		// try{
		// 	image.setImage(Arm.makeImage());
		// 	invalidate();
		// 	validate();
		// 	repaint();
		// } catch(IOException exception){
		// 	System.out.println("Exception caught!");
		// }
	}

	@Override
	public void stateChanged(ChangeEvent e){
		// System.out.println("State Changed!");
		JSlider sliderSource;
		JSpinner spinnerSource;
		double sliderSpeed;

		if(e.getSource() instanceof JSlider){
			sliderSource = (JSlider)e.getSource();
			sliderSpeed = sliderSource.getValue() / 10.0;

			if(sliderSpeed == 0.0){
				sliderSpeed = .01;
			}

			if(sliderSource  ==  speed1slider){
				speed1txtbx.setText(sliderSource.getValue() / 10.0 + "");
				Arm.setSpeed(0, sliderSpeed);
			
			} else if(sliderSource == speed2slider){
				speed2txtbx.setText(sliderSource.getValue() / 10.0 + "");
				Arm.setSpeed(1, sliderSpeed);
			
			} else {
				speed3txtbx.setText(sliderSource.getValue() / 10.0 + "");
				Arm.setSpeed(2, sliderSpeed);
			
			}
		} else if(e.getSource() instanceof JSpinner) {

			spinnerSource = (JSpinner)e.getSource();
			
			if(spinnerSource == length1spinner){
				Arm.setLength(0, (Integer)spinnerSource.getValue());
			} else if(spinnerSource == length2spinner){
				Arm.setLength(1, (Integer)spinnerSource.getValue());
			} else {
				Arm.setLength(2, (Integer)spinnerSource.getValue());
			}
		}
		//Redraw image
		try{
			image.setImage(Arm.makeImage());
			invalidate();
			validate();
			repaint();
		} catch(IOException exception){
			System.out.println("Exception caught!");
		}
	}
}