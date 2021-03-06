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

package com.google.sample.annotation.mappers;

import com.google.sample.models.Album;
import com.google.sample.models.Singer;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface SingerMapper {

  @Select("SELECT SingerId, FirstName, LastName FROM Singers WHERE TRUE")
  List<Singer> findAll();

  @Select("SELECT SingerId, FirstName, LastName FROM Singers WHERE TRUE")
  @Results({
      @Result(property = "SingerId", column = "SingerId"),
      @Result(property = "FirstName", column = "FirstName"),
      @Result(property = "LastName", column = "LastName"),
      @Result(property = "Albums", javaType = List.class, column = "SingerId", many = @Many(select = "findAlbumsForSinger"))
  })
  List<Singer> nestedFindAll();
  @Select("SELECT SingerId, AlbumId, AlbumTitle FROM Albums WHERE SingerId = #{SingerId}")
  List<Album> findAlbumsForSinger();

  // Join selects are not supported with annotations
  // see @Many section in https://mybatis.org/mybatis-3/java-api.html

  @Insert("INSERT INTO Singers (SingerId, FirstName, LastName) VALUES (#{SingerId}, #{FirstName}, #{LastName})")
  long create(
      @Param("SingerId") Long SingerId,
      @Param("FirstName") String FirstName,
      @Param("LastName") String LastName
  );
}
