package sk.pixwell.therun.data.sharedprefs;

import java.util.Date;
import java.util.List;

import rx.Observable;
import sk.pixwell.therun.data.entity.StageCheckIn;
import sk.pixwell.therun.data.entity.StagesInfoEntity.StagesInfoEntity;
import sk.pixwell.therun.data.entity.token.TokenEntity;
import sk.pixwell.therun.domain.StageInfo;

/**
 * Created by Tomáš Baránek on 15.4.2017.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */

public interface ISharedPreferences {
    void putToken(TokenEntity token);
    Observable<TokenEntity> getToken();
    Observable<Boolean> changeFavoriteTeams(List<Integer> list);
    Observable<List<Integer>> getFavoriteTeams();
    void addCheckStage(StageInfo stagee);
    List<StageInfo> getNoUploadStages();
    Observable clearToken();
    Observable<List<StageInfo>> getNoUploadStagesObservable();
}
