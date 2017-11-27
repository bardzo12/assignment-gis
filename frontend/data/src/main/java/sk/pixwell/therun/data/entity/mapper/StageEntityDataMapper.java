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
package sk.pixwell.therun.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import sk.pixwell.therun.data.entity.StageListEntitity;
import sk.pixwell.therun.data.entity.UserEntity;
import sk.pixwell.therun.domain.Stage;
import sk.pixwell.therun.domain.User;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class StageEntityDataMapper {

  @Inject
  StageEntityDataMapper() {}

  public List<Stage> transform(StageListEntitity stageEntity) {

    List<Stage> resultList = new ArrayList<>();
     /*user = null;
    if (userEntity != null) {
      user = new User(userEntity.getUserId());
      user.setCoverUrl(userEntity.getCoverUrl());
      user.setFullName(userEntity.getFullname());
      user.setDescription(userEntity.getDescription());
      user.setFollowers(userEntity.getFollowers());
      user.setEmail(userEntity.getEmail());
    }*/
    return resultList;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public List<User> transform(Collection<UserEntity> userEntityCollection) {
    final List<User> userList = new ArrayList<>(20);
    /*for (UserEntity userEntity : userEntityCollection) {
      final User user = transform(userEntity);
      if (user != null) {
        userList.add(user);
      }
    }*/
    return userList;
  }
}
