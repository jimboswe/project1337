package mapobjects.nature;

import java.util.concurrent.ThreadLocalRandom;

import editor.Map;
import inputs.Type;

public class Stone extends Harvestable{

	public Stone(int setResources, String setObjectName, double setX, double setY, double setScale, Type setType) {
		super(setResources, setObjectName, setX, setY, setScale, setType);
	}
	
	public Stone() {
		super(
				100,
				"Stone",
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				(double) ThreadLocalRandom.current().nextInt(5, 10 + 1) / 10,
				Type.getRandomStoneModel());
	}
}
