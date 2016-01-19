package com.wso2telco.test.framework.http;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

public class SendHttpPost {
	public String postToURL(String url, String message, HttpClient httpClient)
			throws IOException, IllegalStateException,
			UnsupportedEncodingException, RuntimeException {

		HttpPost postRequest = new HttpPost(url);
		StringEntity input = new StringEntity(message);
		input.setContentType("application/json");
		postRequest.setEntity(input);
		HttpResponse response = httpClient.execute(postRequest);
		int responseCode = response.getStatusLine().getStatusCode();
		if (String.valueOf(responseCode).charAt(0) == '4' || String.valueOf(responseCode).charAt(0) == '5') {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(response.getEntity().getContent())));

		String output;
		StringBuffer totalOutput = new StringBuffer();
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			totalOutput.append(output);
		}
		return totalOutput.toString();
	}
}
