package org.Jcing.GUI;

import java.awt.Color;

import org.Jcing.job.Job;

public class ScrollPane extends Pane {

	public static final Color DEFAULTCOLOR = new Color(200, 20, 70, 100);
	private Job job;
	private int yOffset;

	private Job scroll;
	private int toMove;

	public ScrollPane(int x, int y, int width, int height) {
		super(x, y, width, height);
		color = DEFAULTCOLOR;
		scroll = new Job(routine, 40, "scroll");
		setyOffset(0);
//		scroll.setForceTime(0);
	}

	@Override
	public void mouseWheelMoved(int movement) {
		if (hovered) {
			toMove = movement*10;
			if (scroll.isDead()){
//				scroll = new Job(this,40,"scroll");
				scroll.start();
			}
			job.pause(false);
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
			job.pause(true);
		}
	};

	public void setJob(Job job) {
		this.job = job;
	}

	public int getyOffset() {
		return yOffset;
	}

	private void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

}
