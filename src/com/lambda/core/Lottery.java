package com.lambda.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.lambda.core.util.FileManager;
import com.lambda.core.util.RandomCollection;

public class Lottery {
	
	private String firstWinner;
	
	private String secondWinner;
	
	private double firstPrize;
	
	private double secondPrize;
	
	private double donations;
	
	private double totalTickets;
	
	private Map<String, Double> partecipants;
	
	private FileManager manager;
	
	public Lottery(FileManager manager) {
		
		this.manager = manager;
		
		init();
		
	}
	
	public void init() {
		
		totalTickets = 0.0;
		partecipants = manager.getPartecipants();
		donations = manager.getDonations();
		
		calculateWins();
		
	}

	public String pickWinner1() {
		
		RandomCollection<String> people = new RandomCollection<String>();
		
		addElementsToCollection(people, partecipants);
		
		firstWinner = people.next();
		
		return String.format("First prize winner is: <font color='red'>%s</font>", firstWinner);
		
	}
	
	private void calculateWins() {
		
		Set<String> players = partecipants.keySet();
		
		for(String s : players) {
			
			totalTickets += partecipants.get(s);
			
		}
		
		double prizePool = (totalTickets * (1.0 - Settings.guildCut) * Settings.ticketCost) + donations;
		
		firstPrize = prizePool * Settings.firstWinnerCut;
		
		secondPrize = prizePool * Settings.secondWinnerCut;
		
	}

	public double calculateChances(String person) {
		
		return (partecipants.get(person) / totalTickets) * 100.0;
		
	}

	private void addElementsToCollection(RandomCollection<String> people, Map<String, Double> collection) {
		
		Set<String> names = collection.keySet();
		
		for(String s : names) {
			
			people.add(collection.get(s), s);
			
		}
		
	}

	public String pickWinner2() {
		
		Map<String, Double> newPartecipants = clonePartecipants();
		newPartecipants.remove(firstWinner);
		
		RandomCollection<String> people = new RandomCollection<String>();
		addElementsToCollection(people, newPartecipants);
		
		secondWinner = people.next();
		
		return String.format("Second prize winner is: <font color='red'>%s</font>", secondWinner);
		
	}

	private Map<String, Double> clonePartecipants() {
		
		Map<String, Double> result = new HashMap<String, Double>();
		Set<String> names = partecipants.keySet();
		
		for(String s : names) {
			
			result.put(s, partecipants.get(s));
			
		}
		
		return result;
		
	}

	public Map<String, Double> getPartecipantList() {
		
		return partecipants;
		
	}
	
	public double getFirstPrize() {
		
		return firstPrize;
		
	}
	
	public double getSecondPrize() {
		
		return secondPrize;
		
	}

	public double getTotalTickets() {
		
		return totalTickets;
		
	}
	
	public double getTotalPrize() {
		
		return firstPrize + secondPrize;
		
	}
	
	public FileManager getFileManager() {
		
		return manager;
		
	}

}
