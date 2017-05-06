package usefulmethods;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.ini4j.*;

public class ConfigReader {

	public static Ini readConfig(String string) throws InvalidFileFormatException, FileNotFoundException, IOException{
		
		Ini config = new Ini(new FileReader(new File(string)));
		
		return config;
	}
}
