package org.Jcing.job;

import org.Jcing.Essentials.OutputStreamController;

public class Job extends Thread {
	private int TPS;
	private double waitingTime;
	private boolean running, pause;
	private Routine routine;
	private boolean forcejoin = false;
	private static int defaultforceTime = 1000;
	private int forceTime;

	private int tps;
	private int ticks;
	private boolean dead;
	
	private static OutputStreamController o = new OutputStreamController("Job", true);

	/**
	 * A Job is a Thread which calls a Routine repeatedly when a given Time has
	 * passed.
	 * 
	 * @param routine
	 *            the Routine of the Job
	 * @param tps
	 *            Ticks (Routine calls) per Second
	 * @param name
	 *            Name of the Job (and its Thread)
	 */
	public Job(Routine routine, int tps, String name) {
		TPS = tps;
		waitingTime = 1000.0 / TPS;
		this.forceTime = defaultforceTime;
		this.routine = routine;
		this.setName(name);
		this.routine.setJob(this);
		dead = true;
	}

	/**
	 * Starts the Thread (see run())
	 */
	@Override
	public void start() {
		dead = false;
		running = true;
		pause = false;
		this.routine.setJob(this);
		super.start();
	}

	public int getTps() {
		return tps;
	}
	
	public void setTps(int tps){
		TPS = tps;
		waitingTime = 1000.0 / TPS;
	}
	
	/**
	 * runs the Job. manages pause. runs Routine whenever time to wait since
	 * last run has passed. joins Thread when running == false
	 */
	@Override
	public void run() {
		long lastTick = System.currentTimeMillis();
		long lastSec = System.currentTimeMillis();
		double difft = 0;
		while (running) {
			while (pause) {
				try {
					sleep((long) waitingTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (running == false) {
					end();
					return;
				}
//				o.println("Job " + getName() + "Paused");
			}

			ticks++;
			difft -= (long) difft;
			difft += (System.currentTimeMillis() - lastTick) - waitingTime;
			lastTick = System.currentTimeMillis();
			routine.go();

			if (System.currentTimeMillis() - lastSec >= 1000) {
				tps = ticks;
				ticks = 0;
//				diffs -= (long) diffs;
//				diffs = System.currentTimeMillis() - lastSec - 1000;
				lastSec = System.currentTimeMillis();
			}
			try {
				if (waitingTime - difft > 0)
					sleep((long) (waitingTime - difft));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		end();
	}

	/**
	 * when forcejoin is enabled the Thread forces to join in "forceTime" millis
	 * (default: 1000). if it is disabled it joins when its finished.
	 * 
	 * @param forcejoin
	 *            time to live after ending
	 */
	public void setForcejoin(boolean forcejoin) {
		this.forcejoin = forcejoin;
	}

	/**
	 * time to live when stopped.
	 * 
	 * @param forcetime
	 */
	public void setForceTime(int forcetime) {
		forceTime = forcetime;
	}

	/**
	 * @return time to live when stopped.
	 */
	public int getForceTime() {
		return forceTime;
	}

	/**
	 * time to live when stopped. every new Job has this forcetime by default.
	 * you can also change forcetime for single (existing) jobs.
	 * 
	 * @param forcetime
	 */
	public static void setDefaultForceTime(int forcetime) {
		defaultforceTime = forcetime;
	}

	/**
	 * every new Job has this forcetime by default. you can also change
	 * forcetime for single (existing) jobs.
	 * 
	 * @return default time to live when stopped.
	 */
	public static int getDefaultForceTime() {
		return defaultforceTime;
	}

	/**
	 * @return if the Job forces to join its Thread after stopping.
	 */
	public boolean getForcejoin() {
		return forcejoin;
	}

	/**
	 * false when paused if you want to check if its finished call isFinished()
	 * 
	 * @return if this Job is running
	 */
	public boolean isRunning() {
		if (!pause)
			return running;
		else
			return false;
	}

	/**
	 * pauses the Job when Routine is finished
	 */
	public void pause() {
		this.pause = true;
	}

	/**
	 * pauses or resumes the Job
	 * 
	 * @param pause
	 */
	public void pause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * resumes the Job
	 */
	public void unpause() {
		pause = false;
	}

	/**
	 * @return if this Job is paused
	 */
	public boolean isPaused() {
		return pause;
	}

	/**
	 * stops the Job
	 */
	public void finish() {
		o.println("Job " + getName() + " is finished!");
		running = false;
		pause = false;
	}

	public boolean isFinished() {
		return !running;
	}

	/**
	 * renames the Job
	 * 
	 * @param name
	 */
	public void rename(String name) {
		setName(name);
	}

	private void end() {
		o.println("Job " + getName() + " has ended and will be joined.");
		
		try {
			if (forcejoin) {
				join(forceTime);
			} else {
				join();	
			}
		} catch (InterruptedException e) {
			System.err.println("JobInterrupted: could not join " + getName());
			e.printStackTrace();
		}
		dead = true;
	}

	public boolean isDead() {
		return dead;
	}
}
