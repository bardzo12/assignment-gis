package sk.pixwell.therun.data.sharedprefs;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import sk.pixwell.therun.data.entity.StageCheckIn;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;

import static sk.pixwell.therun.data.sharedprefs.SharedPreferencesImpl.Settings.TOKEN;


/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public class SharedPreferencesImpl implements ISharedPreferences {

    private final Context context;
    private final String FAVORITE = "favorite_teams_id";
    private final String STAGE_CHECK_IN = "stage_check_in";

    @Inject
    public SharedPreferencesImpl(Context context){
        this.context = context;
    }

    @Override
    public void putToken(TokenEntity token) {
        SharedPreferencesUtils.savePreference(context, TOKEN.code, token.getToken());
    }

    @Override
    public Observable<TokenEntity> getToken() {



        return Observable.create(new Observable.OnSubscribe<TokenEntity>() {
            @Override
            public void call(Subscriber<? super TokenEntity> subscriber) {
                TokenEntity tokenEntity = new TokenEntity();
                String token = SharedPreferencesUtils.getStringFromPreferences(context, TOKEN.code, "");
                tokenEntity.setToken(token);
                subscriber.onNext(tokenEntity);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Boolean> changeFavoriteTeams(List<Integer> list) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {

                Gson gson = new Gson();
                String jsonIDs = gson.toJson(list);

                SharedPreferencesUtils.savePreference(context, FAVORITE, jsonIDs);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Integer>> getFavoriteTeams() {
        return Observable.create(new Observable.OnSubscribe<List<Integer>>() {
            @Override
            public void call(Subscriber<? super List<Integer>> subscriber) {

                Gson gson = new Gson();
                Type tokenEntityType = new TypeToken<List<Integer>>() {}.getType();
                List<Integer> listIDs =
                        gson.fromJson(SharedPreferencesUtils.getStringFromPreferences(context, FAVORITE, ""),
                                tokenEntityType);
                if(listIDs == null)
                    listIDs = new ArrayList<Integer>();
                subscriber.onNext(listIDs);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public void addCheckStage(StageInfo stage) {

        List<StageInfo> stageList = getNoUploadStages();

        boolean isThere = false;
        for(StageInfo data : stageList){
            if(data.getQrString().equals(stage.getQrString()))
                isThere = true;

        }

        if(!isThere) {
            stageList.add(stage);
            Gson gson = new Gson();
            String jsonIDs = gson.toJson(stageList);
            SharedPreferencesUtils.savePreference(context, STAGE_CHECK_IN, jsonIDs);
        }
    }

    @Override
    public List<StageInfo> getNoUploadStages() {
        Gson gson = new Gson();
        Type stagesType = new TypeToken<List<StageInfo>>() {}.getType();
        List<StageInfo> stageList =
                gson.fromJson(SharedPreferencesUtils.getStringFromPreferences(context, STAGE_CHECK_IN, ""),
                        stagesType);
        if(stageList == null)
            stageList = new ArrayList<>();
        return stageList;
    }

    @Override
    public Observable clearToken() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                SharedPreferencesUtils.removePreference(context, TOKEN.code);
            }
        });
    }

    @Override
    public Observable<List<StageInfo>> getNoUploadStagesObservable() {
        return Observable.create(subscriber -> {
            subscriber.onNext(getNoUploadStages());
            subscriber.onCompleted();
        });
    }


    enum Settings{

        TOKEN("sk.pixwell.therun.domain.Token");

        private final String code;

        Settings(String text){
            this.code = text;
        }
    }

}