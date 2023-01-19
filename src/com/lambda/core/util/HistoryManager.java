package com.lambda.core.util;

public class HistoryManager {
	
	private StringBuilder history;
	
	public HistoryManager() {
		
		history = new StringBuilder();
		
	}
	
	//history.append(String.format("<html><font color='green'>Added %s with %1.0f tickets</font></html>\n", target, amount));
	
	public void addAdditionOperation(String target, double amount) {
		
		history.append(String.format("Added %s (%1.0f tickets)\n", target, amount));
		
	}
	
	public void addRemovalOperation(String target, double amount) {
		
		history.append(String.format("Removed %s (%1.0f tickets)\n", target, amount));
		
	}
	
	public void addEditOperation(String target, double amount) {
		
		history.append(String.format("Edited %s (%1.0f tickets)\n", target, amount));
		
	}
	
	public String getHistory() {
		
		return history.length() == 0 ? "No recent history detected." : history.toString();
		
	}

}
