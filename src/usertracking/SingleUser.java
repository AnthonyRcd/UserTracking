package usertracking;


import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import usefulmethods.BaseClass;
import usefulmethods.ConfigReader;
import usefulmethods.Point;

public class SingleUser extends BaseClass {	
	
	public static void main(String[] args) throws InterruptedException{
		
		try {
			config = ConfigReader.readConfig("./configs/config3.ini");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture du fichier.");
		} 
		
		setVariables("location");
		
		String url = String.join("",url_prefix,mse_ip,url_suffix,mac);
			
		/*
		System.out.println(getResponse(url));
		System.out.println(getInformation(url,UserOptions.detectedBy));
		System.out.println(getMapInformation(url,MapOptions.floorId));
		System.out.println(getMapInformation(url,MapOptions.hierarchyString));
		System.out.println(getInformation(url,UserOptions.identifyUser));
		System.out.println(getInformation(url,UserOptions.ip_address));
		System.out.println(getInformation(url,UserOptions.locate));
		System.out.println(getInformation(url,UserOptions.locatedAt));
		System.out.println(getInformation(url,UserOptions.userType));
		System.out.println(getMapInformation(url,MapOptions.imageInfo));
		System.out.println(retrieveMapImage((String) getMapInformation(url,MapOptions.image)));
		System.out.println(saveImage((String) getMapInformation(url,MapOptions.image)));
		*/
		while(true){
			System.out.println(getInformation(url,UserOptions.locate));
			//TimeUnit.MILLISECONDS.sleep(1500);
		}
	}

}
