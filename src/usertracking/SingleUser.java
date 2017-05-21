package usertracking;


import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import usefulmethods.BaseClass;
import usefulmethods.ConfigReader;
import usefulmethods.Point;

public class SingleUser extends BaseClass {	
	
	public static Object getInformation(String url,Options option){
		JSONObject json = getResponse(url);
		switch(option){
		case locate: {
			double abs = 0;
			double ord = 0;
			try {
				abs = json.getJSONObject(option.getJsonKey()).getDouble("x");
				ord = json.getJSONObject(option.getJsonKey()).getDouble("y");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		
			return new Point(abs,ord); //returns a "Point" Object
		}
		
		default:
			try {
				return json.get(option.getJsonKey());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			/* return types:
			 * ip_address: String[1]
			 * detectedBy: String
			 * locatedAt: JSONObject
			 * identifyUser: String
			*/
		}
	}
	
	public static Object getMapInformation(String url, Options option){
		JSONObject json = (JSONObject) getInformation(url,Options.locatedAt);
		if (option.equals(Options.image))
		{
			JSONObject temp = (JSONObject) getMapInformation(url,Options.imageInfo);
			return temp.opt("imageName");
		}
		try {
			return json.get(option.getJsonKey());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		
		try {
			config = ConfigReader.readConfig("./configs/config.ini");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture du fichier.");
		} 
		
		setVariables();
		
		String url = url_prefix + mse_ip + url_suffix + mac;
		//String url = url_prefix + mse_ip + url_suffix;

		String file_prefix = "./responses/Response";
		String file_suffix = ".json";
		String filename = file_prefix + file_suffix;
		//saveJSON(getResponse(url), filename);
		
		//getResponse("https://cmxlocationsandbox.cisco.com/api/config/v1/maps/image/DevNetCampus/DevNetBuilding/DevNetZone");
		System.out.println(getResponse(url));
	}

}
