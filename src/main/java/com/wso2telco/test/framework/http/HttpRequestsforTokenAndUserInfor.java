package com.wso2telco.test.framework.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

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

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;






import com.thoughtworks.selenium.webdriven.commands.GetHtmlSource;

/**
 * @author Susantha Pathirana
 *
 */
public class HttpRequestsforTokenAndUserInfor {

	
	
	static {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Ignore differences between given hostname and certificate hostname
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (Exception e) {
		}
	}

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
		//HttpClient client = getNewHttpClient();
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
	
	/*public String sendPOSTRequestConnection(String url)
			throws ClientProtocolException, IOException {
		StringBuffer retStr = new StringBuffer();
		HttpsURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		
		
		try {

			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			URL url = new URL(url);
			connection = (HttpsURLConnection) url.openConnection();

			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod(Constants.URLProperties.URL_METHOD.getValue());
			connection.setRequestProperty(Constants.URLProperties.AUTHORIZATION_GRANT_TYPE.getValue(),
					AuthMethod.BASIC + authheader);
			connection.setRequestProperty(Constants.URLTypes.CONTENT.getType(),
					Constants.URLTypes.CONTENT.getValue());
			connection.setRequestProperty(Constants.URLTypes.ENCODING.getType(),
					Constants.URLTypes.ENCODING.getValue());
			connection.setRequestProperty(Constants.URLProperties.LENGTH.getValue(),
					Integer.toString(postDataLength));
			connection.setUseCaches(false);

			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(postData);
			wr.flush();
			wr.close();

			// filter out invalid http codes
			if ((connection.getResponseCode() == Status.OK.getStatusCode())
					|| (connection.getResponseCode() == Status.CREATED.getStatusCode())) {
				is = connection.getInputStream();
			} else {
				is = connection.getErrorStream();
			}

			br = new BufferedReader(new InputStreamReader(is));
			String output;
			while ((output = br.readLine()) != null) {
				retStr.append(output);
			}
		} catch (Exception e) {
			log.error("TokenReGenarator , makerequest(), ", e);
			throw new TokenException(TokenError.TOKEN_REGENERATE_FAIL);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}

			if (connection != null) {
				connection.disconnect();
			}
		}

	
		return retStr.toString();
	}
*/
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
