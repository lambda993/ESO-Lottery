package com.lambda;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import com.lambda.core.util.FileManager;
import com.lambda.gui.GUI;

public class Main {
	
	public static final String dataPath = "data.dat";
	
	public static final String version = "v2.1";

	public static void main(String[] args) {
		
		runGUI();

	}

	private static void runGUI() {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					FileManager manager = new FileManager();					
					GUI window = new GUI(manager);
					
					if(!manager.hasErrors()) {
						
						window.mainGUI.setVisible(true);
						window.mainGUI.setTitle("Lambda93's Lottery Tool " + version);
						
					}
					
				} catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, String.format("Application failed to launch!%nError Type = %s%nError Mesage = %s",
							e.getClass().getCanonicalName(), e.getMessage()), "Fatal Error", JOptionPane.ERROR_MESSAGE);
					
					e.printStackTrace();
					
				}
				
			}
			
		});
		
	}

}
