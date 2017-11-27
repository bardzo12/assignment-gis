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

import sk.pixwell.therun.data.entity.TeamEntity.mapper.TeamEntityDataMapper;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.mapper.TeamsEntityDataMapper;
import sk.pixwell.therun.data.entity.UserEntity;
import sk.pixwell.therun.domain.Runner;
import sk.pixwell.therun.domain.Team;
import sk.pixwell.therun.domain.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class UserEntityDataMapper {

  @Inject
  UserEntityDataMapper() {}

  /**
   * Transform a {@link UserEntity} into an {@link Runner}.
   *
   * @param userEntity Object to be transformed.
   * @return {@link Runner} if valid {@link UserEntity} otherwise null.
   */
  public static Runner transform(UserEntity userEntity) {
    Runner user = null;
    if (userEntity != null) {
        user = new Runner();
        user.setFirstName(userEntity.getData().getFirst_name());
        user.setId(userEntity.getData().getId());
        user.setLastName(userEntity.getData().getLast_name());
        Team team = TeamEntityDataMapper.transform(userEntity.getData().getTeam());
        user.setTeam(team);
    }
    return user;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public List<User> transform(Collection<UserEntity> userEntityCollection) {
    final List<User> userList = new ArrayList<>(20);
    for (UserEntity userEntity : userEntityCollection) {
      /*final User user = transform(userEntity);
      if (user != null) {
        userList.add(user);
      }*/
    }
    return userList;
  }
}
