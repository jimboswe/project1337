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
				Type.getRandomStoneModel());
		
		stone = 100;
	}

	public int getAmountOfResourcesLeft() {
		return stone;
	}

	public int harvest(int harvestAmount) {
		// TODO Auto-generated method stub
		if(stone < harvestAmount) {
			harvestAmount = stone;
			stone = 0;
			return harvestAmount;
		}
		else {
			stone -= harvestAmount;
			return harvestAmount;
		}
	}
}
