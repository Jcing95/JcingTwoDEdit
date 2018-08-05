package org.Jcing.controls;

import java.util.HashMap;
import java.util.Iterator;

import org.Jcing.job.Job;
import org.Jcing.job.Routine;

/**
 * <b>BindingManager</b> is a static Object in InputManager. It can also be used
 * separately. It is a Routine called by a private Job with a given frequency. A
 * <b>BindingManager</b> ticks all registered Bindings and calls their
 * press/release methods.
 * 
 * @author Jcing
 *
 */
public class BindingManager {

	private static HashMap<Integer, Binding> bindings = new HashMap<Integer, Binding>();
	private Job job;

	/**
	 * Initializes a <b>BindingManager</b> with a tick-frequency of <b>TPS</b>
	 * 
	 * @param TPS
	 *            is the tick-frequency.
	 */
	public BindingManager(int TPS) {
		bindings = new HashMap<Integer, Binding>();
		job = new Job(routine, TPS, "Binding Manager");
		job.start();
	}

	/**
	 * Called at <i>InputManager.<b>BindingManager</b></i> from new
	 * <b>Binding</b>.
	 * 
	 * @param binding
	 *            binding to add.
	 */
	public synchronized void addBinding(Binding binding) {
		bindings.put(binding.getKeyCode(), binding);
		job.pause(false);
	}

	public Binding getBinding(int keyCode) {
		return bindings.get(keyCode);
	}

	public synchronized void removeBinding(Binding binding) {
		bindings.remove(binding.getKeyCode());
	}

	public void press(int keyCode) {
		if (bindings.containsKey(keyCode) && !bindings.get(keyCode).isPressed())
			bindings.get(keyCode).press();
	}

	public void release(int keyCode) {
		if (bindings.containsKey(keyCode))
			bindings.get(keyCode).release();
	}
	
	
	//TODO: check here
	private Routine routine = new Routine(() -> {
		if (!bindings.isEmpty()) {
			Iterator<Integer> i = iterator();
			while (i.hasNext()) {
				Integer b = i.next();
				if (InputManager.keyboard.pressed(b)) {
					bindings.get(b).tick();
				}
			}
		} else {
			job.pause(true);
		}
	});

	/**
	 * @return Iterator of the <i>keySet</i> (bound KeyCodes)
	 */
	public Iterator<Integer> iterator() {
		return bindings.keySet().iterator();
	}

	public void setJob(Job job) {
		this.job = job;
	}
}
