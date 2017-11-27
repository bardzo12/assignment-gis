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
package sk.pixwell.therun.domain;

import java.util.Date;
import java.util.List;

import rx.Observable;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public interface Repository {

    Observable<List<Stage>> getStages();

    Observable<List<PointOfStage>> getTrack();

    Observable<Integer> authWithEmail(String email, String password);

    Observable<Token> getToken();

    Observable<Runner> getUserByToken(Token token);

    Observable<List<Team>> getTeams();

    Observable<List<CheckIn>> getCheckInInfo(Token token);

    Observable<Boolean> checkInStage(Token token, StageInfo stage);

    Observable<Boolean> checkInOnStart(Token token, Date time, String type);

    Observable<Boolean> startRun(Token token, Date time, String type);

    Observable<Boolean> changeFavoriteTeams(List<Integer> list);

    Observable<List<Integer>> getFavoriteTeams();

    Observable<Boolean> uploadSegmentTime(Token token);

    Observable<List<Report>> getReports(Token token);

    Observable<List<Segment>> getTeamSegments(Token token);

    Observable clearToken();

    Observable<List<StageInfo>> getStagesInfo(Token token);

    Observable<List<StageInfo>> getOfflineCheckIn();

    Observable<List<StageInfo>> geEndInInfo();

    Observable<Boolean> endRun(Token token, Date time, String type);

    Observable<List<AmenityPoint>> getAmenity(Token token, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage stageInfo);

    Observable<List<String>> getKraje(Stage points);

    Observable<List<String>> getOkresy(Stage points);

    Observable<List<Landuse>> getLandUse(Stage points);

    Observable<List<CastlePolygon>> getCastlePolygon(Token token, int distance, Double lat, Double lng);
}
