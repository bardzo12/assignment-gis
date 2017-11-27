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
package sk.pixwell.therun.data.repository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.AmenityPoints;
import sk.pixwell.therun.data.entity.AmenityPointsEnity.mapper.AmenityPointsEntityDataMapper;
import sk.pixwell.therun.data.entity.CheckInEntity.CheckInEntity;
import sk.pixwell.therun.data.entity.CheckInEntity.mapper.CheckInDataMapper;
import sk.pixwell.therun.data.entity.ReportsEntity.ReportsEntity;
import sk.pixwell.therun.data.entity.ReportsEntity.mapper.ReportsEntityDataMapper;
import sk.pixwell.therun.data.entity.SegmentsEntity.SegmentsEntity;
import sk.pixwell.therun.data.entity.SegmentsEntity.mapper.SegmentsEntityDataMapper;
import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.data.entity.StagesInfoEntity.mapper.StagesInfoEntityDataMapper;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.mapper.TeamsEntityDataMapper;
import sk.pixwell.therun.data.entity.UserEntity;
import sk.pixwell.therun.data.entity.mapper.StageEntityDataMapper;
import sk.pixwell.therun.data.entity.mapper.StageEntityJsonMapper;
import sk.pixwell.therun.data.entity.mapper.UserEntityDataMapper;
import sk.pixwell.therun.data.entity.points.PointsEntity;
import sk.pixwell.therun.data.entity.points.mapper.PointsEntityDataMapper;
import sk.pixwell.therun.data.entity.stages.StagesEntity;
import sk.pixwell.therun.data.entity.stages.mapper.StagesEntityDataMapper;
import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.data.entity.token.mapper.TokenEntityDataMapper;
import sk.pixwell.therun.data.repository.datasource.DataStore;
import sk.pixwell.therun.data.repository.datasource.DataStoreFactory;
import sk.pixwell.therun.domain.AmenityPoint;
import sk.pixwell.therun.domain.CastlePolygon.CastlePolygon;
import sk.pixwell.therun.domain.CheckIn;
import sk.pixwell.therun.domain.Landuse;
import sk.pixwell.therun.domain.PointOfStage;
import sk.pixwell.therun.domain.Report;
import sk.pixwell.therun.domain.Repository;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.Segment;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.StageInfo;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.Token;


/**
 * {@link Repository} for retrieving user data.
 */
@Singleton
public class DataRepository implements Repository {

    private final DataStoreFactory dataStoreFactory;
    private Observable<List<Stage>> stages;

    private final Func1<TokenEntity, Integer> mappingFunctionTokenToStatusCode = new Func1<TokenEntity, Integer>() {
        @Override
        public Integer call(TokenEntity tokenEntity) {
            return tokenEntity.getStatusCode();
        }
    };

    private final Func1<StagesEntity, List<Stage>> mappingFunctionStages = new Func1<StagesEntity, List<Stage>>() {
        @Override
        public List<Stage> call(StagesEntity stagesEntity) {
            return StagesEntityDataMapper.transform(stagesEntity);
        }
    };

    private final Func1<SegmentsEntity, List<Segment>> mappingFunctionToTeamSegments = new Func1<SegmentsEntity, List<Segment>>() {
        @Override
        public List<Segment> call(SegmentsEntity segmentsEntity) {
            return SegmentsEntityDataMapper.transform(segmentsEntity);
        }
    };

    private final Func1<PointsEntity, List<PointOfStage>> mappingFunctionPoints = new Func1<PointsEntity, List<PointOfStage>>() {
        @Override
        public List<PointOfStage> call(PointsEntity pointsEntity) {
            return PointsEntityDataMapper.transform(pointsEntity);
        }
    };

    private final Func1<TokenEntity, Token> mappingFunctionToken = new Func1<TokenEntity, Token>() {
        @Override
        public Token call(TokenEntity tokenEntity) {
            return TokenEntityDataMapper.transform(tokenEntity);


        }
    };

    private final Func1<UserEntity, Runner> mappingFunctionToUser = new Func1<UserEntity, Runner>() {
        @Override
        public Runner call(UserEntity userEntity) {

            return UserEntityDataMapper.transform(userEntity);
        }
    };

    private final Func1<ReportsEntity, List<Report>> mappingFunctionToReports = new Func1<ReportsEntity, List<Report>>() {
        @Override
        public List<Report> call(ReportsEntity reportsEntity) {

            return ReportsEntityDataMapper.transform(reportsEntity);
        }
    };

    private final Func1<StagesInfoEntity, List<StageInfo>> mappingFunctionToStageInfoList = new Func1<StagesInfoEntity, List<StageInfo>>() {
        @Override
        public List<StageInfo> call(StagesInfoEntity stagesInfoEntity) {

            return StagesInfoEntityDataMapper.transform(stagesInfoEntity);
        }
    };

    private final Func1<CheckInEntity, List<CheckIn>> mappingFunctionToCheckIn = new Func1<CheckInEntity, List<CheckIn>>() {
        @Override
        public List<CheckIn> call(CheckInEntity checkInEntity) {

            return CheckInDataMapper.transform(checkInEntity);
        }
    };

