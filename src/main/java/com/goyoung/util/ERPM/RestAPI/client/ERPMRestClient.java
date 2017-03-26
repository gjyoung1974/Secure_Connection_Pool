package com.goyoung.util.ERPM.RestAPI.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class ERPMRestClient {

	public static String post(String input, String operation) throws IOException {

		Properties prop = new Properties();
		InputStream in = ERPMRestClient.class.getResourceAsStream("config.properties");

		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();

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
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
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
