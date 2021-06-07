/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.models;

import java.util.List;
import java.util.Objects;

public class Singer {

  private Long SingerId;
  private String FirstName;
  private String LastName;
  private List<Album> Albums;

  public void setSingerId(Long singerId) {
    SingerId = singerId;
  }

  public void setFirstName(String firstName) {
    FirstName = firstName;
  }

  public void setLastName(String lastName) {
    LastName = lastName;
  }

  public void setAlbums(List<Album> albums) {
    Albums = albums;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Singer)) {
      return false;
    }
    Singer singer = (Singer) o;
    return Objects.equals(SingerId, singer.SingerId) &&
        Objects.equals(FirstName, singer.FirstName) &&
        Objects.equals(LastName, singer.LastName) &&
        Objects.equals(Albums, singer.Albums);
  }

  @Override
  public int hashCode() {
    return Objects.hash(SingerId, FirstName, LastName, Albums);
  }

  @Override
  public String toString() {
    return "Singer{" +
        "SingerId=" + SingerId +
        ", FirstName='" + FirstName + '\'' +
        ", LastName='" + LastName + '\'' +
        ", Albums=" + Albums +
        '}';
  }
}
