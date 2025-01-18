package de.jcing.files;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.jcing.essentials.OutputStreamController;

public class FileLoader {

	private static FileInputStream fis;
	private static ObjectInputStream ois;

	private static FileOutputStream fos;
	private static ObjectOutputStream oos;
	
	private static OutputStreamController o = new OutputStreamController("FileLoader", true);
	
	private static URL getURL(String path){
		URL file = FileLoader.class.getClassLoader().getResource(path);
		System.out.println("JAR: " + path + " to URL: " + file);
		o.println("JAR: " + path + " to URL: " + file);
		return file;
	}
	
	public static BufferedImage LoadImage(String path) {
		File file = new File(path);
		BufferedImage img = null;
		try {
			if(file.canRead()){
				o.println("loading: " + file);
				img = ImageIO.read(file);
			}else{
				o.println("loading from jar: " + getURL(path));
				System.out.println("loading from jar: " + getURL(path));
				img = ImageIO.read(getURL(path));
			}
		} catch (IOException e) {
			System.err.println("Could not Load Image: " + path);
			e.printStackTrace();
			return null;
		}
		return img;
	}

	public static void saveImage(String path, RenderedImage img) {
		File file = new File(path);
		file.getParentFile().mkdirs();
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			System.err.println("Could not save img at: " + path);
			e.printStackTrace();
		}
	}

	public static Object LoadFile(String path) {
		File file = new File(path);
		Object obj = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load Object at: " + path);
			e.printStackTrace();
			return null;
		}
		return obj;
	}
	
	public static Object LoadFileInJar(String path) {
		Object obj = null;
		try {
			ois = new ObjectInputStream(new FileLoader().getClass().getClassLoader().getResourceAsStream(path));
			obj = ois.readObject();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load injar Object at: " + path);
			e.printStackTrace();
			return null;
		}
		return obj;
	}

	@SuppressWarnings({ "rawtypes" })
	public static ArrayList LoadArrayList(String path) {
		//TODO: Redundant da ArrayList entgegen meines urspr�nglichen Glaubens serializable.
		//TODO: genau wie saveArrayList.
		File file = new File(path);
//		Object obj[] = null;
		ArrayList al = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			al = (ArrayList) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("Could not save Object at: " + path);
			e.printStackTrace();
			return null;
		}
//		ArrayList al = new ArrayList();
//		for (int i = 0; i < obj.length; i++) {
//			al.add(obj[i]);
//		}
		return al;
	}

	public static synchronized void saveFile(String path, Serializable toSave) {
		File file = new File(path);
		if (file.getParentFile() != null)
			file.getParentFile().mkdirs();
		if(file.exists()){
			file.renameTo(new File(file.getPath()+".BACKUP"));
		}
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(toSave);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found exception. Should not happen!!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not save " + toSave.getClass().getName() + " at: " + path);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static synchronized void saveArrayList(String path, ArrayList arrayList) {
//		Object toSave[] = new Object[arrayList.size()];
//		for (int i = 0; i < toSave.length; i++) {
//			toSave[i] = arrayList.get(i);
//		}

		File file = new File(path);
		if (file.getParentFile() != null)
			file.getParentFile().mkdirs();
		if(file.exists()){
			file.renameTo(new File(file.getPath()+".BACKUP"));
		}
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(arrayList);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found exception when saving. Should not happen!!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not save " + arrayList.getClass().getName() + " at: " + path);
			e.printStackTrace();
		}
	}

}
