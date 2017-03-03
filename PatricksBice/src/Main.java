import java.util.ArrayList;

public class Main {
	
	private static ArrayList<Vehicle> garage = new ArrayList<Vehicle>();

	public static void main(String args[]) {
		Vehicle volvo740 = new Car("Volvo 740", 140, "Petrol", 4);
		garage.add(volvo740);
		
		Vehicle scaniatruck = new Truck("Scania", 240, 1, "Diesel", 8, 20);
		garage.add(scaniatruck);
		
		for(Vehicle v : garage) {
			System.out.println("Fuel: " + v.name);
			System.out.println("Horsepower: " + v.horsePower);
			System.out.println("Number of passengers: " + v.numberOfPassengers);
			System.out.println("Fuel: " + v.fuelType);
			System.out.println(""); //Lämna en tom rad
			if (v.getClass().equals(Truck.class))
				System.out.println("CargoVolume: " + ((Truck) v).getCargoVolume());
		}
	}
}
