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
package sk.pixwell.therun.domain.repository;

import java.util.List;

import rx.Observable;
import sk.pixwell.therun.domain.Stage;

/**
 * Interface that represents a Repository for getting {@link Stage} related data.
 */
public interface MapRepository {
  /**
   * Get an {@link Observable} which will emit a List of {@link Stage}.
   */
  Observable<List<Stage>> stages();

}
