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
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface DataStore {

    Observable<StagesEntity> getStages();

    Observable<PointsEntity> getTrack();

    Observable<TokenEntity> authWithEmail(String email, String password);

    Observable<TokenEntity> getToken();

    Observable<UserEntity> getUserFromToken(TokenEntity token);

    Observable<TeamsEntity> getTeams();

    Observable<Boolean> checkInStage(TokenEntity tokenEntity, StageInfo stage);

    Observable<CheckInEntity> getCheckInInfo(TokenEntity token);

    Observable<Boolean> checkInOnStart(TokenEntity transform, Date time, String type);

    Observable<Boolean> startRun(TokenEntity transform, Date time, String type);

    Observable<Boolean> changeFavoriteTeams(List<Integer> list);

    Observable<List<Integer>> getFavoriteTeams();
    
    List<StageInfo> getNoUploadStages();

    Observable<Boolean> uploadSegmentTime(TokenEntity tokenEntity, List<StageInfo> noUploadStages);

    Observable<ReportsEntity> getReports(TokenEntity transform);

    Observable<SegmentsEntity> getTeamSegments(TokenEntity transform);

    Observable clearToken();

    Observable<StagesInfoEntity>  getStagesInfo(TokenEntity transform);

    Observable<List<StageInfo>> getOfflineCheckIn();

    Observable<StagesInfoEntity> geEndInInfo();

    Observable<Boolean> endRun(TokenEntity transform, Date time, String type);

    Observable<AmenityPoints> getAmenity(TokenEntity transform, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int aBoolean, Stage stageInfo);

    Observable<List<String>> getKraje(Stage points);

    Observable<List<String>> getOkresy(Stage points);

    Observable<List<Landuse>> getLandUse(Stage points);

    Observable<List<CastlePolygon>> getCastlePolygon(int distance, Double lat, Double lng);
}
