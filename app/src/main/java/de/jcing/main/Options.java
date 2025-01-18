package de.jcing.main;

import java.io.File;
import java.io.Serializable;

import de.jcing.files.FileLoader;

public class Options implements Serializable {

	/**
	 * serial Version UID for saving.
	 */
	private static final long serialVersionUID = -6962347418424785480L;

	private String path;

	public String getPath() {
		return path;
	}

	public Options(String path) {
		this.path = path;
	}

	public static Options load(String path) {
		if (new File(path).exists()) {
			if (FileLoader.LoadFile(path) != null) {
				return (Options) FileLoader.LoadFile(path);
			}
			if (FileLoader.LoadFileInJar(path) != null) {
				return (Options) FileLoader.LoadFileInJar(path);
			} else
				return new Options(path);
		} else
			return new Options(path);
	}

	public void save() {
		FileLoader.saveFile(path, this);
	}
	
	
	//// OPTION FLAGS:

	public boolean fullscreen = false;
	public boolean creatorWindowed = true;
	public boolean showGrid = true;
	public boolean showCollision = true;
	public boolean showEntitySizes = false;
	public boolean showEntityFootprints = false;
	public boolean replaceTile = true;

}
