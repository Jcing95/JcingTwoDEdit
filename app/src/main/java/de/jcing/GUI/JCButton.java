package de.jcing.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import de.jcing.graphics.JCImage;

public class JCButton extends Clickable {

	private String label;
	private JCImage normalLeft, hoverLeft, clickLeft;
	private JCImage normal, hover, click;
	private JCImage normalRight, hoverRight, clickRight;
	private Color fontColor;

	BufferedImage buttonN, buttonH, buttonC;

	private int textX;

	public JCButton(int xPos, int yPos, String label) {
		super(xPos, yPos);
		hovered = false;
		clicked = false;
		this.label = label;
		normal = new JCImage("gfx/Button/normal.png");
		hover = new JCImage("gfx/Button/hover.png");
		click = new JCImage("gfx/Button/click.png");
		normalLeft = new JCImage("gfx/Button/normalLeft.png");
		hoverLeft = new JCImage("gfx/Button/hoverLeft.png");
		clickLeft = new JCImage("gfx/Button/clickLeft.png");
		normalRight = new JCImage("gfx/Button/normalRight.png");
		hoverRight = new JCImage("gfx/Button/hoverRight.png");
		clickRight = new JCImage("gfx/Button/clickRight.png");

		initButtons();

		fontColor = new Color(220, 220, 220);
		w = buttonN.getWidth();
		h = buttonN.getHeight();
		x = xPos;
		y = yPos;

		setSize(w, h);

	}

	protected void initButtons() {
		textX = normalLeft.getImg().getWidth();
		buttonN = new BufferedImage(
				normalLeft.getImg().getWidth() + normal.getImg().getGraphics().getFontMetrics().stringWidth(label)
						+ normalRight.getImg().getWidth(),
				normalLeft.getImg().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = buttonN.getGraphics();
		g.drawImage(normalLeft.getImg(), 0, 0, null);
		g.drawImage(normalRight.getImg(), buttonN.getWidth() - normalRight.getImg().getWidth(), 0, null);
		g.drawImage(normal.getImg().getScaledInstance(g.getFontMetrics().stringWidth(label),
				normal.getImg().getHeight(), Image.SCALE_SMOOTH), normalLeft.getImg().getWidth(), 0, null);

		buttonH = new BufferedImage(
				normalLeft.getImg().getWidth() + normal.getImg().getGraphics().getFontMetrics().stringWidth(label)
						+ normalRight.getImg().getWidth(),
				normalLeft.getImg().getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = buttonH.getGraphics();
		g.drawImage(hoverLeft.getImg(), 0, 0, null);
		g.drawImage(hoverRight.getImg(), buttonN.getWidth() - normalRight.getImg().getWidth(), 0, null);
		g.drawImage(hover.getImg().getScaledInstance(g.getFontMetrics().stringWidth(label), normal.getImg().getHeight(),
				Image.SCALE_SMOOTH), normalLeft.getImg().getWidth(), 0, null);

		buttonC = new BufferedImage(
				normalLeft.getImg().getWidth() + normal.getImg().getGraphics().getFontMetrics().stringWidth(label)
						+ normalRight.getImg().getWidth(),
				normalLeft.getImg().getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = buttonC.getGraphics();
		g.drawImage(clickLeft.getImg(), 0, 0, null);
		g.drawImage(clickRight.getImg(), buttonN.getWidth() - normalRight.getImg().getWidth(), 0, null);
		g.drawImage(click.getImg().getScaledInstance(g.getFontMetrics().stringWidth(label), normal.getImg().getHeight(),
				Image.SCALE_SMOOTH), normalLeft.getImg().getWidth(), 0, null);

		normal = null;
		normalLeft = null;
		normalRight = null;
		hover = null;
		hoverLeft = null;
		hoverRight = null;
		click = null;
		clickLeft = null;
		clickRight = null;
	}
	
	public void click(){
		cm.actionHappened(this);
	}
	
	public void paint(Graphics g) {
		if (clicked) {
			g.drawImage(buttonC, x, y, null);
		} else if (hovered) {
			g.drawImage(buttonH, x, y, null);
		} else {
			g.drawImage(buttonN, x, y, null);
		}
		g.setColor(fontColor);
		g.drawString(label, x + textX, y + getHeight() / 2 + g.getFontMetrics().getAscent() / 2);
	}

}
