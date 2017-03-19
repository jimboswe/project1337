package mapobjects;

import java.util.concurrent.ThreadLocalRandom;

import game.Map;
import inputs.Type;

public class Stone extends StaticObject{

	int stone;

	public Stone(String setObjectName, double setX, double setY, double setScale, Type setType) {
		super(setObjectName, setX, setY, setScale, setType);
		
		stone = 100;
	}
	
	public Stone() {
		super("Stone",
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				ThreadLocalRandom.current().nextInt(100, Map.world_size + 1),
				(double) ThreadLocalRandom.current().nextInt(5, 10 + 1) / 10,
				Type.STONE);
		
		stone = 100;
	}
}
