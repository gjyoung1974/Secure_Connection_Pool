package com.goyoung.util.ERPM.RestAPI.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.logging.*;

public class ErpmRestClient {

    private static Logger logger = Logger.getLogger(ErpmRestClient.class.getName());


    public static String post(String input, String operation) throws IOException {


        // Load RestAPI username & password from config file
        File configDir = new File(System.getProperty("catalina.base"), "conf");
        File configFile = new File(configDir, "config.properties");
        InputStream in = new FileInputStream(configFile);
        Properties prop = new Properties();

        try {
            prop.load(in);
        } catch (IOException e) {
            logger.log(Level.INFO, "prop.load() threw an exception.", e);
        }

        SslUtilities.trustAllHostnames();
        SslUtilities.trustAllHttpsCertificates();

        URL url = new URL(prop.getProperty("url") + operation);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        // TODO some urlconnection Eror handling
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            logger.log(Level.INFO, "conn.getResponseCode() threw ", new IOException("Failed : HTTP error code : " + conn.getResponseCode()));
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }
}
