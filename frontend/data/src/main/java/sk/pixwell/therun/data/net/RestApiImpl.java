/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sk.pixwell.therun.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.mapper.AmenityPointsEntityJsonMapper;
import sk.pixwell.therun.data.entity.CheckInEntity.CheckInEntity;
import sk.pixwell.therun.data.entity.CheckInEntity.mapper.CheckInEntityJsonMapper;
import sk.pixwell.therun.data.entity.LandUseEntity;
import sk.pixwell.therun.data.entity.LandUseEntityJsonMapper;
import sk.pixwell.therun.data.entity.NameListEntity;
import sk.pixwell.therun.data.entity.NameListEntityJsonMapper;
import sk.pixwell.therun.data.entity.PostPoint;
import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.ReportsEntity.mapper.ReportsEntityJsonMapper;
import sk.pixwell.therun.data.entity.SegmentsEntity.SegmentsEntity;
import sk.pixwell.therun.data.entity.SegmentsEntity.mapper.SegmentsEntityJsonMapper;
import sk.pixwell.therun.data.entity.StageCheckIn;
import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.data.entity.StagesInfoEntity.mapper.StagesInfoEntityJsonMapper;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.mapper.TeamsEntityJsonMapper;
import sk.pixwell.therun.data.entity.UserEntity;
import sk.pixwell.therun.data.entity.mapper.UserEntityJsonMapper;
import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.data.entity.points.mapper.PointsEntityJsonMapper;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.data.entity.stages.mapper.StagesEntityJsonMapper;
import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.data.entity.token.mapper.TokenEntityJsonMapper;
import sk.pixwell.therun.data.exception.NetworkConnectionException;
import sk.pixwell.therun.data.exception.UnVisibleException;
import sk.pixwell.therun.data.exception.UnautorizedUserException;
import sk.pixwell.therun.data.exception.UpdateException;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;
import timber.log.Timber;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {


  private final Context context;
  private final TokenEntityJsonMapper tokenJsonMapper;
  private final StagesEntityJsonMapper stagesEntityJsonMapper;
    private final PointsEntityJsonMapper pointsEntityJsonMapper;
  private final UserEntityJsonMapper userJsonMapper;
    private final TeamsEntityJsonMapper teamsEntityJsonMapper;
    private final CheckInEntityJsonMapper checkInJsonMapper;
    private final ReportsEntityJsonMapper reportsEntityJsonMapper;
    private final SegmentsEntityJsonMapper segmentsEntityJsonMapper;
    private final StagesInfoEntityJsonMapper stagesInfoEntityJsonMapper;
    private final AmenityPointsEntityJsonMapper amenityPointsEntityJsonMapper;
    private final NameListEntityJsonMapper nameListEntityJsonMapper;
    private final LandUseEntityJsonMapper landUseEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   */
  public RestApiImpl(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }


    this.context = context.getApplicationContext();
    this.tokenJsonMapper = new TokenEntityJsonMapper();
    this.userJsonMapper = new UserEntityJsonMapper();
      this.stagesEntityJsonMapper = new StagesEntityJsonMapper();
      this.pointsEntityJsonMapper = new PointsEntityJsonMapper();
      this.teamsEntityJsonMapper = new TeamsEntityJsonMapper();
      this.checkInJsonMapper = new CheckInEntityJsonMapper();
      this.reportsEntityJsonMapper = new ReportsEntityJsonMapper();
      this.segmentsEntityJsonMapper = new SegmentsEntityJsonMapper();
      this.stagesInfoEntityJsonMapper = new StagesInfoEntityJsonMapper();
      this.amenityPointsEntityJsonMapper = new AmenityPointsEntityJsonMapper();
      this.nameListEntityJsonMapper = new NameListEntityJsonMapper();
      this.landUseEntityJsonMapper = new LandUseEntityJsonMapper();
  }


    private StagesEntity getStagesEntity() {
        Timber.i("PR: Začal som čitanie zo súboru");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("stage_data.json")));


            String mLine;
            StringBuilder json = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                json.append(mLine);
            }
            Timber.i("PR: Skončil som čitanie zo súboru");
            return stagesEntityJsonMapper.transformStagesEntity(json.toString());
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
    }


    private TeamsEntity getTeamsEntity() {
        Timber.i("PR: Začal som čitanie zo súboru");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("teams.json")));


            String mLine;
            StringBuilder json = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                json.append(mLine);
            }
            Timber.i("PR: Skončil som čitanie zo súboru");
            return teamsEntityJsonMapper.transformTeamsEntity(json.toString());
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
    }

    private StagesInfoEntity getStagesInfoEntity() {
        Timber.i("PR: Začal som čitanie zo súboru");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("stage_info.json")));


            String mLine;
            StringBuilder json = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                json.append(mLine);
            }
            Timber.i("PR: Skončil som čitanie zo súboru");
            return stagesInfoEntityJsonMapper.transformStagesInfoEntity(json.toString());
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
    }

    private PointsEntity getPointsEntity() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("track.json")));


            String mLine;
            StringBuilder json = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                json.append(mLine);
            }
           return pointsEntityJsonMapper.transformPointsEntity(json.toString());
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return  null;
    }

    /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
            (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    Timber.d("Internet connection - %s", isConnected);

    return isConnected;
  }

  @Override
  public Observable<StagesEntity> getStages() {
      return Observable.create(subscriber -> {
          subscriber.onNext(getStagesEntity());
          subscriber.onCompleted();
      });
  }

  @Override
  public Observable<PointsEntity> getTrack() {
    return Observable.create(subscriber -> {
        subscriber.onNext(getPointsEntity());
        subscriber.onCompleted();
    });
  }

  @Override
  public Observable<TokenEntity> getTokenFromEmail(String email, String password) {
    return Observable.create(new Observable.OnSubscribe<TokenEntity>() {

      @Override
      public void call(Subscriber<? super TokenEntity> subscriber) {
        if (isThereInternetConnection()) {
          try {
            Response response = postAuthWithEmail(email, password);
            if (response != null) {
              TokenEntity tokenEntity = new TokenEntity();
              if (response.networkResponse().code() == 200) {
                String tokenJsonResponse = response.body().string();
                tokenEntity = tokenJsonMapper.transformTokenEntity(tokenJsonResponse);
                Timber.d("getTokenFromEmail - sk.pixwell.therun.domain.Token - %s", tokenEntity.getToken());
              }else if(response.code() == 406){
                  subscriber.onError(new UpdateException());
              }
              tokenEntity.setStatusCode(response.networkResponse().code());
              Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
              subscriber.onNext(tokenEntity);
              subscriber.onCompleted();
            } else {
              Timber.e("Get token from email response is null");
              subscriber.onError(new NetworkConnectionException());
            }
          } catch (Exception e) {
            Timber.e(e, "Get token from email");
            subscriber.onError(new NetworkConnectionException(e.getCause()));
          }
        } else {
          Timber.e("Get token from email, no connection");
          subscriber.onError(new NetworkConnectionException());
        }
      }

      private Response postAuthWithEmail(String email, String password) throws MalformedURLException {
        final String EMAIL_KEY = "email";
        final String PASSWORD_KEY = "password";

        ApiConnection apiConnection = ApiConnection.createPOST(API_URL_POST_AUTH_LOGIN, context);

        HashMap<String, Object> body = new HashMap<>();
        body.put(EMAIL_KEY, email);
        body.put(PASSWORD_KEY, password);

        apiConnection.createBody(body);

        Timber.d(apiConnection.toString());

        return apiConnection.requestSyncCall();
      }

    });
  }

  @Override
  public Observable<UserEntity> getUserFromToken(TokenEntity token) {
    return Observable.create(new Observable.OnSubscribe<UserEntity>() {
      @Override
      public void call(Subscriber<? super UserEntity> subscriber) {
        if(token.getToken().equals("")){
            subscriber.onError(new NetworkConnectionException());
        }else {
            if (isThereInternetConnection()) {
                try {
                    Response response = getUserFromTokenApi(token);
                    if (response != null && response.code() == 200) {
                        String userFromToken = response.body().string();
                        UserEntity userEntity = userJsonMapper.transformUserEntity(userFromToken);
                        Timber.d("getUserFromToken - User - %s", userEntity.toString());
                        subscriber.onNext(userEntity);
                        subscriber.onCompleted();
                    } else if (response.code() == 401) {
                        subscriber.onError(new UnautorizedUserException());
                    } else if (response.code() == 406) {
                        subscriber.onError(new UpdateException());
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        }
      }

      private Response getUserFromTokenApi(TokenEntity token) throws MalformedURLException{
        ApiConnection apiConnection = ApiConnection.createGET(API_URL_GET_ME, context);


        HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
        headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


        apiConnection.createHeaders(headers);

        Timber.d(apiConnection.toString());

        return apiConnection.requestSyncCall();
      }
    });
  }

    @Override
    public Observable<TeamsEntity> getTeams() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getTeamsEntity());
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<Boolean> checkInStage(TokenEntity tokenEntity, StageInfo stage) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = checkInStage(tokenEntity, stage);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            } else if(response.code() == 400) {
                                JSONObject jsonObject= new JSONObject (response.body().string());
                                String message = jsonObject.getString("message");
                                subscriber.onError(new UnVisibleException(message));
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException(stage));
                }
            }

            private Response checkInStage(TokenEntity tokenEntity, StageInfo stage) throws MalformedURLException {
                final String CODE = "code";
                final String STARTED_AT = "started_at";

                ApiConnection apiConnection = ApiConnection.createPOST(API_URL_CHECK_IN_SEGMENT + String.valueOf(stage.getId()) + API_URL_CHECK_IN_SEGMENT_END, context);

                HashMap<String, Object> body = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date now = new Date();
                body.put(CODE, stage.getQrString());
                body.put(STARTED_AT, null);

                apiConnection.createBody(body);

                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, tokenEntity.getToken());
                apiConnection.createHeaders(headers);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<CheckInEntity> getCheckInInfo(TokenEntity token) {
        return Observable.create(new Observable.OnSubscribe<CheckInEntity>() {
            @Override
            public void call(Subscriber<? super CheckInEntity> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getCheckInInfo(token);
                        if (response != null) {
                            if(response.code() == 200){
                                String checkIn = response.body().string();
                                CheckInEntity checkInEntity = checkInJsonMapper.transformCheckInEntity(checkIn);
                                subscriber.onNext(checkInEntity);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        }else if(response.code() == 401){
                            getTokenFromEmail("","");
                        }else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getCheckInInfo(TokenEntity token) throws MalformedURLException{
                ApiConnection apiConnection = ApiConnection.createGET(API_URL_GET_CHECK_IN_INFO, context);


                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


                apiConnection.createHeaders(headers);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }
        });
    }

    @Override
    public Observable<Boolean> checkInOnStart(TokenEntity token, Date time, String type) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = checkInOnStart(token, time, type);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response checkInOnStart(TokenEntity token, Date time, String type) throws MalformedURLException {
                final String TIME = "checked_at";
                final String TYPE = "type";

                ApiConnection apiConnection = ApiConnection.createPOST(API_URL_CHECK_IN_ON_START, context);

                HashMap<String, Object> body = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String strDate = dateFormat.format(time);
                body.put(TIME, strDate);
                body.put(TYPE, type);

                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


                apiConnection.createHeaders(headers);

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<Boolean> startRun(TokenEntity token, Date time, String type) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = startRun(token, time, type);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response startRun(TokenEntity token, Date time, String type) throws MalformedURLException {
                final String TIME = "started_at";
                final String TYPE = "type";

                ApiConnection apiConnection = ApiConnection.createPOST(API_URL_START_RUN, context);

                HashMap<String, Object> body = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String strDate = dateFormat.format(time);
                body.put(TIME, strDate);
                body.put(TYPE, type);

                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


                apiConnection.createHeaders(headers);

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<Boolean> uploadSegmentTime(TokenEntity tokenEntity, List<StageInfo> noUploadStages) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        int number = 0;
                        for(StageInfo data : noUploadStages) {
                            Response response = uploadSegmentTime(tokenEntity,data);
                            if (response != null) {
                                if (response.networkResponse().code() == 200) {
                                    number++;
                                    Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                } else if (response.code() == 406) {
                                    subscriber.onError(new UpdateException());
                                } else if (response.code() == 400) {
                                    number++;
                                }
                            } else {
                                Timber.e("Get token from email response is null");
                                subscriber.onError(new NetworkConnectionException());
                            }
                        }
                        if(number == noUploadStages.size()){
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        }else {
                            subscriber.onNext(false);
                            subscriber.onCompleted();
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response uploadSegmentTime(TokenEntity tokenEntity, StageInfo noUploadStage) throws MalformedURLException {
                final String CODE = "code";
                final String STARTED_AT = "started_at";

                ApiConnection apiConnection = ApiConnection.createPOST(API_URL_CHECK_IN_SEGMENT + String.valueOf(noUploadStage.getId()) + API_URL_CHECK_IN_SEGMENT_END, context);

                HashMap<String, Object> body = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date now = noUploadStage.getTime();
                body.put(CODE, noUploadStage.getQrString());
                body.put(STARTED_AT, dateFormat.format(now).toString());

                apiConnection.createBody(body);

                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, tokenEntity.getToken());

                apiConnection.createHeaders(headers);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<ReportsEntity> getReports(TokenEntity token) {
        return Observable.create(new Observable.OnSubscribe<ReportsEntity>() {
            @Override
            public void call(Subscriber<? super ReportsEntity> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getReports(token);
                        if (response != null) {
                            if(response.code() == 200){
                                String reportsJson = response.body().string();
                                ReportsEntity reportsEntity = reportsEntityJsonMapper.transformReportsEntity(reportsJson);
                                subscriber.onNext(reportsEntity);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        }else if(response.code() == 401){
                            getTokenFromEmail("","");
                        }else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getReports(TokenEntity token) throws MalformedURLException{
                ApiConnection apiConnection = ApiConnection.createGET(API_URL_REPORTS, context);


                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


                apiConnection.createHeaders(headers);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }
        });
    }

    @Override
    public Observable<SegmentsEntity> getTeamSegments(TokenEntity token) {
        return Observable.create(new Observable.OnSubscribe<SegmentsEntity>() {
            @Override
            public void call(Subscriber<? super SegmentsEntity> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getTeamSegments(token);
                        if (response != null) {
                            if(response.code() == 200){
                                String segmentsJson = response.body().string();
                                SegmentsEntity segmentsEntity = segmentsEntityJsonMapper.transformSegmentsEntity(segmentsJson);
                                subscriber.onNext(segmentsEntity);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        }else if(response.code() == 401){
                            getTokenFromEmail("","");
                        }else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getTeamSegments(TokenEntity token) throws MalformedURLException{
                ApiConnection apiConnection = ApiConnection.createGET(API_URL_SEGMENTS, context);


                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


                apiConnection.createHeaders(headers);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }
        });
    }

    @Override
    public Observable<StagesInfoEntity> getStagesInfo(TokenEntity transform)  {
        return Observable.create(subscriber -> {
            StagesInfoEntity stagesInfoEntity = getStagesInfoEntity();
            subscriber.onNext(stagesInfoEntity);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<StagesInfoEntity> geEndInInfo() {
        return Observable.create(subscriber -> {
            StagesInfoEntity stagesInfoEntity = getEndInfoEntity();
            subscriber.onNext(stagesInfoEntity);
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<Boolean> endRun(TokenEntity token, Date time, String type) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = endRun(token, time, type);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                subscriber.onNext(true);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response endRun(TokenEntity token, Date time, String type) throws MalformedURLException {
                final String TIME = "finished_at";
                final String TYPE = "type";

                ApiConnection apiConnection = ApiConnection.createPOST(API_URL_END_RUN, context);

                HashMap<String, Object> body = new HashMap<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String strDate = dateFormat.format(time);
                body.put(TIME, strDate);
                body.put(TYPE, type);

                HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, token.getToken());


                apiConnection.createHeaders(headers);

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<AmenityPoints> getAmenity(TokenEntity transform, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage stageInfo) {
        return Observable.create(new Observable.OnSubscribe<AmenityPoints>() {

            @Override
            public void call(Subscriber<? super AmenityPoints> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getAmenity(transform, clinic, dentist, doctors, hospital, pharmacy, distance, stageInfo);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                String responseString = response.body().string();
                                AmenityPoints amenityPoints = amenityPointsEntityJsonMapper.transformPointsEntity(responseString);
                                subscriber.onNext(amenityPoints);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getAmenity(TokenEntity tokenEntity, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage stageInfo) throws MalformedURLException {
                final String POINTS = "points";

                ApiConnection apiConnection = ApiConnection.createPOST("http://10.0.2.2:8000/api/v1/health_car" , context);

                List<PostPoint> points = new ArrayList<>();
                for (PointOfStage data : stageInfo.getPoints()) {
                    PostPoint point = new PostPoint();
                    point.lat = data.getLatitude();
                    point.lng = data.getLongitude();
                    points.add(point);
                }
                String type = "";
                if(clinic) {
                    if (type.equals("")) {
                        type = type + "clinic";
                    } else {
                        type = type + ",clinic";
                    }
                }

                if(dentist) {
                    if (type.equals("")) {
                        type = type + "dentist";
                    } else {
                        type = type + ",dentist";
                    }
                }

                if(doctors) {
                    if (type.equals("")) {
                        type = type + "doctors";
                    } else {
                        type = type + ",doctors";
                    }
                }

                if(hospital) {
                    if (type.equals("")) {
                        type = type + "hospital";
                    } else {
                        type = type + ",hospital";
                    }
                }

                if(pharmacy) {
                    if (type.equals("")) {
                        type = type + "pharmacy";
                    } else {
                        type = type + ",pharmacy";
                    }
                }

                HashMap<String, Object> body = new HashMap<>();
                Gson gson = new Gson();
                String data = gson.toJson(points);
                JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
                body.put(POINTS, jsonArray);
                body.put("type", type);
                body.put("distance", distance);

                /*HashMap<ApiConnection.HeaderType, String> headers = new HashMap<>();
                headers.put(ApiConnection.HeaderType.AUTHORIZATION, tokenEntity.getToken());

                apiConnection.createHeaders(headers);*/

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<List<String>> getKraje(Stage points) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {

            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getKraje(points);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                String responseString = response.body().string();
                                NameListEntity nameListEntity = nameListEntityJsonMapper.transform(responseString);
                                List<String> result = new ArrayList<>();
                                for (NameListEntity.Result entity: nameListEntity.result) {
                                    result.add(entity.name);
                                }
                                subscriber.onNext(result);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getKraje(Stage stageInfo) throws MalformedURLException {
                final String POINTS = "points";

                ApiConnection apiConnection = ApiConnection.createPOST("http://10.0.2.2:8000/api/v1/administrative" , context);

                List<PostPoint> points = new ArrayList<>();
                for (PointOfStage data : stageInfo.getPoints()) {
                    PostPoint point = new PostPoint();
                    point.lat = data.getLatitude();
                    point.lng = data.getLongitude();
                    points.add(point);
                }

                HashMap<String, Object> body = new HashMap<>();
                Gson gson = new Gson();
                String data = gson.toJson(points);
                JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
                body.put(POINTS, jsonArray);
                body.put("administrative", "kraj");

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<List<String>> getOkresy(Stage points) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getOkresy(points);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                String responseString = response.body().string();
                                NameListEntity nameListEntity = nameListEntityJsonMapper.transform(responseString);
                                List<String> result = new ArrayList<>();
                                for (NameListEntity.Result entity: nameListEntity.result) {
                                    result.add(entity.name);
                                }
                                subscriber.onNext(result);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getOkresy(Stage stageInfo) throws MalformedURLException {
                final String POINTS = "points";

                ApiConnection apiConnection = ApiConnection.createPOST("http://10.0.2.2:8000/api/v1/administrative" , context);

                List<PostPoint> points = new ArrayList<>();
                for (PointOfStage data : stageInfo.getPoints()) {
                    PostPoint point = new PostPoint();
                    point.lat = data.getLatitude();
                    point.lng = data.getLongitude();
                    points.add(point);
                }

                HashMap<String, Object> body = new HashMap<>();
                Gson gson = new Gson();
                String data = gson.toJson(points);
                JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
                body.put(POINTS, jsonArray);
                body.put("administrative", "okres");

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<List<Landuse>> getLandUse(Stage points) {
        return Observable.create(new Observable.OnSubscribe<List<Landuse>>() {
            @Override
            public void call(Subscriber<? super List<Landuse>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getLandUse(points);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                String responseString = response.body().string();
                                LandUseEntity landUseEntity = landUseEntityJsonMapper.transform(responseString);
                                List<Landuse> result = new ArrayList<>();
                                for (LandUseEntity.Result entity: landUseEntity.result) {
                                    Landuse landuse = new Landuse();
                                    landuse.landuse = control(entity.landuse);
                                    landuse.length = Float.valueOf(entity.length);
                                    if(landuse.length > 0)
                                        result.add(landuse);
                                }
                                subscriber.onNext(result);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getLandUse(Stage stageInfo) throws MalformedURLException {
                final String POINTS = "points";

                ApiConnection apiConnection = ApiConnection.createPOST("http://10.0.2.2:8000/api/v1/land_use" , context);

                List<PostPoint> points = new ArrayList<>();
                for (PointOfStage data : stageInfo.getPoints()) {
                    PostPoint point = new PostPoint();
                    point.lat = data.getLatitude();
                    point.lng = data.getLongitude();
                    points.add(point);
                }

                HashMap<String, Object> body = new HashMap<>();
                Gson gson = new Gson();
                String data = gson.toJson(points);
                JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
                body.put(POINTS, jsonArray);
                body.put("administrative", "okres");

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    @Override
    public Observable<List<CastlePolygon>> getCastlePolygon(int distance, Double lat, Double lng) {
        return Observable.create(new Observable.OnSubscribe<List<CastlePolygon>>() {
            @Override
            public void call(Subscriber<? super List<CastlePolygon>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        Response response = getCastlePolygon(distance, lat, lng);
                        if (response != null) {
                            if (response.networkResponse().code() == 200) {
                                Timber.d("getTokenFromEmail - Status code - %d", response.networkResponse().code());
                                String responseString = response.body().string();

                                JSONObject jsonObject = new JSONObject(responseString);
                                JSONArray jsonArray = jsonObject.getJSONArray("features");
                                List<CastlePolygon> castlePolygons = new ArrayList<>();
                                for(int j = 0; j < jsonArray.length(); j ++){
                                    CastlePolygon castlePolygon = new CastlePolygon();
                                    JSONObject object = (JSONObject) jsonArray.get(j);
                                    castlePolygon.name = object.getJSONObject("properties").getString("name");
                                    JSONArray coordinate = (JSONArray) ( (JSONArray) object.getJSONObject("geometry").getJSONArray("coordinates").get(0)).get(0);
                                    for(int i = 0; i < coordinate.length(); i++){
                                        CastlePolygon.Coordinates castlePolygonCoord = new CastlePolygon.Coordinates();
                                        JSONArray coordinates = coordinate.getJSONArray(i);
                                        castlePolygonCoord.lat = (Double) coordinates.get(1);
                                        castlePolygonCoord.lng = (Double) coordinates.get(0);
                                        castlePolygon.polygon.add(castlePolygonCoord);
                                    }
                                    castlePolygons.add(castlePolygon);
                                }
                                subscriber.onNext(castlePolygons);
                                subscriber.onCompleted();
                            }else if(response.code() == 406){
                                subscriber.onError(new UpdateException());
                            }
                        } else {
                            Timber.e("Get token from email response is null");
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        Timber.e(e, "Get token from email");
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    Timber.e("Get token from email, no connection");
                    subscriber.onError(new NetworkConnectionException());
                }
            }

            private Response getCastlePolygon(int distance, Double lat, Double lng) throws MalformedURLException {

                ApiConnection apiConnection = ApiConnection.createPOST("http://10.0.2.2:8000/api/v1/castle/poly/1" , context);

                HashMap<String, Object> body = new HashMap<>();
                body.put("lat", lat);
                body.put("lng", lng);

                apiConnection.createBody(body);

                Timber.d(apiConnection.toString());

                return apiConnection.requestSyncCall();
            }

        });
    }

    public String control(String value){
      switch(value){
          case "fishfarm":
              return "Rybia farma";
          case "winter_sports":
              return "Zimné športy";
          case "spa":
              return "Kúpele";
          case "depot":
              return "Depo";
          case "institutional":
              return "Industry";
          case "churchyard":
              return "Cintorín";
          case "railway":
              return "Železnice";
          case "traffic_island":
              return "Dopravný mostík";
          case "brownfield":
              return "Nevyuživané pole";
          case "logging":
              return "Ťažobná plocha(drevo)";
          case "quarry":
              return "Lom";
          case "driving school training ground":
              return "Školiaca plocha pre autoškoly";
          case "aquaculture":
              return "Aqua kultúra";
          case "storage":
              return  "Sklad";
          case "reservoir":
              return "Nádrž";
          case "village_green":
              return "Obecná zeleň";
          case "grass":
              return "Tráva";
          case "dung-yard":
              return "Honjisko";
          case "allotments":
              return "Pridelená záhrada";
          case "vineyard":
              return "Vinice";
          case "industrial":
              return "Priemysel";
          case "landfill":
              return "Skládky";
          case "greenfield":
              return "Zelené pole";
          case "plant_nursery":
              return "Rastlinná škôlka";
          case "leisure":
              return "Voľno";
          case "piste":
              return "Zjazdová dráha";
          case "military":
              return "Vojenská časť";
          case "civic_admin":
              return "Vládne miesto";
          case "highway":
              return "Ďialnica";
          case "garden":
              return "Záhrada";
          case "observatory":
              return "Hvezdáreň";
          case "commercial":
              return "Komerčné plochy";
          case "farmland":
              return "Farmárske pole";
          case "residential":
              return "Obytná časť";
          case "railway;highway":
              return "Železnice, ďalnice";
          case "greenhouse_horticulture":
              return "Skleníky";
          case "construction":
              return "Výstavba";
          case "pasture":
              return "Pasienok";
          case "cemetery":
              return "Cintorín";
          case "garages":
              return "Garáže";
          case "religious":
              return "Náboženské miesto";
          case "apiary":
              return "Včelín";
          case "school":
              return "Škola";
          case "orchard":
              return "Ovocný sad";
          case "retail":
              return "Maloobchod";
          case "hop_garden":
              return "Chmeľové pole";
          case "disused":
              return "Nepouživaný";
          case "basin":
              return "Kotlina";
          case "industrial;retail":
              return "Priemyselná, máloobchodná časť";
              case "farmyard":
              return "Dvor";
          case "meadow":
              return "Lúka";
          case "forest":
              return "Les";
          case "recreation_ground":
              return "Rekreačná časť";
          default:
              return "";
      }
    }

    private StagesInfoEntity getEndInfoEntity() {
        Timber.i("PR: Začal som čitanie zo súboru");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("end_beacon.json")));


            String mLine;
            StringBuilder json = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                json.append(mLine);
            }
            Timber.i("PR: Skončil som čitanie zo súboru");
            return stagesInfoEntityJsonMapper.transformStagesInfoEntity(json.toString());
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
    }
}

