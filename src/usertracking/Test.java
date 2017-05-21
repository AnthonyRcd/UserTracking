package usertracking;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import usefulmethods.*;

public class Test extends BaseClass{
	
	public static void main(String[] args){
		try {
			config = ConfigReader.readConfig("./configs/config2.ini");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture du fichier.");
		} 
		setVariables();
		
		JSONObject json = BaseClass.getResponse(url_prefix + mse_ip + url_suffix + mac);
		
		try(FileWriter fw = new FileWriter("test.txt")){
			while(json.sortedKeys().hasNext()){
				fw.write(json.sortedKeys().next().toString() + "\n");
			}
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
