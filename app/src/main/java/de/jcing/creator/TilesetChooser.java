package de.jcing.creator;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TilesetChooser extends FileFilter{

	@Override
	  public boolean accept(File file) {
	    if (file.isDirectory()) {
	      return true;
	    }
	    int index = file.getName().lastIndexOf('.');
	    if (index < file.getName().length() - 1) {
	      String ending = file.getName().substring(index + 1).toLowerCase();
	      if (ending.equals("png")) {
	        return true;
	      }
	    }
	    return false;
	  }

	  @Override
	  public String getDescription() {
	    return "A Folder containing Tile-images";
	  }
	
	
}