    private final Func1<TeamsEntity, List<Team>> mappingFunctionToTeams = new Func1<TeamsEntity, List<Team>>() {
        @Override
        public List<Team> call(TeamsEntity teamsEntity) {

            return TeamsEntityDataMapper.transform(teamsEntity);
        }
    };

    private final Func1<AmenityPoints, List<AmenityPoint>> mappingFunctionToAmenity = AmenityPointsEntityDataMapper::transform;

    /**
     * Constructs a {@link Repository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     */
    @Inject
    public DataRepository(DataStoreFactory dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<List<Stage>> getStages() {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getStages().map(mappingFunctionStages);
    }

    @Override
    public Observable<List<PointOfStage>> getTrack() {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getTrack().map(mappingFunctionPoints);
    }

    @Override
    public Observable<Integer> authWithEmail(String email, String password) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();

        return dataStore.authWithEmail(email,password).map(mappingFunctionTokenToStatusCode);
    }

    @Override
    public Observable<Token> getToken() {
        DataStore dataStore = dataStoreFactory.createDiskDataStore();
        return dataStore.getToken().map(mappingFunctionToken);
    }

    @Override
    public Observable<Runner> getUserByToken(Token token) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getUserFromToken(TokenEntityDataMapper.transform(token)).map(mappingFunctionToUser);
    }

    @Override
    public Observable<List<Team>> getTeams() {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getTeams().map(mappingFunctionToTeams);
    }

    @Override
    public Observable<List<CheckIn>> getCheckInInfo(Token token) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getCheckInInfo(TokenEntityDataMapper.transform(token)).map(mappingFunctionToCheckIn);
    }

    @Override
    public Observable<Boolean> checkInStage(Token token, StageInfo stage) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.checkInStage(TokenEntityDataMapper.transform(token), stage);
    }

    @Override
    public Observable<Boolean> checkInOnStart(Token token, Date time, String type) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.checkInOnStart(TokenEntityDataMapper.transform(token), time, type);
    }

    @Override
    public Observable<Boolean> startRun(Token token, Date time, String type) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.startRun(TokenEntityDataMapper.transform(token), time, type);
    }

    @Override
    public Observable<Boolean> changeFavoriteTeams(List<Integer> list) {
        DataStore dataStore = dataStoreFactory.createDiskDataStore();
        return dataStore.changeFavoriteTeams(list);
    }

    @Override
    public Observable<List<Integer>> getFavoriteTeams() {
        DataStore dataStore = dataStoreFactory.createDiskDataStore();
        return dataStore.getFavoriteTeams();
    }

    @Override
    public Observable<Boolean> uploadSegmentTime(Token token) {
        DataStore dataStore = dataStoreFactory.createDiskDataStore();
        DataStore dataStore1 = dataStoreFactory.createCloudDataStore();
        return dataStore1.uploadSegmentTime(TokenEntityDataMapper.transform(token), dataStore.getNoUploadStages());
    }

    @Override
    public Observable<List<Report>> getReports(Token token) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getReports(TokenEntityDataMapper.transform(token)).map(mappingFunctionToReports);
    }

    @Override
    public Observable<List<Segment>> getTeamSegments(Token token) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getTeamSegments(TokenEntityDataMapper.transform(token)).map(mappingFunctionToTeamSegments);
    }

    @Override
    public Observable clearToken() {
        DataStore dataStore = dataStoreFactory.createDiskDataStore();
        return dataStore.clearToken();
    }

    @Override
    public Observable<List<StageInfo>> getStagesInfo(Token token) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getStagesInfo(TokenEntityDataMapper.transform(token)).map(mappingFunctionToStageInfoList);
    }

    @Override
    public Observable<List<StageInfo>> getOfflineCheckIn() {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getOfflineCheckIn();
    }

    @Override
    public Observable<List<StageInfo>> geEndInInfo() {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.geEndInInfo().map(mappingFunctionToStageInfoList);
    }

    @Override
    public Observable<Boolean> endRun(Token token, Date time, String type) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.endRun(TokenEntityDataMapper.transform(token), time, type);
    }

    @Override
    public Observable<List<AmenityPoint>> getAmenity(Token token, Boolean clinic, Boolean dentist, Boolean doctors, Boolean hospital, Boolean pharmacy, int distance, Stage stageInfo) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getAmenity(TokenEntityDataMapper.transform(token), clinic,dentist,doctors,hospital,pharmacy,distance,stageInfo).map(mappingFunctionToAmenity);
    }

    @Override
    public Observable<List<String>> getKraje(Stage points) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getKraje(points);
    }

    @Override
    public Observable<List<String>> getOkresy(Stage points) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getOkresy(points);
    }

    @Override
    public Observable<List<Landuse>> getLandUse(Stage points) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getLandUse(points);
    }

    @Override
    public Observable<List<CastlePolygon>> getCastlePolygon(Token token, int distance, Double lat, Double lng) {
        DataStore dataStore = dataStoreFactory.createCloudDataStore();
        return dataStore.getCastlePolygon(distance, lat, lng);
    }
}
