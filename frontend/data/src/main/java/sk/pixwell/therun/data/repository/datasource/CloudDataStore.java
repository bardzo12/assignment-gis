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
package sk.pixwell.therun.data.repository.datasource;


import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import sk.pixwell.therun.data.cache.Cache;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.data.entity.CheckInEntity.CheckInEntity;
import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.SegmentsEntity.SegmentsEntity;
import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.UserEntity;
import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.data.exception.NetworkConnectionException;
import sk.pixwell.therun.data.net.RestApi;
import sk.pixwell.therun.data.sharedprefs.ISharedPreferences;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;
import timber.log.Timber;

/**
 * {@link DataStore} implementation based on connections to the api (Cloud).
 */
class CloudDataStore implements DataStore {

    private final RestApi restApi;
    private final Cache cache;
    private final ISharedPreferences sharedPreferences;


    private Action1<Throwable> errorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            if(throwable instanceof NetworkConnectionException){
                NetworkConnectionException exception = (NetworkConnectionException) throwable;
                sharedPreferences.addCheckStage(exception.getStage());
            }
            Timber.d("error Action");
        }
    };

    private final Action1<TokenEntity> saveToSharedPrefToken = new Action1<TokenEntity>() {
        @Override
        public void call(TokenEntity token) {
            sharedPreferences.putToken(token);
        }
    };


    /**
     * Construct a {@link DataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param cache A {@link Cache} to cache data retrieved from the api.
     */
    CloudDataStore(RestApi restApi, Cache cache, ISharedPreferences sharedPreferences) {
        this.restApi = restApi;
        this.cache = cache;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Observable<StagesEntity> getStages() {
        return this.restApi.getStages();
    }

    @Override
    public Observable<PointsEntity> getTrack() {
        return this.restApi.getTrack();
    }

    @Override
    public Observable<TokenEntity> authWithEmail(String email, String password) {
        return this.restApi.getTokenFromEmail(email,password).doOnNext(saveToSharedPrefToken);
    }

    @Override
    public Observable<TokenEntity> getToken() {
        return null;
    }

    @Override
    public Observable<UserEntity> getUserFromToken(TokenEntity token) {
        return this.restApi.getUserFromToken(token);
                //.doOnNext(saveToCacheAction);
    }

    @Override
    public Observable<TeamsEntity> getTeams() {
        return this.restApi.getTeams();
    }

    @Override
    public Observable<Boolean> checkInStage(TokenEntity tokenEntity, StageInfo stage) {
        return this.restApi.checkInStage(tokenEntity, stage).doOnError(errorAction);
    }

    @Override
    public Observable<CheckInEntity> getCheckInInfo(TokenEntity token) {
        return this.restApi.getCheckInInfo(token);
    }

    @Override
    public Observable<Boolean> checkInOnStart(TokenEntity token, Date time, String type) {
        return this.restApi.checkInOnStart(token, time, type);
    }

    @Override
    public Observable<Boolean> startRun(TokenEntity token, Date time, String type) {
        return this.restApi.startRun(token, time, type);
    }

    @Override
    public Observable<Boolean> changeFavoriteTeams(List<Integer> list) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<Integer>> getFavoriteTeams() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public List<StageInfo> getNoUploadStages() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<Boolean> uploadSegmentTime(TokenEntity tokenEntity, List<StageInfo> noUploadStages) {
        return this.restApi.uploadSegmentTime(tokenEntity,noUploadStages);
    }

    @Override
    public Observable<ReportsEntity> getReports(TokenEntity transform) {
        return this.restApi.getReports(transform);
    }

    @Override
    public Observable<SegmentsEntity> getTeamSegments(TokenEntity transform) {
        return this.restApi.getTeamSegments(transform);
    }

    @Override
    public Observable clearToken() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<StagesInfoEntity> getStagesInfo(TokenEntity transform) {
        return this.restApi.getStagesInfo(transform);
    }

    @Override
    public Observable<List<StageInfo>> getOfflineCheckIn() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<StagesInfoEntity> geEndInInfo() {
        return this.restApi.geEndInInfo();
    }

    @Override
    public Observable<Boolean> endRun(TokenEntity token, Date time, String type) {
        return this.restApi.endRun(token, time, type);
    }

    @Override
    public Observable<AmenityPoints> getAmenity(TokenEntity transform, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage stageInfo) {
        return this.restApi.getAmenity(transform, clinic, dentist,doctors,hospital,pharmacy,distance, stageInfo);
    }

    @Override
    public Observable<List<String>> getKraje(Stage points) {
        return this.restApi.getKraje(points);
    }

    @Override
    public Observable<List<String>> getOkresy(Stage points) {
        return this.restApi.getOkresy(points);
    }

    @Override
    public Observable<List<Landuse>> getLandUse(Stage points) {
        return this.restApi.getLandUse(points);
    }

    @Override
    public Observable<List<CastlePolygon>> getCastlePolygon(int distance, Double lat, Double lng) {
        return this.restApi.getCastlePolygon(distance, lat, lng);
    }
}
