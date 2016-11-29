package org.Jcing.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.Jcing.Essentials.OutputStreamController;
import org.Jcing.graphics.JCImage;

public class FolderLoader {

	private static OutputStreamController o = new OutputStreamController("FolderLoader", true);

	/**
	 * loads all n.pngs in a Folder. used primarily for animations.
	 * 
	 * @param path
	 * @return
	 */
	public static ArrayList<JCImage> numericLoad(final String path) {
		// TODO: make sure that every file is <Integer>.png
		ArrayList<JCImage> imgs = new ArrayList<JCImage>();
		File location = new File(path);
		if(!location.isDirectory()){
			imgs.add(new JCImage(path));
			return imgs;
		}
		String[] files = location.list();
		Arrays.sort(files, new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (new File(path + "/" + o1).isDirectory() || new File(path + "/" + o2).isDirectory()) {
					return 0;
				}
				return Integer.valueOf(o1.substring(0, o1.indexOf(".")))
						.compareTo(Integer.valueOf(o2.substring(0, o2.indexOf("."))));
			}
		});
		for (int i = 0; i < files.length; i++) {
			if (!new File(path + "/" + files[i]).isDirectory()) {
				o.println("numeric Load: " + files[i]);
				imgs.add(new JCImage(path + "/" + files[i]));
			}
		}
		return imgs;
	}

	private static boolean indexedLoad = false;

	/**
	 * loads all Images in a Folder (and subfolders) and indexes them. Not yet
	 * indexed Images will be added last.
	 * 
	 * @param path
	 * @param subFolders
	 * @return
	 */
	public static ArrayList<JCImage> indexedLoad(String path, boolean subFolders) {
		ArrayList<String> filepaths;
		if (new File(path + "/loadata.Jdta").exists()) {
			filepaths = FileLoader.LoadArrayList(path + "/loadata.Jdta");
		} else {
			filepaths = new ArrayList<String>();
		}
		indexedLoad = false;
		ArrayList<JCImage> imgs = privIndexLoad(new ArrayList<JCImage>(), filepaths, path, subFolders);
		FileLoader.saveArrayList(path + "/loadata.Jdta", filepaths);
		indexedLoad = false;
		return imgs;
	}

	private static ArrayList<JCImage> privIndexLoad(ArrayList<JCImage> imgs, ArrayList<String> filepaths, String path,
			boolean subFolders) {
		File location = new File(path);
		if (!location.isDirectory()) {
			System.err.println("IMAGE HANDLER ERROR: path is no directory!");
		} else {

			if (!indexedLoad) {
				indexedLoad = true;
				for (int i = 0; i < filepaths.size(); i++) {
					File file = new File(filepaths.get(i));
					if (file.exists()) {
						o.println("Indexed img: ");
						if (file.isDirectory()) {
							imgs.add(new JCImage(file.getPath(), true));
						} else {
							imgs.add(new JCImage(file.getPath()));
						}
					} else {
						o.println("Missing: " + file.getPath());
						imgs.add(new JCImage("gfx/tilesets/MISSINGTILE.png"));
					}

				}
			}
			String[] files = location.list();
			for (int i = 0; i < files.length; i++) {
				File file = new File(path + "/" + files[i]);
				if (filepaths.contains(file.getPath())) {
					// o.println("Indexed img: ");
					// imgs.add(new JCImage(file.getPath()));
				} else {
					o.println(file.getName());

					// o.println(file.getPath() + " != " + path +
					// "/loadata.Jdta");
					//					if (file.isDirectory()) {
					//						if (subFolders) {
					//							o.println("found folder - including it!");
					//							imgs = privIndexLoad(imgs, filepaths, file.getPath(), subFolders);
					//						}
					//					} else 
					if (file.isDirectory()
							|| file.getName().substring(file.getName().lastIndexOf('.')).equals(".png")) {
						filepaths.add(file.getPath());
						o.println("Adding new file index: " + file.getPath());
						if (file.isDirectory()) {
							imgs.add(new JCImage(file.getPath(), true));
						} else {
							imgs.add(new JCImage(file.getPath()));
						}

						o.println("loaded file!");
					}

				}
			}
		}
		return imgs;
	}
}
