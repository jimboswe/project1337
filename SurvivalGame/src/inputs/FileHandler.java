package inputs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import mapobjects.AnimatedObject;
import mapobjects.StaticObject;
import mapobjects.Zombie;

public class FileHandler {

	public static final String PATH = "c:\\java\\map.txt";

	public static void SaveToFile(ArrayList<StaticObject> objects) {
		System.out.println("Saving...");
		Formatter f;
		try {
			f = new Formatter(PATH);
			for (StaticObject o : objects) {
				f.format("%s %s %s %s", Type.getString(o.getModel()), o.getX(), o.getY(), o.getScale());
				f.format("\r\n");
			}
			f.close(); // Stäng formatter och filen
			System.out.println(PATH + " has been created and objects have been saved.");
		} catch (FileNotFoundException e) {
			System.out.println("Error. Couldn't create file");
			e.printStackTrace();
		}
	}

	public static ArrayList<StaticObject> ReadFromFile() {
		System.out.println("Loading data...");
		File file = new File(PATH);
		ArrayList<StaticObject> list = new ArrayList<StaticObject>();
		if (file.exists()) {
			try {

				Scanner scan = new Scanner(file);
				Type t;
				while (scan.hasNext()) {
					try {
						t = Type.getModel((scan.next()));
						switch (t) {
						case STONE1:
							list.add(new StaticObject("", Double.valueOf(scan.next()), Double.valueOf(scan.next()), Double.valueOf(scan.next()), t));
							break;
						case ZOMBIE:
							list.add(new Zombie(Double.valueOf(scan.next()), Double.valueOf(scan.next()), Double.valueOf(scan.next()), t));
							break;
						case TREE1:
							list.add(new AnimatedObject("", Double.valueOf(scan.next()), Double.valueOf(scan.next()), Double.valueOf(scan.next()), t, 4, 0, false, 1, null, true, true));
							break;
						default:
							System.out.println("Error reading type, ReadFromFile");
						}
					} catch (Exception e) {
						System.out.println("File Error");
						e.printStackTrace();
						break;
					}
				}
				scan.close();
				System.out.println("Loaded from " + file.getPath());
			} catch (FileNotFoundException e) {
				System.out.println("Error, Scanner");
				e.printStackTrace();
			}
		}
		return list;
	}
}
