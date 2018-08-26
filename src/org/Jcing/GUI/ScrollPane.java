package org.Jcing.GUI;

import java.awt.Color;

import de.Jcing.tasks.Task;

public class ScrollPane extends Pane {

	public static final Color DEFAULTCOLOR = new Color(200, 20, 70, 100);
	private int yOffset;

	private Task scroll;
	private int toMove;

	public ScrollPane(int x, int y, int width, int height) {
		super(x, y, width, height);
		color = DEFAULTCOLOR;
		scroll = new Task(routine, 40);
		setyOffset(0);
//		scroll.setForceTime(0);
	}

	@Override
	public void mouseWheelMoved(int movement) {
		if (hovered) {
			toMove = movement*10;
			if (scroll.isRunning()){
//				scroll = new Job(this,40,"scroll");
				scroll.start();
			}
			scroll.pause(false);
//			System.out.println(scroll.isDead());
		}
	}

	private Runnable routine = () -> {
		for (int i = 0; i < paintables.size(); i++) {
			if (paintables.get(i) != null) {
				paintables.get(i).setY(paintables.get(i).getY() - toMove);
			}
		}
		setyOffset(getyOffset() - toMove);
		boolean minus = true;
		if (toMove > 0)
			minus = false;
		
		if (minus)
			toMove++;
		else
			toMove--;

		if (toMove == 0) {
			scroll.pause(true);
		}
	};


	public int getyOffset() {
		return yOffset;
	}

	private void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

}
