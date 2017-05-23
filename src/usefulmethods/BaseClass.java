package usefulmethods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

import javax.imageio.ImageIO;

import org.ini4j.Ini;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * Base class used for the retrieval of information on the CMX. All the subsequent classes inherit from
 * this one. It realizes the initialization of the different fields used to fire the different HHTP requests.
 * @author Anthony Ricard
 *
 */
public class BaseClass {

	protected static Ini config;
	protected static String mse_ip ;
	protected static String username ;
	protected static String password ;
	protected static String authentication;
	protected static String url_suffix ;
	protected static String mac ;
	protected static String response_format ;
	protected static String plotly_username;
	protected static String plotly_api_key ;
	protected static String[] plotly_streaming_keys ;
	protected static int interval ;
	
	protected static final String charset = java.nio.charset.StandardCharsets.UTF_8.name();
	protected static final String url_prefix = "https://" ;
	protected static final String url_query_parameters1 = "?/page=" ;
	protected static final String url_query_parameters2 = "&pageSize=" ;
	
	protected static int numCharsRead;
    protected static char[] charArray = new char[1024];
    protected static StringBuffer sb = new StringBuffer();
	
    /**
     * Initialization of the fields.
     * @author Anthony Ricard
     */
	protected static void setVariables(String function){

		/* ----- D�but de lecture du fichier de config -----*/
		
		mse_ip = config.get("mse", "mse_ip");
		username = config.get("mse", "username");
		password = config.get("mse", "password");
		authentication = Base64.encode(String.join(":",username,password).getBytes());
		url_suffix = config.get(function, "url_suffix");
		mac = config.get("local", "mac");
		response_format = config.get("local", "response_format");
		plotly_username = config.get("plotly", "plotly_username");
		plotly_api_key = config.get("plotly", "plotly_api_key");
		plotly_streaming_keys = config.get("plotly", "plotly_streaming_keys").split(",");
		interval = Integer.parseInt(config.get("local", "interval"));
		
		/* ----- Fin de lecture du fichier de config ----- */
	}	
	
	/**
	 * Options used to extract information from the JSON answer.
	 * @author Anthony Ricard
	 *
	 */
	protected enum UserOptions{
		
		//User options
		locate("mapCoordinate"),
		ip_address("ipAddress"),
		detectedBy("detectingControllers"),
		locatedAt("mapInfo"),
		identifyUser("userName"),
		userType("guestUser");
		
		protected final String jsonKey;
		
		public String getJsonKey() {
			return jsonKey;
		}

		UserOptions(final String text){
			this.jsonKey=text;
		}	
	}
	
	protected enum MapOptions{
		
		//Map options
		floorId("floorRefId"),
		hierarchyString("mapHierarchyString"),
		imageInfo("image"),
		image("imageName");
		
		protected final String jsonKey;
		
		public String getJsonKey() {
			return jsonKey;
		}

		MapOptions(final String text){
			this.jsonKey=text;
		}	
	}
	
	/**
	 * Fires a HTTP request to the url passed as a parameter.
	 * @param url
	 * @return the JSONObject containing all the information about the tracked user
	 * @throws NullPointerException
	 */
	public static JSONObject getResponse(String url) throws NullPointerException{
			
			JSONObject json = null;
			
			try{			
					HTTPMethods.SSLHandler();
					HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
					HTTPMethods.setLocationHeaders(connection, url, charset, authentication);
					
					InputStream response = connection.getInputStream();
					InputStreamReader isr = new InputStreamReader(response);
					
		            while ((numCharsRead = isr.read(charArray)) > 0) {
		                sb.append(charArray, 0, numCharsRead);
		            }
		            
		            String result = sb.toString();
		            json = new JSONObject(result);
		           
		            sb.delete(0, sb.length());
			        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}
			
			return json;
		}

	public static void saveJSON(JSONObject json,String filename){
		try (FileWriter file = new FileWriter(filename)) {
				file.write(json.toString(2));
				System.out.println("Successfully Copied JSON Object to File...\n");
			} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static String retrieveMACAddress(){
		String address = null;
		try {
			Enumeration<NetworkInterface> net = null;
			net = NetworkInterface.getNetworkInterfaces();
			NetworkInterface int_ = null;
			while(net.hasMoreElements()){
				int_ = net.nextElement();
				byte[] mac_ = int_.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				if(!(mac_ == null))
					for (int i = 0; i < mac_.length; i++) 
						sb.append(String.format("%02X%s", mac_[i], (i < mac_.length - 1) ? "-" : ""));
		        address = sb.toString();
		        System.out.println(address);
		        sb.delete(0, sb.length());
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}
	
	public static BufferedImage retrieveMapImage(String imagename){
		try {
			config = ConfigReader.readConfig("./configs/config3.ini");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture du fichier.");
		} 
		
		setVariables("map");
		String url = String.join("",url_prefix,mse_ip,url_suffix,imagename) ;
		HTTPMethods.SSLHandler();
		
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			HTTPMethods.setMapHeaders(connection, url, charset, authentication);
			BufferedImage image = ImageIO.read(connection.getInputStream());
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;		
	}

	public static boolean saveImage(String imagename){
		BufferedImage image = retrieveMapImage(imagename);
		try {
			ImageIO.write(image, "jpg",new File(String.join("","./resources/",imagename)));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static Object getInformation(String url,UserOptions option){
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
	
	public static Object getMapInformation(String url, MapOptions option){
		JSONObject json = (JSONObject) getInformation(url,UserOptions.locatedAt);
		if (option.equals(MapOptions.image))
		{
			JSONObject temp = (JSONObject) getMapInformation(url,MapOptions.imageInfo);
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
	
}
