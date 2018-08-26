package org.Jcing.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.Jcing.files.FileLoader;
import org.Jcing.files.FolderLoader;

import de.Jcing.tasks.Task;

public class JCImage {

	private ArrayList<ArrayList<BufferedImage>> imgs;
	private int index, anim;
	private int fps;
	private Task task;
	private boolean animated;

	public static final int DEFAULTFPS = 12;

	public JCImage(String path) {
		imgs = new ArrayList<ArrayList<BufferedImage>>();
		imgs.add(new ArrayList<BufferedImage>());
		imgs.get(0).add(FileLoader.LoadImage(path));
		index = 0;
		anim = 0;
		animated = false;
	}

	public JCImage(int width, int height) {
		imgs = new ArrayList<ArrayList<BufferedImage>>();
		imgs.add(new ArrayList<BufferedImage>());
		imgs.get(0).add(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		index = 0;
		anim = 0;
		animated = false;
	}
	
	public JCImage(int width, int height, Color color) {
		imgs = new ArrayList<ArrayList<BufferedImage>>();
		imgs.add(new ArrayList<BufferedImage>());
		imgs.get(0).add(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		Graphics g = imgs.get(0).get(0).getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.dispose();
		index = 0;
		anim = 0;
		animated = false;
	}

	public JCImage(BufferedImage img) {
		imgs = new ArrayList<ArrayList<BufferedImage>>();
		imgs.add(new ArrayList<BufferedImage>());
		imgs.get(0).add(img);
		index = 0;
		anim = 0;
		animated = false;
	}

	public JCImage(String path, boolean animation) {
		if (animation) {
			ArrayList<JCImage> loaded = FolderLoader.numericLoad(path);
			imgs = new ArrayList<ArrayList<BufferedImage>>();
			imgs.add(new ArrayList<BufferedImage>());
			index = 0;
			anim = 0;
			fps = DEFAULTFPS;
			task = new Task(routine, fps);
			for (int i = 0; i < loaded.size(); i++) {
				imgs.get(0).add(loaded.get(i).getImg());
			}
			animated = true;
			task.start();
		} else {
			new JCImage(path);
		}
	}

	public void addAnimation(String path) {
		if (fps == 0) {
			fps = DEFAULTFPS;
		}
		if(task == null){
			task = new Task(routine, fps);
			task.start();
		}
		//		imgs.add(FolderLoader.numericLoad(path));
		ArrayList<JCImage> loaded = FolderLoader.numericLoad(path);
		imgs.add(new ArrayList<BufferedImage>());
		for (int i = 0; i < loaded.size(); i++) {
			imgs.get(imgs.size() - 1).add(loaded.get(i).getImg());
		}
		animated = true;
	}

	public void start() {
		task.start();
	}

	public void pause() {
		task.pause(true);
	}

	public BufferedImage getImg() {
		return imgs.get(anim).get(index);
	}

	public static BufferedImage getSub(BufferedImage img, int x, int y, int w, int h) {

		if (x > 0 && y > 0 && w < img.getWidth() && h < img.getHeight()) {
			return img.getSubimage(x, y, w, h);
		}

		BufferedImage frame = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = frame.getGraphics();

		g.drawImage(img, x, y, null);
		return frame;
	}

	public Graphics getGraphics() {
		return imgs.get(anim).get(index).getGraphics();
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
		task.setTps(fps);
	}

	public void setAnimation(int anim) {
		if (anim != this.anim) {
			this.index = 0;
			this.anim = anim;
		}
	}

	public int getAnimation() {
		return anim;
	}

	public boolean isAnimated() {
		return animated;
	}

	private Runnable routine = () -> {
		if (index >= imgs.get(anim).size() - 1) {
			index = 0;
		} else {
			index++;
		}
		//		index %= imgs.get(anim).size();
	};

	public void setJob(Task job) {
		this.task = job;
	}

}
