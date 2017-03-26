package com.goyoung.util.ERPM.RestAPI.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SharedCredential {

	public static String CheckOut(String AuthenticationToken, String Comment, String AccountName,
			String SharedCredentialListName, String SystemName) throws IOException {

		String sp_checkout_message = "{\"AuthenticationToken\":\"" + AuthenticationToken + "\",\"Comment\":\"" + Comment
				+ "\",\"SharedCredentialIdentifier\":" + "{\"AccountName\":\"" + AccountName
				+ "\",\"SharedCredentialListName\":\"" + SharedCredentialListName + "\",\"SystemName\":\"" + SystemName
				+ "\"}}";

		String response = ERPMRestClient.post(sp_checkout_message, "AccountStoreOps_SharedCredential_CheckOut");

		JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

		return jobj.get("Password").getAsString();

	}

}
