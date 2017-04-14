package mapobjects.nature;

import java.util.concurrent.ThreadLocalRandom;

import game.Map;
import inputs.Type;

public class Tree extends Harvestable{

	public Tree(int setResources, String setObjectName, double setX, double setY, double setScale, Type setType) {
		super(setResources, setObjectName, setX, setY, setScale, setType);
	}
	
	public Tree() {
		super(
				100,
				"Tree",
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				(double) ThreadLocalRandom.current().nextInt(5, 10 + 1) / 10,
				Type.getRandomTreeModel());
	}
}
