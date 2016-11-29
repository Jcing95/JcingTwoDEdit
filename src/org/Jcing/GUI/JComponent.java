package org.Jcing.GUI;

public interface JComponent {
	
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	
	public boolean contains(int x, int y);
	
//	public JComponent getComponent();
	public void setX(int x);
	public void setY(int y);
	public void setWidth(int w);
	public void setHeight(int h);
	
	
}
