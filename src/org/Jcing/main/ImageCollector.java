package org.Jcing.main;

import java.util.ArrayList;

import org.Jcing.graphics.JCImage;

public class ImageCollector {

	
	//TODO: hashmap? general revision of ImageCollector.
	private ArrayList<ArrayList<JCImage>> imgs = new ArrayList<ArrayList<JCImage>>();

	public JCImage get(int type, int imgID) {
		// TODO: throw und so shit
		if (imgs.get(type) == null || imgs.get(type).size() <= imgID)
			return null;
		return imgs.get(type).get(imgID);
	}

	public JCImage get(CollectedImage img) {
		return img.get(this);
	}

	public CollectedImage find(JCImage img) {
		for (int i = 0; i < imgs.size(); i++) {
			for (int j = 0; j < imgs.get(i).size(); j++) {
				if (imgs.get(i).get(j).getImg().equals(img.getImg())) {
					return new CollectedImage(i, j);
				}
			}
		}
		return null;
	}

	public int addList() {
		imgs.add(new ArrayList<JCImage>());
		return imgs.size() - 1;
	}

	public int add(int list, String path) {
		imgs.get(list).add(new JCImage(path));
		return imgs.get(list).size() - 1;
	}
	
	public void remove(int list, int id){
		imgs.get(list).remove(id);
	}
	
	public void remove(CollectedImage img){
		imgs.get(img.getList()).remove(img.getId());
	}
	
	public int add(int list, JCImage img) {
		imgs.get(list).add(img);
		return imgs.get(list).size() - 1;
	}

	public void set(int list, int id, JCImage img) {
		imgs.get(list).set(id, img);
	}

	public int addList(ArrayList<JCImage> toadd) {
		imgs.add(toadd);
		return imgs.size() - 1;
	}

	public boolean isAvailable(int list, int id) {
		if (imgs.size() <= list) {
			return false;
		}
		if (imgs.get(list).size() <= id) {
			return false;
		}
		return true;
	}

	public int size() {
		return imgs.size();
	}

	public int size(int list) {
		return imgs.get(list).size();
	}
}
