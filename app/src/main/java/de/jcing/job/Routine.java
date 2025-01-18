package de.jcing.job;

public interface Routine {

	/**
	 * is called by the Job when time to pass has passed
	 */
	public void go();

	/**
	 * is called when Job is initializing. use to end the Job from the Routine
	 * if necessary
	 * 
	 * @param job
	 *            job which will run this Routine.
	 */
	public void setJob(Job job);

}
