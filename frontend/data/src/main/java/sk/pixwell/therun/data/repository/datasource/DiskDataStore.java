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
package sk.pixwell.therun.data.repository.datasource;


import java.util.Date;
import java.util.List;

import rx.Observable;
import sk.pixwell.therun.data.cache.Cache;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.data.entity.CheckInEntity.CheckInEntity;
import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.SegmentsEntity.SegmentsEntity;
import sk.pixwell.therun.data.entity.StageCheckIn;
import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.UserEntity;
import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.data.sharedprefs.ISharedPreferences;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;

/**
 * {@link DataStore} implementation based on file system data store.
 */
class DiskDataStore implements DataStore {

    private final Cache cache;
    private final ISharedPreferences sharedPreferences;

    /**
     * Construct a {@link DataStore} based file system data store.
     *
     * @param cache A {@link Cache} to cache data retrieved from the api.
     */
    DiskDataStore(Cache cache, ISharedPreferences sharedPreferences) {
        this.cache = cache;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Observable<StagesEntity> getStages() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<PointsEntity> getTrack() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<TokenEntity> authWithEmail(String email, String password) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<TokenEntity> getToken() {
        return sharedPreferences.getToken();
    }

    @Override
    public Observable<UserEntity> getUserFromToken(TokenEntity token) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<TeamsEntity> getTeams() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<Boolean> checkInStage(TokenEntity tokenEntity, StageInfo stage) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<CheckInEntity> getCheckInInfo(TokenEntity token) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<Boolean> checkInOnStart(TokenEntity transform, Date time, String type) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<Boolean> startRun(TokenEntity transform, Date time, String type) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<Boolean> changeFavoriteTeams(List<Integer> list) {
        return sharedPreferences.changeFavoriteTeams(list);
    }

    @Override
    public Observable<List<Integer>> getFavoriteTeams() {
        return sharedPreferences.getFavoriteTeams();
    }

    public List<StageInfo> getNoUploadStages(){
        return sharedPreferences.getNoUploadStages();
    }

    @Override
    public Observable<Boolean> uploadSegmentTime(TokenEntity token, List<StageInfo> noUploadStages) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<ReportsEntity> getReports(TokenEntity transform) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<SegmentsEntity> getTeamSegments(TokenEntity transform) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable clearToken() {
        return sharedPreferences.clearToken();
    }

    @Override
    public Observable<StagesInfoEntity> getStagesInfo(TokenEntity transform) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<StageInfo>> getOfflineCheckIn() {
        return sharedPreferences.getNoUploadStagesObservable();
    }

    @Override
    public Observable<StagesInfoEntity> geEndInInfo() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<Boolean> endRun(TokenEntity transform, Date time, String type) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<AmenityPoints> getAmenity(TokenEntity transform, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int aBoolean, Stage stageInfo) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<String>> getKraje(Stage points) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<String>> getOkresy(Stage points) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<Landuse>> getLandUse(Stage points) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<List<CastlePolygon>> getCastlePolygon(int distance, Double lat, Double lng) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

}
