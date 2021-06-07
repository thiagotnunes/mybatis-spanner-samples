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

import java.util.Objects;

public class Album {

  private Long SingerId;
  private Long AlbumId;
  private String AlbumTitle;

  public void setSingerId(Long singerId) {
    SingerId = singerId;
  }

  public void setAlbumId(Long albumId) {
    AlbumId = albumId;
  }

  public void setAlbumTitle(String albumTitle) {
    AlbumTitle = albumTitle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Album)) {
      return false;
    }
    Album album = (Album) o;
    return Objects.equals(SingerId, album.SingerId) &&
        Objects.equals(AlbumId, album.AlbumId) &&
        Objects.equals(AlbumTitle, album.AlbumTitle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(SingerId, AlbumId, AlbumTitle);
  }

  @Override
  public String toString() {
    return "Album{" +
        "SingerId=" + SingerId +
        ", AlbumId=" + AlbumId +
        ", AlbumTitle='" + AlbumTitle + '\'' +
        '}';
  }
}
