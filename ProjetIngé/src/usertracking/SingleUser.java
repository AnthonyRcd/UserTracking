package usertracking;


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
			config = ConfigReader.readConfig("./configs/config2.ini");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture du fichier.");
		} 
		
		setVariables();
		
		String url = url_prefix + mse_ip + url_suffix + mac;
		
		/*
		String file_prefix = "./responses/Response";
		String file_suffix = ".json";
		String filename = null;
		
		
		for(int i=0;i<1;i++){
			 filename = file_prefix + i + file_suffix;
			//System.out.println(getInformation(url, Options.locate));
			saveJSON(getResponse(url),filename);
			try {
				java.util.concurrent.TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		*/
		//System.out.println(getMapInformation(url, Options.hierarchyString));
		System.out.println(getResponse(url));
	}

}
