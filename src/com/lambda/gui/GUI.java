package com.lambda.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import com.lambda.core.Lottery;
import com.lambda.core.util.FileManager;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import net.miginfocom.swing.MigLayout;

public class GUI {

	public JFrame mainGUI;
	/**
	 * Create the application.
	 */
	
	private Lottery lottery;
	private JDialog settingsDialog;
	private JDialog dialogAddEntry;
	private JButton pickWinner;
	private JScrollPane scrollPane;
	private JButton clearList;
	private JButton settings;
	private JList<String> list;
	private JLabel partecipantData;
	
	private DefaultListModel<String> model;
	private JButton addEntry;
	private JButton addFromFile;
	private JMenuBar menuBar;
	private JLabel partecipantData2;
	public GUI(FileManager manager) {
		
		lottery = new Lottery(manager);
		
		model = new DefaultListModel<>();
		
		initialize(manager);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(FileManager manager) {
		
		mainGUI = new JFrame();
		mainGUI.setResizable(false);
		
		mainGUI.setBounds(100, 100, 620, 564);
		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainGUI.setLocation(dim.width/2 - mainGUI.getSize().width/2, dim.height/2 - mainGUI.getSize().height/2);
		
		scrollPane = new JScrollPane();
		
		list = new JList<>(model);
		list.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scrollPane.setViewportView(list);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(list, popupMenu);
		
		JMenuItem printDataField = new JMenuItem("Copy list to clipboard");
		printDataField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String listToClipboard = getListAsString();
				StringSelection sel = new StringSelection(listToClipboard);
				Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
				cp.setContents(sel, null);
				
			}

			private String getListAsString() {
				
				StringBuilder sb = new StringBuilder();
				
				for(int i = 0; i < model.size(); i++) {
					
					String line = model.get(i);
					StringTokenizer st = new StringTokenizer(line);
					String t1, t2;
					
					t1 = st.nextToken();
					t2 = st.nextToken();
					
					sb.append(String.format("%s\t\t\t%s%n", t1, t2.substring(1, t2.length())));
					
				}
				
				return sb.toString();
				
			}
			
		});
		
		JMenuItem editField = new JMenuItem("Edit ticket amount");
		editField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(list.getSelectedValue() != null) {
					
					dialogAddEntry = new EntryDialog(mainGUI, manager, GUI.this, false);
					dialogAddEntry.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialogAddEntry.setLocationRelativeTo(mainGUI);
					dialogAddEntry.setVisible(true);
					
				} else {
					
					JOptionPane.showMessageDialog(null, "No entry was selected!", "Invalid Entry Warning", JOptionPane.WARNING_MESSAGE);
					
				}
				
			}
		});
		
		JMenuItem deleteEntry = new JMenuItem("Delete selected field");
		deleteEntry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String selected = list.getSelectedValue();
				
				if(selected == null || selected.length() == 0) {
					
					JOptionPane.showMessageDialog(null, "No entry was selected!", "Invalid Entry Warning", JOptionPane.WARNING_MESSAGE);
					
				} else {
					
					StringTokenizer st = new StringTokenizer(selected);
					String name = st.nextToken();
					
					int dialogResult = JOptionPane.showConfirmDialog(null, String.format(
							"Are you sure you want to delete %s from the list? This operation cannot be reverted!",	name), "Delete Selected Entriy Warning",
							JOptionPane.YES_NO_OPTION);
					
					if(dialogResult == JOptionPane.YES_OPTION) {
						
						manager.getHistoryManager().addRemovalOperation(name, manager.getPartecipants().get(name));
						manager.getPartecipants().remove(name);
						manager.saveData();
						reloadUI();
						
					}
					
				}
				
			}
		});
		
		JMenuItem historyField = new JMenuItem("Show Input History");
		historyField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JTextPane lbl = new JTextPane();
				lbl.setText(manager.getHistoryManager().getHistory());
				lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lbl.setEditable(false);
				
				JScrollPane scrollPane = new JScrollPane(lbl);
				
				JOptionPane.showMessageDialog(null, scrollPane, "History View", JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		
		mainGUI.getContentPane().setLayout(new MigLayout("", "[594px]", "[488px][]"));
		
		popupMenu.add(editField);
		popupMenu.add(deleteEntry);
		popupMenu.add(historyField);
		popupMenu.add(printDataField);
		
		partecipantData = new JLabel("Data");
		partecipantData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		partecipantData.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(partecipantData);
		mainGUI.getContentPane().add(scrollPane, "cell 0 0,grow");
		
		partecipantData2 = new JLabel("New label");
		partecipantData2.setHorizontalAlignment(SwingConstants.CENTER);
		partecipantData2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		mainGUI.getContentPane().add(partecipantData2, "cell 0 1");
		
		menuBar = new JMenuBar();
		mainGUI.setJMenuBar(menuBar);
		
		addEntry = new JButton("Add Single Entry");
		menuBar.add(addEntry);
		addEntry.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addEntry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dialogAddEntry = new EntryDialog(mainGUI, manager, GUI.this, true);
				dialogAddEntry.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogAddEntry.setLocationRelativeTo(mainGUI);
				dialogAddEntry.setVisible(true);
				
			}
		});
		
		addFromFile = new JButton("Add Entries From File");
		menuBar.add(addFromFile);
		addFromFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		addFromFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						
						return "Text Document (.txt)";
						
					}
					
					@Override
					public boolean accept(File f) {
						
						if(f.isDirectory()) {
							
							return true;
							
						}
						
						String fname = f.getName().toLowerCase();
						
						return fname.endsWith("txt");
						
					}
				});
				File wd = new File(System.getProperty("user.dir"));
				fc.setCurrentDirectory(wd);
				int returnValue = fc.showOpenDialog(mainGUI);
				
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					
					manager.addPartecipantsFromFile(fc.getSelectedFile());
					manager.saveData();
					
				} else if(returnValue == JFileChooser.CANCEL_OPTION) {
					
					//got nothing to do here i guess
					
				} else if(returnValue == JFileChooser.ERROR_OPTION) {
					
					JOptionPane.showMessageDialog(null, "An error occured while selecting the file!", "Selection Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
				reloadUI();
				
			}
		});
		
		clearList = new JButton("Clear List");
		menuBar.add(clearList);
		clearList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		clearList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(lottery.getPartecipantList().size() == 0) {
					
					JOptionPane.showMessageDialog(null, "List is empty!", "Emplty List Warning", JOptionPane.WARNING_MESSAGE);
					
				} else {
					
					int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all entries? This operation cannot be reverted!", 
							"Delete All Entries Warning", JOptionPane.YES_NO_OPTION);
					
					if(dialogResult == JOptionPane.YES_OPTION) {
						
						manager.getPartecipants().clear();
						manager.saveData();
						reloadUI();
						
					}
					
				}
				
			}
		});
		
		pickWinner = new JButton("Pick Winners!");
		menuBar.add(pickWinner);
		pickWinner.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		settings = new JButton("Settings");
		menuBar.add(settings);
		settings.setFont(new Font("Tahoma", Font.PLAIN, 12));
		settings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				settingsDialog = new SettingsDialog(mainGUI, manager, GUI.this);
				settingsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				settingsDialog.setLocationRelativeTo(mainGUI);
				settingsDialog.setVisible(true);
				
			}
			
		});
		pickWinner.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(lottery.getPartecipantList().size() < 2) {
					
					JOptionPane.showMessageDialog(null, "You need at least 2 players to pick the winners.", "Player Amount Warning",
							JOptionPane.WARNING_MESSAGE);
					
				} else {
					
					JLabel message = new JLabel();
					message.setFont(new Font("Tahoma", Font.BOLD, 20));
					message.setText(String.format("<html>%s<br>%s</html>", lottery.pickWinner1(), lottery.pickWinner2()));
					
					JOptionPane.showMessageDialog(null, message, "Lottery Winners", JOptionPane.PLAIN_MESSAGE);
					
				}
				
			}
			
		});
		
		addElementsToList();
		
	}
	
	public void reloadUI() {
		
		lottery.init();
		
		model.clear();
		
		addElementsToList();
		
	}

	private void addElementsToList() {
		
		Map<String, Double> list = lottery.getPartecipantList();
		List<String> items = new ArrayList<String>(list.keySet());
		
		items.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				
				if(list.get(o1) < list.get(o2)) {
					
					return 1;
					
				} else if(list.get(o1) > list.get(o2)) {
					
					return -1;
					
				}
					
				return 0;
				
			}
			
		});
		
		for(String s : items) {
			
			double currentTickets = list.get(s);
			
			model.addElement(String.format("%s (%1.0f tickets) (%.02f%% chance)", s, currentTickets, lottery.calculateChances(s)));
			
		}
		
		partecipantData.setText(String.format("| Players: %d | Tickets: %1.0f | First Prize : %1.0f | Second Prize : %1.0f |", items.size(),
				lottery.getTotalTickets(), lottery.getFirstPrize(), lottery.getSecondPrize()));
		
		partecipantData2.setText(String.format("Total winnings: %1.0f gold.", lottery.getTotalPrize()));
		
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		
		component.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				
				if (e.isPopupTrigger()) {
					
					showMenu(e);
					
				}
				
			}
			
			public void mouseReleased(MouseEvent e) {
				
				if (e.isPopupTrigger()) {
					
					showMenu(e);
					
				}
				
			}
			
			private void showMenu(MouseEvent e) {
				
				popup.show(e.getComponent(), e.getX(), e.getY());
				
			}
			
		});
		
	}
	
	public JList<String> getList() {
		
		return list;
		
	}
	
}
