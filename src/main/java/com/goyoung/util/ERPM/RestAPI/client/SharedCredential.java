package com.goyoung.util.ERPM.RestAPI.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SharedCredential {

	public static String checkOut(String authenticationToken, String comment, String accountName,
								  String sharedCredentialListName, String systemName) throws IOException {

		String spCheckoutMessage = "{\"AuthenticationToken\":\"" + authenticationToken + "\",\"Comment\":\"" + comment
				+ "\",\"SharedCredentialIdentifier\":" + "{\"AccountName\":\"" + accountName
				+ "\",\"SharedCredentialListName\":\"" + sharedCredentialListName + "\",\"SystemName\":\"" + systemName
				+ "\"}}";

		String response = ErpmRestClient.post(spCheckoutMessage, "AccountStoreOps_SharedCredential_CheckOut");

		JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

		return jobj.get("Password").getAsString();

	}

}
