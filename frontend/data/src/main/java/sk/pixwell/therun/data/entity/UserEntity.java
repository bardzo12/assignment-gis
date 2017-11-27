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
package sk.pixwell.therun.data.entity;

import com.google.gson.annotations.SerializedName;

import sk.pixwell.therun.data.entity.TeamEntity.TeamEntity;
import sk.pixwell.therun.data.entity.TeamsEntity.TeamsEntity;

/**
 * User Entity used in the data layer.
 */
public class UserEntity {

  @SerializedName("data")
  public Data data;

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public static class Data {
    @SerializedName("id")
    public int id;
    @SerializedName("first_name")
    public String first_name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("phone")
    public String phone;
    @SerializedName("dob_year")
    public String dob_year;
    @SerializedName("gender")
    public String gender;
    @SerializedName("tshirt_size")
    public String tshirt_size;
    @SerializedName("time_10km")
    public String time_10km;
    @SerializedName("is_captain")
    public boolean is_captain;
    @SerializedName("team")
    public TeamEntity team;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getFirst_name() {
      return first_name;
    }

    public void setFirst_name(String first_name) {
      this.first_name = first_name;
    }

    public String getLast_name() {
      return last_name;
    }

    public void setLast_name(String last_name) {
      this.last_name = last_name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getDob_year() {
      return dob_year;
    }

    public void setDob_year(String dob_year) {
      this.dob_year = dob_year;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public String getTshirt_size() {
      return tshirt_size;
    }

    public void setTshirt_size(String tshirt_size) {
      this.tshirt_size = tshirt_size;
    }

    public String getTime_10km() {
      return time_10km;
    }

    public void setTime_10km(String time_10km) {
      this.time_10km = time_10km;
    }

    public boolean is_captain() {
      return is_captain;
    }

    public void setIs_captain(boolean is_captain) {
      this.is_captain = is_captain;
    }

    public TeamEntity getTeam() {
      return team;
    }

    public void setTeam(TeamEntity team) {
      this.team = team;
    }
  }
}