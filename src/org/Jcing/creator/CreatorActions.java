package org.Jcing.creator;

import org.Jcing.GUI.Clickable;
import org.Jcing.GUI.ClickableManager;
import org.Jcing.main.Main;

public class CreatorActions implements ClickableManager {

	public CreatorActions() {

	}

	public void actionHappened(Clickable e) {
		if (e == Main.getCreator().windowCreator) {
			Main.getCreator().toggleWindow();
		}
		if (e == Main.getCreator().addChunks) {
			Main.getGame().getActiveLevel().toggleAddChunks();
			if (Main.getCreator().addChunks.isActive()) {
				Main.getCreator().deleteChunks.setActive(false);
			}
		}
		if (e == Main.getCreator().deleteChunks) {
			Main.getGame().getActiveLevel().toggleDeleteChunks();
			if (Main.getCreator().deleteChunks.isActive()) {
				Main.getCreator().addChunks.setActive(false);
			}
		}
		if (e == Main.getCreator().showGrid) {
			Main.getGame().getActiveLevel().toggleShowGrid();
			Main.getGame().getActiveLevel().reload();
		}
		if (e == Main.getCreator().showCollision) {
			Main.getGame().getActiveLevel().toggleShowCollision();
			Main.getGame().getActiveLevel().reload();
		}
		if (e == Main.getCreator().addTileset) {
			Main.getCreator().addTileSet();
		}
		if (e == Main.getCreator().entityFootprint) {
			Main.settings().showEntityFootprints = !Main.settings().showEntityFootprints;
		}
		if (e == Main.getCreator().entitySize) {
			Main.settings().showEntitySizes = !Main.settings().showEntitySizes;
		}
	}

}
