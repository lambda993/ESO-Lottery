package com.lambda.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.lambda.core.Settings;
import com.lambda.core.util.FileManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SettingsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton saveButton;
	private JButton discardButton;
	private JLabel ticketCostLabel;
	private JTextField goldAmountField;
	
	private JLabel guildCutLabel;
	private JSlider guildCutSlider;
	private JLabel firstPrizeLabel;
	private JSlider firstPrizeSlider;
	private JLabel secondPrizeLabel;
	private JSlider secondPrizeSlider;
	
	private boolean validGoldAmountTicketCost;
	private boolean validGoldAmountDonations;
	private JTextField donationsField;
	
	private FileManager manager;

	/**
	 * Create the dialog.
	 * @param mainGUI 
	 * @param manager 
	 * @param parent 
	 * @param settingsData 
	 */
	public SettingsDialog(JFrame mainGUI, FileManager manager, GUI parent) {
		
		super(mainGUI);
		
		this.manager = manager;
		
		setTitle("Settings");
		
		setBounds(100, 100, 512, 435);
		setModalityType(DEFAULT_MODALITY_TYPE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		{
			ticketCostLabel = new JLabel("Value of tickets in gold:");
			ticketCostLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		
		goldAmountField = new JTextField();
		goldAmountField.setHorizontalAlignment(SwingConstants.CENTER);
		goldAmountField.setToolTipText("Insert a positive integer that will represent the gold value of a single ticket.");
		goldAmountField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		goldAmountField.setColumns(10);
		
		guildCutLabel = new JLabel("Guild cut in %:");
		guildCutLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		guildCutSlider = new JSlider();
		guildCutSlider.setMinorTickSpacing(25);
		guildCutSlider.setMajorTickSpacing(50);
		guildCutSlider.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		firstPrizeLabel = new JLabel("First prize cut in %:");
		firstPrizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		firstPrizeSlider = new JSlider();
		firstPrizeSlider.setMinorTickSpacing(25);
		firstPrizeSlider.setMajorTickSpacing(50);
		firstPrizeSlider.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		secondPrizeLabel = new JLabel("Second prize cut in %:");
		secondPrizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		secondPrizeSlider = new JSlider();
		secondPrizeSlider.setMinorTickSpacing(25);
		secondPrizeSlider.setMajorTickSpacing(50);
		secondPrizeSlider.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel donationLabel = new JLabel("Total donations in gold:");
		donationLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		donationsField = new JTextField();
		donationsField.setToolTipText("Insert a positive integer that will represent the total amount of gold donated. This gold amount is not affected by the guild cut slider.");
		donationsField.setHorizontalAlignment(SwingConstants.CENTER);
		donationsField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		donationsField.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(guildCutLabel, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(guildCutSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(firstPrizeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(secondPrizeLabel, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(secondPrizeSlider, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addComponent(firstPrizeSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(ticketCostLabel)
								.addComponent(donationLabel, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
							.addGap(81)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(donationsField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
								.addComponent(goldAmountField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(ticketCostLabel)
						.addComponent(goldAmountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(donationLabel)
						.addComponent(donationsField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(guildCutSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(guildCutLabel))
					.addGap(52)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(firstPrizeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(firstPrizeLabel))
					.addGap(47)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(secondPrizeSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(secondPrizeLabel))
					.addGap(20))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				saveButton = new JButton("Save");
				saveButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				saveButton.setActionCommand("OK");
				getRootPane().setDefaultButton(saveButton);
			}
			{
				discardButton = new JButton("Discard");
				discardButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				discardButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
						.addGap(125)
						.addComponent(discardButton, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
						.addContainerGap())
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(saveButton)
							.addComponent(discardButton))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		
		getSavedValues();
		
		guildCutSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				updateComponents();
				
			}
		});
		
		firstPrizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				if(firstPrizeSlider.getValue() > (100 - secondPrizeSlider.getValue())) {
					
					secondPrizeSlider.setValue(100 - firstPrizeSlider.getValue());
					
				}
				
				updateComponents();
				
			}
		});
		
		secondPrizeSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				
				if(secondPrizeSlider.getValue() > (100 - firstPrizeSlider.getValue())) {
					
					firstPrizeSlider.setValue(100 - secondPrizeSlider.getValue());
					
				}
				
				updateComponents();
				
			}
		});
		
		goldAmountField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				checkInput();
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				
				checkInput();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
				checkInput();
				
			}
			
			private void checkInput() {
				
				String text = goldAmountField.getText();
				
				goldAmountField.setForeground(Color.BLACK);
				validGoldAmountTicketCost = true;
				
				for(int i = 0; i < text.length(); i++) {
					
					if(!(text.charAt(i) >= '0' && text.charAt(i) <= '9')) {
						
						goldAmountField.setForeground(Color.RED);
						validGoldAmountTicketCost = false;
						break;
						
					}
					
				}
				
			}
		});
		
		donationsField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				checkInput();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				
				checkInput();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

				checkInput();
				
			}
			
			private void checkInput() {
				
				String text = donationsField.getText();
				
				donationsField.setForeground(Color.BLACK);
				validGoldAmountDonations = true;
				
				for(int i = 0; i < text.length(); i++) {
					
					if(!(text.charAt(i) >= '0' && text.charAt(i) <= '9')) {
						
						donationsField.setForeground(Color.RED);
						validGoldAmountDonations = false;
						break;
						
					}
					
				}
				
			}
		});
		
		discardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(validGoldAmountTicketCost && validGoldAmountDonations && goldAmountField.getText().length() > 0 && donationsField.getText().length() > 0) {
					
					Settings.ticketCost = Double.valueOf(goldAmountField.getText());
					Settings.guildCut = guildCutSlider.getValue() / 100.0;
					Settings.firstWinnerCut = firstPrizeSlider.getValue() / 100.0;
					Settings.secondWinnerCut = secondPrizeSlider.getValue() / 100.0;
					manager.setDonations(Double.valueOf(donationsField.getText()));
					
					manager.saveData();
					
					dispose();
					
					parent.reloadUI();
					
				} else {
					
					JOptionPane.showMessageDialog(null, "Invalid gold value, please insert a positive integer value.", "Invalid Value Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
		});
		
	}
	
	private void getSavedValues() {
		
		goldAmountField.setText(String.valueOf((int) Settings.ticketCost));
		donationsField.setText(String.valueOf((int) manager.getDonations()));
		guildCutSlider.setValue((int) (Settings.guildCut * 100));
		firstPrizeSlider.setValue((int) (Settings.firstWinnerCut * 100));
		secondPrizeSlider.setValue((int) (Settings.secondWinnerCut * 100));
		
		validGoldAmountTicketCost = true;
		validGoldAmountDonations = true;
		
		updateComponents();
		
	}

	private void updateComponents() {
		
		guildCutLabel.setText(String.format("Guild cut is %d%%:", guildCutSlider.getValue()));
		firstPrizeLabel.setText(String.format("First prize cut is %d%%:", firstPrizeSlider.getValue()));
		secondPrizeLabel.setText(String.format("Second prize cut is %d%%:", secondPrizeSlider.getValue()));
		
	}
}
