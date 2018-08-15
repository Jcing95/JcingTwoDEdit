package org.Jcing.controls;

import org.Jcing.Essentials.OutputStreamController;

/**
 * A <b>Binding</b> adds itself automatically to the static
 * <i>BindingManager</i> of <b>InputManager</b>. if you use a
 * <b>InputManager</b> and any key is pressed, if a corresponding <b>Binding</b>
 * is initialized, it fires until released. If <b>Oneclick</b> is true, a
 * Binding only fires once at press event!
 * 
 * @author Jcing
 *
 */
public class KeyBinding {
	
	//TODO: implement Listeners. OnPress, OnRelease, OnType, OnHold or so.

	protected Executable binded;
	protected int keycode;
	protected boolean pressed;
	private static OutputStreamController o = new OutputStreamController("Binding",true);

	private boolean oneclick;

	/**
	 * 
	 * @param keyCode
	 *            to bind (KeyEvent.VK_XYZ)
	 * @param toBind
	 *            Executable to fire while bound <b>keyCode</b> is pressed.
	 */
	public KeyBinding(int keyCode, Executable toBind) {
		init(keyCode, toBind, false);
	}

	/**
	 * 
	 * @param keyCode
	 *            to bind (KeyEvent.VK_XYZ)
	 * @param toBind
	 *            Executable to fire when bound <b>keyCode</b> is pressed.
	 * @param oneClick
	 *            <i>true</i> if this <b>Binding</b> should only fire once per
	 *            press-event. <i>false> if this <b>Binding</b> should fire
	 *            while bound <b>keyCode</b> is pressed.
	 */
	public KeyBinding(int keyCode, Executable toBind, boolean oneClick) {
		init(keyCode, toBind, oneClick);
	}

	//TODO: keymap + default keymap in KeyMap class.
	
	private void init(int keyCode, Executable toBind, boolean oneClick) {
		binded = toBind;
		this.oneclick = oneClick;
		this.keycode = keyCode;
		pressed = false;
		InputManager.bm.addBinding(this);
		o.println("registered Binding: " + keyCode);
	}

	/**
	 * @return if keycode equals <b>obj</b>
	 */
	@Override
	public boolean equals(Object obj) {
		return new Integer(keycode).equals(obj);
	}

	/**
	 * called by <i>InputManager.<b>BindingManager</b></i> frequently while
	 * bound <b>keyCode</b> is pressed.</br>
	 * Fires bound <b>Executable</b> if <b>oneClick</b> == <i>false</i>.
	 */
	public void tick() {
		if (!oneclick) {
			binded.execute(this);
			// System.out.print(" t" + keycode);
		}
	}

	/**
	 * called by <i>InputManager.<b>BindingManager</b></i> when bound
	 * <b>keyCode</b> is pressed.</br>
	 * Fires bound <b>Executable</b> if <b>oneClick</b> == <i>false</i>.
	 */
	public void press() {
		o.println("p" + keycode);
		pressed = true;
		if (oneclick) {
			binded.execute(this);
		}
		// bm.addBinding(this);
	}

	/**
	 * called by <i>InptuManager.<b>BindingManager</b></i> when bound
	 * <b>keyCode</b> is released.</br>
	 * Fires bound <b>Executable</b> if <b>oneClick</b> == <i>false</i>.
	 */
	public void release() {
		pressed = false;
		if (!oneclick)
			binded.execute(this);
	}

	/**
	 * removes this <b>Binding</b> from
	 * <i>InputManager.<b>BindingManager</b></i>
	 */
	public void remove() {
		InputManager.bm.removeBinding(this);
	}

	public int getKeyCode() {
		return keycode;
	}

	public boolean isOneClick() {
		return oneclick;
	}

	public boolean isPressed() {
		return pressed;
	}

}
