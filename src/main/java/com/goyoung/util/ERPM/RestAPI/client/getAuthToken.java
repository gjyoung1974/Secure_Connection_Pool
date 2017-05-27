package com.goyoung.util.ERPM.RestAPI.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class getAuthToken {

    private static Logger logger = Logger.getLogger(getAuthToken.class.getName());

    public static String go() throws IOException {

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

        // compose our JSON message
        String input = "{ \"LoginType\":1, \"Password\":\"" + prop.getProperty("password") + "\", \"Username\":\""
                + prop.getProperty("username") + "\" }";
        String operation = "DoLogin2";// set the operation endpoint

        // post it & collect the response
        String response = ERPMRestClient.post(input, operation);
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        return jobj.get("OperationMessage").getAsString();

    }

}
