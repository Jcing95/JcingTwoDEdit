package de.jcing.creator;

import de.jcing.GUI.Clickable;
import de.jcing.GUI.ClickableManager;
import de.jcing.main.Main;

public class CreatorActions implements ClickableManager{
	
	Creator c;
	Main m;
	
	public CreatorActions(Creator creator, Main main){
		c = creator;
		m = main;
	}

	public void actionHappened(Clickable e) {
		if(e == c.windowCreator){
			c.toggleWindow();
		}
		if(e == c.addChunks){
			m.getGame().getActiveLevel().toggleAddChunks();
			if(c.addChunks.isActive()){
				c.deleteChunks.setActive(false);
			}
		}
		if(e == c.deleteChunks){
			m.getGame().getActiveLevel().toggleDeleteChunks();
			if(c.deleteChunks.isActive()){
				c.addChunks.setActive(false);
			}
		}
		if(e == c.showGrid){
			m.getGame().getActiveLevel().toggleShowGrid();
			m.getGame().getActiveLevel().reload();
		}
		if(e == c.showCollision){
			m.getGame().getActiveLevel().toggleShowCollision();
			m.getGame().getActiveLevel().reload();
		}
		if(e == c.addTileset){
			c.addTileSet();
		}
		if(e == c.entityFootprint){
			m.options().showEntityFootprints = !m.options().showEntityFootprints;
		}
		if(e == c.entitySize){
			m.options().showEntitySizes = !m.options().showEntitySizes;
		}
	}
	
	
}
