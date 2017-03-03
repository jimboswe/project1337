
public class Car extends Vehicle{
	
	public int numberOfWheels;

	Car(String setName, int setHorsePower, String setFuelType, int setNumberOfWheels) {
		super(setName, setHorsePower, 4, setFuelType);
		
		numberOfWheels = setNumberOfWheels;
	}

	public int getNumberOfWheels() {
		return numberOfWheels;
	}

}
