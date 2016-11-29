package org.Jcing.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.Jcing.Essentials.OutputStreamController;

public class ImageManager {

	private ArrayList<BufferedImage> imgs;
	private static OutputStreamController o = new OutputStreamController("ImageManager", true);
	
	public ImageManager(String path){
		imgs = new ArrayList<BufferedImage>();
		load(path);
	}
	
	public BufferedImage get(int i){
		return imgs.get(i);
	}
	
	public Iterator<BufferedImage> iterator(){
		return imgs.iterator();
	}
	
	
	//TODO: load single folder
	
	public void load(String path){
		File location = new File(path);
		if(!location.isDirectory()){
			System.err.println("IMAGE HANDLER ERROR: path is no directory!");
		}else{
			String[] files = location.list();
			for (int i = 0; i < files.length; i++) {
				File file = new File(path+ "/" + files[i]);
				if(file.getAbsoluteFile().isDirectory()){
					o.println("found folder - including it!");
					load(file.getAbsolutePath());
				}else{
					try {
						imgs.add(ImageIO.read(file.getAbsoluteFile()));
						o.println("loaded file!");
					} catch (IOException e) {
						System.err.println("IMAGE HANDLER ERROR: could not load: " + file.getAbsolutePath());
						e.printStackTrace();
					}
				}
			}
		}
	}
}
