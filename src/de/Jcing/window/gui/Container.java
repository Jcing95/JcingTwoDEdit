package de.Jcing.window.gui;

import java.awt.Graphics2D;
import java.util.HashSet;

public class Container extends Component {
	
	protected HashSet<Component> subComponents;
	
	public Container(int x, int y, int w, int h) {
		super(x,y,w,h);		

	}
	
	public void addComponent(Component c) {
		subComponents.add(c);
		c.setParent(this);
	}
	
	@Override
	public void paint(Graphics2D g) {
		for (Component c: subComponents)
			c.draw(g);
	}

}
