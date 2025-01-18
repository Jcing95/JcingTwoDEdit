package de.jcing.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.jcing.graphics.Resizable;

public class Pane extends Clickable implements JComponent, Paintable, Resizable {

	public static final Color DEFAULTCOLOR = new Color(10, 150, 100, 100);

	protected Resizer resizer;
	protected BufferedImage img;
	protected ArrayList<Paintable> paintables;

	protected Color color;

	public Pane(int x, int y, int w, int h) {
		super(x, y);
		setSize(w, h);
		paintables = new ArrayList<Paintable>();
		color = DEFAULTCOLOR;
	}

	public void render() {
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < paintables.size(); i++) {
			paintables.get(i).paint(img.getGraphics());
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void paint(Graphics g) {
		render();
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.drawImage(img, x, y, null);
		g.setColor(Line.DEFAULTCOLOR);
		g.drawRect(x, y, w, h);
	}

	@Override
	public void hover(int x, int y) {
		super.hover(x, y);
		if (contains(x, y)) {
			for (int i = 0; i < paintables.size(); i++) {
				if (paintables.get(i).isClickable())
					((Clickable) paintables.get(i)).hover(x - this.x, y - this.y);
			}
		}
	}

	@Override
	public void click(boolean clicked, int x, int y) {
		super.click(clicked, x, y);
		if (this.clicked && !clicked || contains(x, y)) {
			for (int i = 0; i < paintables.size(); i++) {
				if (paintables.get(i).isClickable())
					((Clickable) paintables.get(i)).click(clicked, x - this.x, y - this.y);
			}
		}
	}

	public void add(Paintable paintable) {
		paintables.add(paintable);
	}

	public Paintable get(int i) {
		return paintables.get(i);
	}

	public int paintablesSize() {
		return paintables.size();
	}

	public void setResizable(int width, int height, int style) {
		resizer = new Resizer(x, y, w, h, width, height);
		resizer.setStyle(style);
	}

	public void resize(int w, int h) {
		if (resizer != null) {
			Rectangle newsize = resizer.resize(w, h);
			x = newsize.x;
			y = newsize.y;
			this.w = newsize.width;
			this.h = newsize.height;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int width) {
		this.w = width;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int height) {
		this.h = height;
	}

	public boolean contains(int x, int y) {
		return new Rectangle(this.x, this.y, w, h).contains(x, y);
	}

}
