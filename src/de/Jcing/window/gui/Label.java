package de.Jcing.window.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import de.Jcing.Main;

public class Label extends Component {
	
	public static final Color DEFAULTFONTCOLOR = Color.CYAN;
	
	protected String text;
	
	public Label(String text, int x, int y) {
		super(x,y,Main.getWindow().getFontMetrics().stringWidth(text), Main.getWindow().getFontMetrics().getHeight());
		setText(text);
	}
	
	public void setText(String text) {
		this.text = text;
		setWidth(Main.getWindow().getFontMetrics().stringWidth(text));
	}
	
	@Override
	public void prepareDraw(Graphics2D g) {
		g.setColor(DEFAULTFONTCOLOR);
	}
	
	@Override
	public void paint(Graphics2D g) {
		g.drawString(text, 0, 0);
	}
	
}
