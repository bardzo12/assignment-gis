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

import java.util.Date;
import java.util.List;

import rx.Observable;
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
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.CheckIn;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    //production
    String API_BASE_URL = "https://therun.sk/api/";

    //dev
    //String API_BASE_URL = "https://therun.pixwell.io/api/";

    String API_URL_POST_AUTH_LOGIN = API_BASE_URL + "auth/token";
    String API_URL_GET_ME = API_BASE_URL + "me?include=team";
    String API_URL_GET_TEAMS = API_BASE_URL + "teams";
    String API_URL_CHECK_IN_SEGMENT = API_BASE_URL + "me/team/segments/";
    String API_URL_CHECK_IN_SEGMENT_END = "/start";
    String API_URL_GET_CHECK_IN_INFO = API_BASE_URL + "me/team/checkins";
    String API_URL_CHECK_IN_ON_START = API_BASE_URL + "me/team/checkins/check";
    String API_URL_START_RUN = API_BASE_URL + "me/team/checkins/start";
    String API_URL_END_RUN = API_BASE_URL + "me/team/checkins/finish";
    String API_URL_REPORTS = API_BASE_URL + "me/reports";
    String API_URL_SEGMENTS = API_BASE_URL + "me/team/segments";


  Observable<StagesEntity> getStages();

  Observable<PointsEntity> getTrack();

    Observable<TokenEntity> getTokenFromEmail(String email, String password);

  Observable<UserEntity> getUserFromToken(TokenEntity token);

    Observable<TeamsEntity> getTeams();

    Observable<Boolean> checkInStage(TokenEntity tokenEntity, StageInfo stage);

  Observable<CheckInEntity> getCheckInInfo(TokenEntity token);

    Observable<Boolean> checkInOnStart(TokenEntity token, Date time, String type);

    Observable<Boolean> startRun(TokenEntity token, Date time, String type);

  Observable<Boolean> uploadSegmentTime(TokenEntity tokenEntity, List<StageInfo> noUploadStages);

  Observable<ReportsEntity> getReports(TokenEntity transform);

    Observable<SegmentsEntity> getTeamSegments(TokenEntity transform);

    Observable<StagesInfoEntity> getStagesInfo(TokenEntity transform);
    Observable<StagesInfoEntity> geEndInInfo();

    Observable<Boolean> endRun(TokenEntity token, Date time, String type);

    Observable<AmenityPoints> getAmenity(TokenEntity transform, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage stageInfo);

    Observable<List<String>> getKraje(Stage points);

    Observable<List<String>> getOkresy(Stage points);

    Observable<List<Landuse>> getLandUse(Stage points);

    Observable<List<CastlePolygon>> getCastlePolygon(int distance, Double lat, Double lng);
}

