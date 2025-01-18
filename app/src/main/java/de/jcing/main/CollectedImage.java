package de.jcing.main;

import java.io.Serializable;

import de.jcing.graphics.JCImage;

public class CollectedImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8747880623589832283L;
	
	private int list, id;

	public CollectedImage(int list, int id) {
		this.list = list;
		this.id = id;
	}

	public void setList(int list) {
		this.list = list;
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public int getList() {
		return list;
	}
	
	public boolean equals(Object obj){
		return ((CollectedImage)obj).id == id && ((CollectedImage)obj).list == list;
	}

	public int getId() {
		return id;
	}

	public JCImage get(ImageCollector collector) {
		return collector.get(list, id);
	}
	
	public void set(ImageCollector collector, JCImage img){
		collector.set(list, id, img);
	}
	
	public boolean isAvailable(ImageCollector collector){
		return collector.isAvailable(list, id);
	}
}
