import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import usefulmethods.BaseClass;
import usefulmethods.ConfigReader;
import usefulmethods.HTTPMethods;

public class MapRetrieval extends BaseClass{

	public static void main(String[] args) {
		try {
			config = ConfigReader.readConfig("./configs/config3.ini");
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture du fichier.");
		} 
		
		setVariables();
		
		String url = url_prefix + mse_ip + url_suffix;
		HTTPMethods.SSLHandler();
		try {
		
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		HTTPMethods.setMapHeaders(connection, url, charset, authentication);
		
		BufferedImage image = ImageIO.read(connection.getInputStream());
		ImageIO.write(image, "jpg",new File("./resources/img.jpg"));
		
		//System.out.println(connection.getURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
