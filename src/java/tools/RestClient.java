package tools;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



/**
 *
 * @author Administrator
 */
public class RestClient {
    
    String endpointurl = "https://104.45.23.0/authenticate";
    
    public RestClient(String endpointaddresss){
        
        this.endpointurl = endpointaddresss;
    }
    
       public RestClient(){
         
    }
    
    public String ProcessRequest(String payload, String methodName){
        
        try {
         
            //javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((String hostname, javax.net.ssl.SSLSession sslSession) -> hostname.equals("104.45.23.0"));
           TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };  
            
        URL url = new URL(endpointurl+"/"+methodName); 
        
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(); 
        connection.setDoOutput(true); 
        connection.setInstanceFollowRedirects(false); 
        connection.setRequestMethod("POST"); 
        connection.setRequestProperty("Accept", "application/json");        
        connection.setRequestProperty("Content-Type", "application/json");        connection.setHostnameVerifier(allHostsValid);
         OutputStream os = connection.getOutputStream();
        
        os.write(payload.getBytes("UTF8")); 
        
        os.flush();

            BufferedReader   br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
          StringBuilder sb = new StringBuilder();
          String output;
          
          while ((output = br.readLine()) != null) {
          sb.append(output);
                 
        }
        
          return sb.toString();

    } catch(Exception e) { 
        throw new RuntimeException(e); 
    } 
    }
    
  
     public String get_SHA_512_Hash(String StringToHash, String   salt) throws Exception{
String generatedPassword = null;
    try {
         MessageDigest md = MessageDigest.getInstance("SHA-512");
         md.update(salt.getBytes(StandardCharsets.UTF_8));
         byte[] bytes = md.digest(StringToHash.getBytes(StandardCharsets.UTF_8));
         StringBuilder sb = new StringBuilder();
         for(int i=0; i< bytes.length ;i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
         }
         generatedPassword = sb.toString();
        } 
       catch (NoSuchAlgorithmException e){
       throw (e);
       }
    return generatedPassword;
}
    
    
  
}
