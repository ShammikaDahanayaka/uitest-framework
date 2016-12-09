package com.wso2telco.test.framework.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author Susantha Pathirana
 *
 */
public class HttpRequests {

	private Map<String, String> header = new HashMap<>();
	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

	/**
	 * @param url
	 * @return HttpResponse
	 * @throws IOException
	 * @author Susantha Pathirana
	 */
	public HttpResponse sendGETRequest(String url) throws IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		if (!header.isEmpty()) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpResponse response = client.execute(request);
		// Clean header after sending request
		header.clear();
		return response;

	}

	/**
	 * @param url
	 * @return HttpResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author Susantha Pathirana
	 */
	public HttpResponse sendPOSTRequest(String url) throws ClientProtocolException, IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		// add header
		if (!header.isEmpty()) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				post.setHeader(entry.getKey(), entry.getValue());
			}
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse response = client.execute(post);
		header.clear();
		urlParameters.clear();
		return response;
	}

	/**
	 * @param key
	 *            ,value
	 * @return void
	 * @author Susantha Pathirana
	 */
	public void setHeader(String key, String value) {
		header.put(key, value);
	}

	/**
	 * @param key
	 *            ,value
	 * @return void
	 * @author Susantha Pathirana
	 */
	public void setUrlParameter(String key, String value) {
		urlParameters.add(new BasicNameValuePair(key, value));
	}

	/**
	 * @param url
	 * @return HttpResponse
	 * @throws IOException
	 * @author Susantha Pathirana
	 */
	public String getBodyValueofGETRequest(String url) throws IOException {
		return EntityUtils.toString(sendGETRequest(url).getEntity(), "UTF-8");
	}
}
