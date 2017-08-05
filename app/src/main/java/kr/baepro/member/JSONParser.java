package kr.baepro.member;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by baepro21 on 2017. 8. 1..
 * http://www.javaknowledge.info/sample-login-app-in-android-using-servlet-and-json-parsing/
 */

public class JSONParser {
    static InputStream inputStream1 = null;
    static JSONObject jsonObject = null;
    static String json = "";

    static InputStream inputStream2 = null;
    static JSONArray jsonArray = null;

    // constructor
    public JSONParser() {

    }

    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {
        try {
            if(method == "POST") {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream1 = httpEntity.getContent();
            } else if(method == "GET") {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream1 = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream1, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream1.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jsonObject;
    }

    public JSONArray getJSONFromUrl(String url) {

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e("==>", "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse String to JSON object
        try {
            jsonArray = new JSONArray( builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jsonArray;

    }


    public JSONObject makeHttpRequest2(String url) {

        // Making HTTP request
        try {

            // check for request method
            //if(method == "POST"){
            // request method is POST
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            //  httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream1 = httpEntity.getContent();

//            }else if(method == "GET"){
//                // request method is GET
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                //String paramString = URLEncodedUtils.format(params, "utf-8");
//                url += "?" + paramString;
//                HttpGet httpGet = new HttpGet(url);
//
//                HttpResponse httpResponse = httpClient.execute(httpGet);
//                HttpEntity httpEntity = httpResponse.getEntity();
//                is = httpEntity.getContent();
//            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream1, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inputStream1.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jsonObject;

    }

}
