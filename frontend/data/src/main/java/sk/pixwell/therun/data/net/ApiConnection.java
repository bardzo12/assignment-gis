/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sk.pixwell.therun.data.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import timber.log.Timber;

/**
 * Api Connection class used to retrieve data from the cloud.
 * Implements {@link java.util.concurrent.Callable} so when executed asynchronously can
 * return a value.
 */
class ApiConnection implements Callable<String> {

  private static final HeaderType ACCEPT_LABEL = HeaderType.ACCEPT;
  private static final String API_VERSION = "application/run.therun.v1+json";

  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  private static final long CONNECT_TIMEOUT_S = 120;
  private static final long WRITE_TIMEOUT_S = 120;
  private static final long READ_TIMEOUT_S = 120;


  private URL url;
  private HashMap<HeaderType, String> headers;
  private Response response;
  private RequestType requestType;
  private String token;
  private String version;

  private RequestBody requestBody;
  private JsonObject json;
    private Context context;

  private ApiConnection(String url, RequestType requestType, Context context) throws MalformedURLException {
    this.url = new URL(url);
    this.requestType = requestType;
      this.context = context;
    try {
      PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      String version = pInfo.versionName;
      this.version = version;
      Timber.i("App version: %s", version);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  static ApiConnection createGET(String url, Context context) throws MalformedURLException {
    return new ApiConnection(url, RequestType.GET, context);
  }

  static ApiConnection createPOST(String url, Context context) throws MalformedURLException {
    return new ApiConnection(url, RequestType.POST, context);
  }

  static ApiConnection createDELETE(String url, Context context)throws MalformedURLException {
    return new ApiConnection(url, RequestType.DELETE, context);
  }

  static ApiConnection createPUT(String url, Context context)throws MalformedURLException {
    return new ApiConnection(url, RequestType.PUT, context);
  }

  void createHeaders(HashMap<HeaderType, String> values){
    headers = values;
  }

  void createBody(HashMap<String,Object> values) {
    json = new JsonObject();

    for (Map.Entry<String, Object> entry : values.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();

      if(value == null){
        json.add(key,  JsonNull.INSTANCE);
      }else {
        if (value instanceof String) {
          json.addProperty(key, (String) value);
        } else if (value instanceof Integer) {
          json.addProperty(key, (Integer) value);
        } else if (value instanceof Boolean) {
          json.addProperty(key, (Boolean) value);
        } else if (value instanceof Double) {
          json.addProperty(key, (Double) value);
        } else if (value instanceof Character) {
          json.addProperty(key, (Character) value);
        } else if (value instanceof JSONObject) {
          JSONObject jsonObject = (JSONObject) value;
          JsonParser jsonParser = new JsonParser();
          JsonObject gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
          json.add(key, gsonObject);
        } else if (value instanceof JSONArray) {
          JSONArray jsonArray = (JSONArray) value;
          JsonParser jsonParser = new JsonParser();
          JsonArray gsonArray = (JsonArray) jsonParser.parse(jsonArray.toString());
          json.add(key, gsonArray);
        } else if (value instanceof JsonArray) {
          json.add(key, (JsonArray) value);
        }
      }
    }

    requestBody = RequestBody.create(JSON, json.toString());
  }

  void setRequestBody(RequestBody requestBody) {
    this.requestBody = requestBody;
  }

  /**
   * Do a request to an api synchronously.
   * It should not be executed in the main thread of the application.
   *
   * @return A string response
   */
  @Nullable
  Response requestSyncCall() {
    Request request = connectToApi();
      request = request.newBuilder().addHeader("X-Platform", "android").build();
      request = request.newBuilder().addHeader("X-Version", version).build();
      OkHttpClient okHttpClient = this.createClient(context);
    try {
      this.response = okHttpClient.newCall(request).execute();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return response;
  }

  private Request connectToApi() {
    Request request = new Request.Builder()
            .url(this.url)
            .addHeader(ACCEPT_LABEL.type, API_VERSION)
            .build();

    Timber.d("Request type = %s\nURL = %s", requestType, url);

    switch (requestType){
      case GET:
        request = request.newBuilder().get().build();
        break;
      case DELETE:
        request = request.newBuilder().delete().build();
        break;
      case PUT:
        if (requestBody != null) {
          request = request.newBuilder().put(requestBody).build();
        }else {
          throw new Error("You have to set request body");
        }
        break;
      case POST:
        if (requestBody != null) {
          request = request.newBuilder().post(requestBody).build();
        }else {
          throw new Error("You have to set request body");
        }
        break;
      default:
        throw new Error("Unknown request type");
    }

    if (headers != null) {
      for (Map.Entry<HeaderType, String> entry : headers.entrySet()) {
        if (entry.getKey() == HeaderType.AUTHORIZATION) {
          if(token == null)
            token = entry.getValue();
          request = request.newBuilder().addHeader(entry.getKey().type, "Bearer " + token).build();

          // /request = request.newBuilder().addHeader(entry.getKey().type, "Bearer " + entry.getValue()).build();
        }else {
          request = request.newBuilder().addHeader(entry.getKey().type, entry.getValue()).build();
        }
      }
    }
    return request;
  }

  private OkHttpClient createClient(Context context) {
     CustomInterceptor interceptor =  new CustomInterceptor();
      interceptor.setContext(context);
    return new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor())
            .connectTimeout(CONNECT_TIMEOUT_S, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_S, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_S, TimeUnit.SECONDS)
            .build();
  }

  @Override
  public String call() throws Exception {
    return requestSyncCall().body().string();
  }


  enum HeaderType {
      AUTHORIZATION("Authorization"),
      TYPE("type"),
      DISTANCE("distance"),
      X_PLATFORM("X-Platform"),
      X_VERSION("X-Version"),
      ACCEPT("Accept");

    final String type;
    HeaderType (String type){
      this.type = type;
    }
  }

  private enum RequestType {
    GET,
    POST,
    DELETE,
    PUT
  }

  @Override
  public String toString(){
    StringBuilder result = new StringBuilder();
    result.append("**** API CONNECTION DETAILS**********\n");
    result.append("Header=").append(ACCEPT_LABEL.type).append(" Value=").append(API_VERSION).append("\n");
    if (headers != null) {
      for (Map.Entry<HeaderType, String> entry : headers.entrySet()) {
        result.append("Header=" + entry.getKey().type + " Value=" + entry.getValue() + "\n");
      }
    }

    switch (requestType){
      case GET:
        result.append("Type=GET\n");
        break;
      case DELETE:
        result.append("Type=DELETE\n");
        break;
      case POST:
        if (json != null) {
          result.append("Type=POST\n");
          result.append("Body=").append(json.toString()).append("\n");
        }else if (requestBody != null){
          result.append("Type=POST\n");
          result.append("Body=").append(stringifyRequestBody(requestBody)).append("\n");
        }
        break;
      case PUT:
        if (json != null) {
          result.append("Type=POST\n");
          result.append("Body=").append(json.toString()).append("\n");
        }else if (requestBody != null){
          result.append("Type=POST\n");
          result.append("Body=").append(stringifyRequestBody(requestBody)).append("\n");
        }
        break;
      default:
        throw new Error("Unknown request type");
    }

    result.append("*******************************");

    return result.toString();
  }

  private String stringifyRequestBody(RequestBody request) {
    if (request != null) {
      try {
        final RequestBody copy = request;
        final Buffer buffer = new Buffer();
        copy.writeTo(buffer);
        return buffer.readUtf8();
      } catch (final IOException e) {
        Timber.w("Failed to stringify request body: %s", e.getMessage());
      }
    }
    return "";
  }

  private class CustomInterceptor implements Interceptor {

    // these two static variables serve for the pattern to refresh a token
    private Lock lock = new ReentrantLock();
    private Context context;
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

      Request request = chain.request();
      Response response = chain.proceed(request);


      if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {


        try {
          JSONObject responseJson = new JSONObject(response.body().string());
          if (responseJson.getString("message").equals("sk.pixwell.therun.domain.Token has expired")) {

            // first thread will acquire the lock and start the refresh token
            if (lock.tryLock()) {
              Timber.i("refresh token thread holds the lock");

              // this sync call will refresh the token and save it for
              // later use (e.g. sharedPreferences)
              ApiConnection apiConnection = ApiConnection.createGET(null, context);

              HashMap<HeaderType, String> headers = new HashMap<>();
              headers.put(HeaderType.AUTHORIZATION, token);

              apiConnection.createHeaders(headers);

              Response tokenResponse = apiConnection.requestSyncCall();

              setNewToken(tokenResponse);

              Request newRequest = recreateRequestWithNewAccessToken(chain);
              Timber.i("refresh token finished. release lock");
              lock.unlock();

              return chain.proceed(newRequest);

            } else {
              Timber.i("wait for token to be refreshed");
              lock.lock(); // this will block the thread until the thread that is refreshing
              // the token will call .unlock() method
              lock.unlock();
              Timber.i("token refreshed. retry request");
              Request newRequest = recreateRequestWithNewAccessToken(chain);
              return chain.proceed(newRequest);
            }
          } else if (responseJson.getString("message")
                  .equals("sk.pixwell.therun.domain.Token has expired and can no longer be refreshed")) {



          }else if(responseJson.getString("message").equals("401 Unauthorized")){
              return response;
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } else if(response.code() == HttpURLConnection.HTTP_NOT_ACCEPTABLE){
          //TheRunActivity activity = (App) context;
      }else
        return response;
      return response;
    }

    private void setNewToken(Response body) {
      try {
        try {

          //TODO Refreshed token is not save to SharedPreferences
          JSONObject responseJson = new JSONObject(body.body().string());
          String newToken = responseJson.getString("token");
          /*TokenEntity tokenEntity = new TokenEntity();
          tokenEntity.setStatusCode(1);
          tokenEntity.setToken(newToken);*/
          if(newToken != null)
            token = newToken;
          Timber.i("New token is set");
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    private Request recreateRequestWithNewAccessToken(Chain chain) {
      Timber.i("Build new request");
      return chain.request()
              .newBuilder()
              .removeHeader("Authorization")
              .addHeader("Authorization", "Bearer " + token)
              .build();
    }

      public void setContext(Context context) {
          this.context = context;
      }
  }

}
