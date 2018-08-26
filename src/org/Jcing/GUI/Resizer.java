package org.Jcing.GUI;

import java.awt.Rectangle;

import org.Jcing.Essentials.OutputStreamController;

public class Resizer {

	private int x, y, w, h;
	private int dx, dy, dw, dh;
	private int refw, refh;
	private Rectangle rect;
	private int style;

	private static OutputStreamController o = new OutputStreamController("Resizer", true);
	
	public Resizer(Rectangle rect, int refw, int refh) {
		this.rect = rect;
		x = rect.x;
		y = rect.y;
		w = rect.width;
		h = rect.height;
		dx = x;
		dy = y;
		dw = w;
		dh = h;
		this.refh = refh;
		this.refw = refw;
	}

	public Resizer(int x, int y, int w, int h, int refw, int refh) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		dx = x;
		dy = y;
		dw = w;
		dh = h;
		rect = new Rectangle(x, y, w, h);
		this.refh = refh;
		this.refw = refw;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public Rectangle getRect() {
		rect = new Rectangle(x, y, w, h);
		return rect;
	}

	public Rectangle resize(int w, int h) {
		switch (style) {
		case Resizable.TOPRIGHTBOUND:
			// h = (int) (dh * (double)h / refh);
			o.println(x + " - " + (refw-w) + " = " +  (x-(refw-w)) + " ref: " + refw + " w: " + w);
			this.h -= (refh - h);
			this.x -= (refw - w);
			refw = w;
			refh = h;
//			o.println("resizing toprightbound");
			return getRect();
		case Resizable.TOPLEFTBOUND:
			this.h -= (refh - h);
			refh = h;
			return getRect();
		case Resizable.BOTTOMRIGHTBOUND:
			this.y -= (refh - h);
			this.x -= (refw - w);
			refw = w;
			refh = h;
			return getRect();
		}
//		o.println("FUCK WHY NO RESIZING?");
		return null;
	}

}
