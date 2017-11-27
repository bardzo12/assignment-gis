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

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import sk.pixwell.therun.data.cache.Cache;
import sk.pixwell.therun.data.net.RestApi;
import sk.pixwell.therun.data.net.RestApiImpl;
import sk.pixwell.therun.data.sharedprefs.ISharedPreferences;

/**
 * Factory that creates different implementations of {@link DataStore}.
 */
@Singleton
public class DataStoreFactory {

    private final Context context;
    private final Cache cache;
    private final ISharedPreferences sharedPreferences;

    @Inject
    public DataStoreFactory(@NonNull Context context, @NonNull Cache cache, @NonNull ISharedPreferences sharedPreferences) {
        this.context = context.getApplicationContext();
        this.cache = cache;
        this.sharedPreferences = sharedPreferences;
    }

    /**
    * Create {@link DataStore} from a user id.
    */
    public DataStore create(String userEmail) {
        DataStore dataStore;

        if (!this.cache.isExpired() && this.cache.isCached(userEmail)) {
            dataStore = new DiskDataStore(this.cache, sharedPreferences);
        }else {
            dataStore = createCloudDataStore();
        }

        return dataStore;
    }

    /**
     * Create {@link DataStore} to retrieve data from the Cloud.
     */
     public DataStore createCloudDataStore() {
         RestApi restApi = new RestApiImpl(this.context);

         return new CloudDataStore(restApi, this.cache, this.sharedPreferences);
     }

    public DataStore createDataStoreCategories() {
        DataStore dataStore;

        dataStore = createCloudDataStore();

        return dataStore;
    }

    public DataStore create(int mode){
        DataStore dataStore;
        dataStore = createCloudDataStore();

        return dataStore;
    }

    public DataStore createDiskDataStore() {
        return new DiskDataStore(this.cache, sharedPreferences);
    }
}
