package de.Jcing.tasks;

import java.util.LinkedList;

import de.Jcing.util.Util;

public class Clock {

	private static LinkedList<Task> tasks = new LinkedList<>();
	private static long startMillis = System.currentTimeMillis();
	
	
	private Clock() {
	}

	protected static void addTask(Task task) {
		tasks.add(task);
	}

	public static void start() {
		for(Task t : tasks)
			t.start();
	}

	public static void stop() {
		for(Task t : tasks)
			t.finish();
	}
	
	public static long millis() {
		return startMillis-System.currentTimeMillis();
	}
	
	
	public static void execute(Task task) {
		new Thread( () -> {
		long lastTick;
		long lastSec = System.currentTimeMillis();
		double difft = 0;
		int ticks = 0;
		while (task.running) {
			while (task.pause) {
				Util.sleep((long) task.waitingTime);
			}
			if (task.running == false) {
				return;
			}
			ticks++;			
			lastTick = System.currentTimeMillis();
			task.wrapper.run();
			
			if (System.currentTimeMillis() - lastSec >= 1000) {
				task.tps = ticks;
				ticks = 0;
				lastSec = System.currentTimeMillis();
			}
			
			difft -= (int)difft;
			difft += task.waitingTime-(System.currentTimeMillis() - lastTick);
			
			if (difft > 0)
				Util.sleep((long)(difft));
		}
		}).start();
	}

}
