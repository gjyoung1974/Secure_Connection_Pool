package com.goyoung.util.ERPM.RestAPI.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Get_Auth_Token {

	public static String go() throws IOException {

		// Load RestAPI username & password from config file
		Properties prop = new Properties();
		InputStream in = ERPMRestClient.class.getResourceAsStream("config.properties");

		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
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
