package mapobjects.nature;
import inputs.Type;
import mapobjects.StaticObject;

public class Harvestable extends StaticObject{

	int resources;

	public Harvestable(int setResources, String setObjectName, double setX, double setY, double setScale, Type setType) {
		super(setObjectName, setX, setY, setScale, setType);
		
		resources = setResources;
	}

	public int getAmountOfResourcesLeft() {
		return resources;
	}

	public int harvest(int harvestAmount) {
		if(resources < harvestAmount) {
			harvestAmount = resources;
			resources = 0;
			return harvestAmount;
		}
		else {
			resources -= harvestAmount;
			return harvestAmount;
		}
	}
}
