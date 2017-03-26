package com.goyoung.util.ERPM.RestAPI.client;

import java.io.IOException;

public class App {
	
/*
 * This class represents the main entry point
 * For the sample application
 * 
 */
	public static void main(String[] args) throws IOException {
		
		//first call Get_Auth_Token() to get an API Authentication token
		//Get_Auth_Token() gets the username & password to call the API
		//from config.properties
		String auth_token = Get_Auth_Token.go();
		
		//call SharedCredential.CheckOut() to collected a password from a given password list:
		String output = SharedCredential.CheckOut(auth_token, "testing", "svc.tomcat", "tomcat_passwords", "tomcat01");
		System.out.println(output);
		
	}
}
