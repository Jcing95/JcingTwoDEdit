package org.Jcing.job;

public class Routine{
	
	
	protected Job job;
	
	protected Runnable run;
	
	
	public Routine(Runnable run) {
		this.run = run;
	}
	
	public Runnable runners() {
		return run;
	}

	/**
	 * is called when Job is initializing. use to end the Job from the Routine
	 * if necessary
	 * 
	 * @param job
	 *            job which will run this Routine.
	 */
	public void setJob(Job job) {
		this.job = job;
	}

	public void finish() {
		job.finish();
	}

	public void run() {
		run.run();
	}

}
