package org.Jcing.graphics;

import java.awt.image.BufferedImage;

public class JPic {
	private int w,h;
	
	private int r[], g[], b[], a[];
	
	public JPic(BufferedImage img){
		w = img.getWidth();
		h = img.getHeight();
		
	}
}
