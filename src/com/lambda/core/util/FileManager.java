package com.lambda.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.lambda.Main;
import com.lambda.core.Settings;

public class FileManager {
	
	private static final int lineAMT = 3;
	
	private double donations;
	
	private boolean detectedErrors;
	
	private Map<String, Double> partecipants;
	
	private HistoryManager history;
	
	public FileManager() {
		
		history = new HistoryManager();
		
		donations = 0.0;
		
		partecipants = new HashMap<String, Double>();
		
		checkData();
		
		readData();
		
	}

	private void checkData() {
		
		File data = new File(Main.dataPath);
		
		if(!data.exists()) {
			
			saveData();
			
		}
		
	}

	private void readData() {
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(Main.dataPath));
			String[] lines = new String[lineAMT];
			int lineCounter = 0;
			
			String line = reader.readLine();
			
			while(lineCounter < lineAMT && line != null) {
				
				lines[lineCounter] = line;
				lineCounter++;
				line = reader.readLine();
				
			}
			
			reader.close();
			
			processLines(lines);
			
			if(lineCounter < lineAMT) {
				
				saveData();
				
				JOptionPane.showMessageDialog(null, "Data file was corrupted and some data might have been reset!", "Corrupt Data Warning",
						JOptionPane.WARNING_MESSAGE);
				
			}
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, String.format("Could not read file %s. File does not exist or this program has no permissions to read the file.",
					Main.dataPath), "Data error", JOptionPane.ERROR_MESSAGE);
			
			detectedErrors = true;
			
		}
		
	}

	private void processLines(String[] lines) {
		
		for(int i = 0; i < lines.length; i++) {
			
			if(lines[i] != null && lines[i].length() != 0) {
				
				if(startsWith(lines[i], "settings=")) {
					
					processSettings(lines[i].substring("settings=".length()));
					
				}
				
				if(startsWith(lines[i], "players=")) {
					
					processPlayers(lines[i].substring("players=".length()));
					
				}
				
				if(startsWith(lines[i], "donations=")) {
					
					processDonations(lines[i].substring("donations=".length()));
					
				}
				
			}
			
		}
		
	}

	private boolean startsWith(String line, String textToken) {
		
		return line.substring(0, textToken.length()).equals(textToken);
		
	}

	private void processSettings(String line) {
		
		int index = 0;
		boolean mutex = true;
		StringBuilder sb = new StringBuilder();
		
		String setting = "";
		String value = "";
		
		while(index < line.length()) {
			
			if(startsWith(line, "#")) {
				
				break;
				
			}
			
			if(mutex) {
				
				if(line.charAt(index) != ':') {
					
					sb.append(line.charAt(index));
					
				} else {
					
					setting = sb.toString();
					sb.setLength(0);
					mutex = false;
					
				}
				
			} else {
				
				if(line.charAt(index) != ';') {
					
					sb.append(line.charAt(index));
					
				} else {
					
					value = sb.toString();
					sb.setLength(0);
					mutex = true;
					
					parseSetting(setting, value);
					
				}
				
			}
			
			index++;
			
		}
		
	}

	private void parseSetting(String setting, String value) {
		
		if(setting != null && value != null) {
			
			if(setting.equals(Settings.guildCutName)) {
				
				Settings.guildCut = Double.valueOf(value);
				
			}
			
			if(setting.equals(Settings.ticketCostName)) {
				
				Settings.ticketCost = Double.valueOf(value);
				
			}
			
			if(setting.equals(Settings.firstWinnerCutName)) {
				
				Settings.firstWinnerCut = Double.valueOf(value);
				
			}
			
			if(setting.equals(Settings.secondWinnerCutName)) {
				
				Settings.secondWinnerCut = Double.valueOf(value);
				
			}
			
		}
		
	}

	private void processDonations(String line) {
		
		int index = 0;
		StringBuilder sb = new StringBuilder();
		
		while(index < line.length()) {
			
			if(line.charAt(index) != ';') {
				
				sb.append(line.charAt(index));
				
			} else {
				
				donations += Double.valueOf(sb.toString());
				sb.setLength(0);
				
			}
			
			index++;
			
		}
		
	}

	private void processPlayers(String line) {
		
		int index = 0;
		boolean mutex = true;
		StringBuilder sb = new StringBuilder();
		
		String user = "";
		double tickets = 0.0;
		
		while(index < line.length()) {
			
			if(startsWith(line, "#")) {
				
				break;
				
			}
			
			if(mutex) {
				
				if(line.charAt(index) != ':') {
					
					sb.append(line.charAt(index));
					
				} else {
					
					user = sb.toString();
					sb.setLength(0);
					mutex = false;
					
				}
				
			} else {
				
				if(line.charAt(index) != ';') {
					
					sb.append(line.charAt(index));
					
				} else {
					
					tickets = Double.valueOf(sb.toString());
					sb.setLength(0);
					mutex = true;
					
					addPartecipant(user, tickets);
					
				}
				
			}
			
			index++;
			
		}
		
	}

	public void addPartecipant(String name, double tickets) {
		
		if(partecipants.containsKey(name)) {
			
			double current = partecipants.get(name);
			partecipants.put(name, current + tickets);
			
		} else {
			
			partecipants.put(name, tickets);
			
		}
		
	}
	
	public void editPartecipant(String name, double tickets) {
		
		if(partecipants.containsKey(name)) {
			
			partecipants.remove(name);
			partecipants.put(name, tickets);
			
		}
		
	}
	
	public void saveData() {
		
		try {
			
			FileWriter fw = new FileWriter(new File(Main.dataPath));
			
			fw.write(getFormattedData());
			
			fw.close();
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, String.format("Could create file %s. This program has no permissions to create the file.",
					Main.dataPath), "Data error", JOptionPane.ERROR_MESSAGE);
			
			detectedErrors = true;
			
		}
		
	}
	
	private String getFormattedData() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("settings=%s:%f;%s:%f;%s:%f;%s:%f;%n", Settings.guildCutName, Settings.guildCut,
				Settings.ticketCostName, Settings.ticketCost, Settings.firstWinnerCutName, Settings.firstWinnerCut,
				Settings.secondWinnerCutName, Settings.secondWinnerCut));
		
		sb.append("players=");
		
		if(partecipants.size() == 0) {
			
			sb.append("#;\n");
			
		} else {
			
			Set<String> players = partecipants.keySet();
			
			for(String s : players) {
				
				sb.append(String.format("%s:%f;", s, partecipants.get(s)));
				
			}
			
			sb.append('\n');
			
		}
		
		sb.append(String.format("donations=%f;", donations));
		
		return sb.toString();
		
	}

	public double getDonations() {
		
		return donations;
		
	}

	public boolean hasErrors() {
		
		return detectedErrors;
		
	}

	public Map<String, Double> getPartecipants() {
		
		return partecipants;
		
	}

	public void setDonations(double value) {
		
		donations = value;
		
	}

	public void addPartecipantsFromFile(File file) {
		
		String fileData = "";
		boolean skipped = false;
		
		try {
			
			Scanner sc = new Scanner(file);
			sc.useDelimiter("\\Z");
			
			fileData = sc.next();
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			
			JOptionPane.showMessageDialog(null, String.format("Could not read file %s.", file.getName()), "File Not Found", JOptionPane.ERROR_MESSAGE);
			
			detectedErrors = true;
			
		}
		
		StringTokenizer st = new StringTokenizer(fileData);
		
		while(st.hasMoreTokens()) {
			
			String username = st.nextToken();
			String tickets = st.nextToken();
			
			if(username != null && tickets != null) {
					
				if(isNumber(tickets)) {
						
					double ticket = Double.valueOf(tickets);
					addPartecipant(username, ticket);
					history.addAdditionOperation(username, ticket);
						
				} else {
					
					skipped = true;
					
				}
				
			} else {
				
				skipped = true;
				
			}
			
		}
		
		if(skipped) {
			
			JOptionPane.showMessageDialog(null, "Invalid line format detected, some entries have been skipped!", "Entry Skip Warning", JOptionPane.WARNING_MESSAGE);
			
		}
		
	}

	private boolean isNumber(String number) {
		
		for(int i = 0; i < number.length(); i++) {
			
			if(!(number.charAt(i) >= '0' && number.charAt(i) <= '9')) {
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	public HistoryManager getHistoryManager() {
		
		return history;
		
	}

}
