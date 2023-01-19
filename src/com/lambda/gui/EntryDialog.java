package com.lambda.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.lambda.core.util.FileManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class EntryDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton addButton;
	private JButton cancelButton;
	private JTextField usernameText;
	private JTextField ticketField;
	
	private boolean validTicketAmount;
	private boolean validUsername;

	/**
	 * Create the dialog.
	 * @param gui 
	 * @param manager 
	 * @param mainGUI 
	 */
	public EntryDialog(JFrame mainGUI, FileManager manager, GUI gui, boolean isAdd) {
		
		super(mainGUI);
		
		setBounds(100, 100, 450, 300);
		setTitle(isAdd ? "Add Entry" : "Edit Entry");
		setModalityType(DEFAULT_MODALITY_TYPE);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JLabel ticketsLabel = new JLabel("Ticket amount");
		ticketsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		usernameText = new JTextField();
		usernameText.setToolTipText("Insert the username of the lottery player; white spaces are not allowed.");
		usernameText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernameText.setColumns(10);
		usernameText.getDocument().addDocumentListener(new DocumentListener() {
			
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
				
				String text = usernameText.getText();
				
				usernameText.setForeground(Color.BLACK);
				validUsername = true;
				
				for(int i = 0; i < text.length(); i++) {
					
					if(text.charAt(i) == ' ' || text.charAt(i) <= '\t' || text.charAt(i) == '\n') {
						
						usernameText.setForeground(Color.RED);
						validUsername = false;
						break;
						
					}
					
				}
				
			}
			
		});
		
		if(!isAdd) {
			
			usernameText.setEditable(false);
			StringTokenizer st = new StringTokenizer(gui.getList().getSelectedValue());
			usernameText.setText(st.nextToken());
			
		}
		
		ticketField = new JTextField();
		ticketField.setToolTipText("Insert the amount of tickets tha player has bought; positive integers only.");
		ticketField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ticketField.setColumns(10);
		ticketField.getDocument().addDocumentListener(new DocumentListener() {
			
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
				
				String text = ticketField.getText();
				
				ticketField.setForeground(Color.BLACK);
				validTicketAmount = true;
				
				for(int i = 0; i < text.length(); i++) {
					
					if(!(text.charAt(i) >= '0' && text.charAt(i) <= '9')) {
						
						ticketField.setForeground(Color.RED);
						validTicketAmount = false;
						break;
						
					}
					
				}
				
			}
		});
		
		if(!isAdd) {
			
			ticketField.setText(String.format("%1.0f", manager.getPartecipants().get(usernameText.getText())));
			
		}
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(usernameText, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
					.addComponent(ticketField, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(30)
					.addComponent(usernameLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
					.addComponent(ticketsLabel)
					.addGap(30))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(86)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(usernameLabel)
						.addComponent(ticketsLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(ticketField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(63, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				addButton = new JButton(isAdd ? "Add" : "Change");
				addButton.setMnemonic(KeyEvent.VK_ENTER);
				addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				addButton.setActionCommand("OK");
				addButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(isAdd) {
							
							if(validUsername && validTicketAmount && usernameText.getText().length() > 0 && ticketField.getText().length() > 0) {
								
								manager.addPartecipant(usernameText.getText(), Double.valueOf(ticketField.getText()));
								manager.saveData();
								manager.getHistoryManager().addAdditionOperation(usernameText.getText(), Double.valueOf(ticketField.getText()));
								
								dispose();
								
								gui.reloadUI();
								
							} else {
								
								JOptionPane.showMessageDialog(null, "Invalid username or ticket value, please insert a valid entry.", "Invalid Value Error",
										JOptionPane.ERROR_MESSAGE);
								
							}
							
						} else {
							
							if(validTicketAmount && ticketField.getText().length() > 0) {
								
								manager.editPartecipant(usernameText.getText(), Double.valueOf(ticketField.getText()));
								manager.saveData();
								manager.getHistoryManager().addEditOperation(usernameText.getText(), Double.valueOf(ticketField.getText()));
								
								dispose();
								
								gui.reloadUI();
								
							} else {
								
								JOptionPane.showMessageDialog(null, "Invalid ticket value, please insert a valid entry.", "Invalid Value Error",
										JOptionPane.ERROR_MESSAGE);
								
							}
							
						}
						
					}
				});
				
				getRootPane().setDefaultButton(addButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setMnemonic(KeyEvent.VK_ESCAPE);
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						dispose();
						
					}
				});
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addContainerGap()
						.addComponent(addButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addGap(163)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addGap(16))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(addButton)
							.addComponent(cancelButton))
						.addGap(6))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
	}

}
