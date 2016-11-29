package org.Jcing.Essentials;

import java.io.Serializable;

public class OutputStreamController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4434470819294817505L;

	private String name;
	private boolean active;

	public OutputStreamController(String name, boolean disabled) {
		this.name = name;
		this.active = !disabled;
	}

	public void println(String output) {
		if (active) {
			System.out.println(name + ": " + output);
		}
	}

	public void print(String output) {
		if (active) {
			System.out.print(output);
		}
	}

}
