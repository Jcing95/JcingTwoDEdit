package org.Jcing.job;

import java.util.ArrayList;

public class JobManager {

	private ArrayList<Job> jobs;
	
	//TODO: rework completely.. e.g. Clock + task

	/**
	 * A JobManager manages a bunch of Jobs. it can add Jobs to its list and
	 * run, pause and stop them. the Jobs can be ran, paused or stopped all at
	 * once or one by one (when job name is given).
	 */
	public JobManager() {
		jobs = new ArrayList<Job>();
	}

	/**
	 * adds Job to managed List.
	 * 
	 * @param job
	 *            job to be added.
	 */
	public void addJob(Job job) {
		if (job == null) {
			System.err.println("NO NULL JOBS PLEASE - cannot add a nulljob");
		} else {
			jobs.add(job);
		}
	}

	/**
	 * adds new Job to managed List.
	 * 
	 * @param routine
	 *            Routine of Job
	 * @param tps
	 *            Ticks per Second
	 * @param name
	 *            Name of Job
	 */
	public void addJob(Runnable routine, int tps, String name) {
		jobs.add(new Job(routine, tps, name));
	}

	/**
	 * starts given Job
	 * 
	 * @param name
	 *            name of the Job
	 */
	public void startJob(String name) {
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getName().equals(name)) {
				jobs.get(i).start();
			}
		}
	}

	/**
	 * starts all Jobs in the List.
	 */
	public void startJobs() {
		for (int i = 0; i < jobs.size(); i++) {
			jobs.get(i).start();
		}
	}

	/**
	 * pauses given Job
	 * 
	 * @param name
	 *            name of the Job
	 */
	public void pauseJob(String name) {
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getName().equals(name)) {
				jobs.get(i).pause();
			}
		}
	}

	/**
	 * pauses all Jobs
	 */
	public void pauseJobs() {
		for (int i = 0; i < jobs.size(); i++) {
			jobs.get(i).pause();
		}
	}

	/**
	 * resumes given Job
	 * 
	 * @param name
	 *            name of the Job
	 */
	public void resumeJob(String name) {
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getName().equals(name)) {
				jobs.get(i).unpause();
			}
		}
	}

	/**
	 * resumes all Jobs
	 */
	public void resumeJobs() {
		for (int i = 0; i < jobs.size(); i++) {
			jobs.get(i).unpause();
		}
	}

	/**
	 * stops given Job
	 * 
	 * @param name
	 *            name of the Job
	 */
	public void stopJob(String name) {
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getName().equals(name)) {
				jobs.get(i).finish();
			}
		}
	}

	/**
	 * @return the number of running Jobs managed by this JobManager
	 */
	public int runningJobs() {
		int runningJobs = 0;
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isRunning()) {
				runningJobs++;
			}
		}
		return runningJobs;
	}

	/**
	 * @return ArrayList<Job> of all running Jobs
	 */
	public ArrayList<Job> getRunningJobs() {
		ArrayList<Job> runningJobs = new ArrayList<Job>();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isRunning()) {
				runningJobs.add(jobs.get(i));
			}
		}
		return runningJobs;
	}

	/**
	 * @return the number of paused Jobs managed by this JobManager
	 */
	public int pausedJobs() {
		int pausedJobs = 0;
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isPaused()) {
				pausedJobs++;
			}
		}
		return pausedJobs;
	}

	/**
	 * @return ArrayList<Job> of all paused Jobs
	 */
	public ArrayList<Job> getPausedJobs() {
		ArrayList<Job> pausedJobs = new ArrayList<Job>();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isPaused()) {
				pausedJobs.add(jobs.get(i));
			}
		}
		return pausedJobs;
	}

	/**
	 * @return the number of running Jobs managed by this JobManager
	 */
	public int finishedJobs() {
		int finishedJobs = 0;
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isFinished()) {
				finishedJobs++;
			}
		}
		return finishedJobs;
	}

	/**
	 * @return ArrayList<Job> of all running Jobs
	 */
	public ArrayList<Job> getfinishedJobs() {
		ArrayList<Job> finishedJobs = new ArrayList<Job>();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isFinished()) {
				finishedJobs.add(jobs.get(i));
			}
		}
		return finishedJobs;
	}

	/**
	 * stops all Jobs (not recommended)
	 */
	public void finishAll() {
		for (int i = 0; i < jobs.size(); i++) {
			jobs.get(i).finish();
		}
	}

}
