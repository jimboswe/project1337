package mapobjects;

import java.util.concurrent.ThreadLocalRandom;

import game.Map;
import inputs.Type;

public class Tree extends StaticObject{
	
	int wood;

	public Tree(String setObjectName, double setX, double setY, double setScale, Type setType) {
		super(setObjectName, setX, setY, setScale, setType);
		
		wood = 100;
	}
	
	public Tree() {
		super("Tree",
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				(double) ThreadLocalRandom.current().nextInt(5, 10 + 1) / 10,
				Type.getRandomTreeModel());
		
		wood = 100;
	}

	public int getAmountOfResourcesLeft() {
		return wood;
	}

	public int harvest(int harvestAmount) {
		if(wood < harvestAmount) {
			harvestAmount = wood;
			wood = 0;
			return harvestAmount;
		}
		else {
			wood -= harvestAmount;
			return harvestAmount;
		}
	}
}
