package de.jcing.main;

import java.util.Iterator;
import java.util.LinkedList;

public class Reminder<Reminding> {
	
	LinkedList<Remindable<Reminding>> toRemind;
	Reminding r;
	
	public Reminder(Reminding r){
		this.r = r;
		toRemind = new LinkedList<Remindable<Reminding>>();
	}
	
	public void addRemindable(Remindable<Reminding> toRemind){
		this.toRemind.add(toRemind);
	}
	
	public void remind(){
		Iterator<Remindable<Reminding>> i = toRemind.iterator();
		while(i.hasNext()){
			i.next().remind(r);
		}
	}
	
	
}
