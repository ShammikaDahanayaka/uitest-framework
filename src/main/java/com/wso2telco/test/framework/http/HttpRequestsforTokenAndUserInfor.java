package com.wso2telco.test.framework.http;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.thoughtworks.selenium.webdriven.commands.GetHtmlSource;

/**
 * @author Susantha Pathirana
 *
 */
public class HttpRequestsforTokenAndUserInfor {

	private Map<String, String> header = new HashMap<>();
	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

	/**
	 * @param url
	 * @return HttpResponse
	 * @author ShammikaD
	 */

	public HttpResponse sendGETRequest(String url) {
		
		StringBuilder requestUrl = new StringBuilder(url);
			
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("schema", "openid"));
			
		String querystring = URLEncodedUtils.format(nvps, "utf-8");
		requestUrl.append("?");
		requestUrl.append(querystring);

		HttpGet request = new HttpGet(requestUrl.toString());

		// add request header
		if (!header.isEmpty()) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
			
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;

		httpclient = getNewHttpClient();

		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		return response;
	}

	@SuppressWarnings("deprecation")
	public CloseableHttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new AirTelSSLSocket(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			//registry.register(new Scheme("https", sf, 443));
			registry.register(new Scheme("https", sf, 8243));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
		}
		return new DefaultHttpClient();
	}

	/**
	 * @param url
	 * @return HttpResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author Susantha Pathirana
	 */
	public HttpResponse sendPOSTRequest(String url)
			throws ClientProtocolException, IOException {
		HttpClient client = getNewHttpClient();
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
	 * @param key ,value
	 * @return void
	 * @author Susantha Pathirana
	 */
	public void setHeader(String key, String value) {
		header.put(key, value);
	}

	/**
	 * @param key ,value
	 * @return void
	 * @author Susantha Pathirana
	 */
	public void setUrlParameter(String key, String value) {
		urlParameters.add(new BasicNameValuePair(key, value));
	}

}
