package usefulmethods;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.ini4j.*;

public class ConfigReader {

	/**
	 * Reads the config from the file whose name is passed as a parameter.
	 * The configuration file is a *.ini file and falls into 2 parts: "mse" and "local" (the third part is obsolete).
	 * @param string
	 * @return The Ini object containing the whole config
	 * @throws InvalidFileFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Ini readConfig(String string) throws InvalidFileFormatException, FileNotFoundException, IOException{
		
		Ini config = new Ini(new FileReader(new File(string)));
		
		return config;
	}
}
