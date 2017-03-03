
public class Truck extends Car{
	int cargoVolume;
	
	Truck(String setName, int setHorsePower, int setPassengers, String setFuelType, int setWheels, int setcargoVolume) {
		super(setName, setHorsePower, setFuelType, setWheels);
		cargoVolume = setcargoVolume;
	}

	public int getCargoVolume() {
		return cargoVolume;
	}

	
}
