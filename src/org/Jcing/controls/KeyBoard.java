package org.Jcing.controls;

import java.util.HashMap;
import java.util.Iterator;

public class KeyBoard {

	public static HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>();

	public void press(int keycode) {
//		System.out.print(keycode);
		keys.put(keycode, true);
	}

	public void release(int keycode) {
		keys.put(keycode, false);
	}

	public KeyBoard() {
		keys = new HashMap<Integer, Boolean>();
	}

	public Iterator<Integer> iterator() {
		return keys.keySet().iterator();
	}

	public boolean pressed(int keycode) {
	
		if (keys.containsKey(keycode)) {
			return keys.get(keycode);
		}
		return false;
	}

	public boolean pressed() {
		return !keys.isEmpty();
	}
}
