package org.Jcing.main;

import java.util.LinkedList;

public class Reminder {
	
	LinkedList<Runnable> toRemind;
	
	public Reminder(){
		toRemind = new LinkedList<>();
	}
	
	public void addRemindable(Runnable toRemind){
		this.toRemind.add(toRemind);
	}
	
	public void remind(){
		for(Runnable r : toRemind)
			r.run();
	}
	
	
}
