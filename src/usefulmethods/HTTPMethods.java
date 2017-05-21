package usefulmethods;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HTTPMethods {

	
	/**
	 * Allows and handles the use of SSL certificates for the connection to the CMX
	 * @author Cisco
	 */
	public static void SSLHandler(){

		TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        SSLContext sc = null;

        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);		
	}

	/**
	 * Sets the headers for the request that retrieves information on a single user
	 * @author Anthony Ricard
	 * @param connection HTTP connection handler
	 * @param url URL used to fire the connection
	 * @param charset Default charset used by the page to retrieve
	 * @param authentication Authentication String ("uname:passwd" encoded with Base64 encoding)
	 */
	public static void setLocationHeaders(HttpURLConnection connection, String url, String charset, String authentication) throws MalformedURLException, IOException{
		
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Authorization", "Basic " + authentication);
		connection.setRequestProperty("Accept", "application/json");
	}

	public static void setMapHeaders(HttpURLConnection connection, String url, String charset, String authentication) throws ProtocolException {
		
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Authorization", "Basic " + authentication);
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
	}
	
}
