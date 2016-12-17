package com.edonica.somfy;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class Protocol {
    static final String CONNECT_HOST = "https://www.tahomalink.com/";
    static final String BASE_URL = CONNECT_HOST + "enduser-mobile-web/externalAPI/";
    static final String BASE_URL_ENDUSER = CONNECT_HOST + "enduser-mobile-web/enduserAPI/";
    CloseableHttpClient httpClient;
    JSONObject setup;

    public Protocol() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        httpClient = HttpClients.custom().
                setHostnameVerifier(new AllowAllHostnameVerifier()).
                setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
                {
                    public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
                    {
                        return true;
                    }
                }).build()).build();
    }

    public void login(String username, String password) throws IOException {


        HttpPost httpPost = new HttpPost(BASE_URL + "json/login");

        HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpPost.setConfig(config);
        httpPost.addHeader("User-Agent","mine");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("userId", username));
        nvps.add(new BasicNameValuePair("userPassword", password));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
    }

    public void getSetup() throws IOException {
        HttpGet httpGet = new HttpGet(BASE_URL + "json/getSetup");
        httpGet.addHeader("User-Agent","mine");

        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();

            String json = IOUtils.toString(entity.getContent());
            setup = new JSONObject(json);
            System.out.println(json);
        } finally {
            response.close();
        }
    }

    public JSONObject createAction(String deviceURL, String command) {
        JSONObject commandPair = new JSONObject();
        commandPair.put("name",command);
        commandPair.put("parameters", new JSONArray());
        JSONArray commandArray = new JSONArray();
        commandArray.put(commandPair);

        JSONObject action = new JSONObject();
        action.put("commands", commandArray);
        action.put("deviceURL",deviceURL);

        JSONArray actions = new JSONArray();
        actions.put(action);



        JSONObject root = new JSONObject();
        root.put("label","Label that is ignored probably");
        root.put("actions", actions);

        System.out.println(root.toString());
        return root;
    }

    public void closeWindow() throws IOException {

        JSONObject window = ((JSONObject)((JSONArray)((JSONObject)setup.get("setup")).get("devices")).get(1));
        String command2= createAction(window.getString("deviceURL"), "close").toString();

        HttpPost httpPost = new HttpPost(BASE_URL_ENDUSER + "exec/apply");

        HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
        httpPost.setConfig(config);
        httpPost.addHeader("User-Agent","mine");
        httpPost.addHeader("Content-Type","application/json;charset=UTF-8");

        httpPost.setEntity(new StringEntity(command2, "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }


    }
}
