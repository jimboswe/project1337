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
				Type.TREE);
		
		wood = 100;
	}

}
