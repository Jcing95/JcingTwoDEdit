package org.Jcing.graphics;

public interface Resizable {
	
	public void resize(int w, int h);
	
	public void setResizable(int w, int h, int style);
	
	/**
	 * Don't change width at resize, height changed at "normal"
	 */
	public static final int FIXEDWIDTH = 0;

	/**
	 * Don't change height at resize, width changed at "normal"
	 */
	public static final int FIXEDHEIGHT = 1;

	/**
	 * Percentage change of width to refWidth, height will be changed "normal"
	 */
	public static final int PERCENTAGEWIDTH = 2;

	/**
	 * Percentage change of height to refHeight, width will be changed "normal"
	 */
	public static final int PERCENTAGEHEIGHT = 3;

	/**
	 * Percentage change of everything
	 */
	public static final int PERCENTAGE = 4;

	/**
	 * Everything is resized "normal"
	 */
	public static final int NORMAL = 5;

	/**
	 * Percentage change of everything but fixed x.
	 */
	public static final int PERCENTAGEFIXEDX = 6;

	/**
	 * Percentage change of everything but fixed y.
	 */
	public static final int PERCENTAGEFIXEDY = 7;

	/**
	 * Percentage change of size but fixed Position.
	 */
	public static final int PERCENTAGEFIXEDXANDY = 8;

	public static final int PERCENTAGEX = 10;
	public static final int PERCENTAGEY = 11;
	
	/**
	 * use when component is located at the TOP-RIGHT corner.
	 * at resize x will move delta Ref-width, y will stay same, height will change delta Ref-height, width will stay same.
	 */
	public static final int TOPRIGHTBOUND = 100;
	
	
	/**
	 * use when component is located at the TOP-LEFT corner.
	 * at resize x will stay same, y will stay same, height will change delta Ref-height, width will stay same.
	 */
	public static final int TOPLEFTBOUND = 110;
	
	/**
	 * use when component is located at the BOTTOM-RIGHT corner.
	 * at resize only Position will change.
	 */
	public static final int BOTTOMRIGHTBOUND = 0;
}
