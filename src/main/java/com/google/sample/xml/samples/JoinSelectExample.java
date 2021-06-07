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

package com.google.sample.xml.samples;

import com.google.sample.models.Singer;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class JoinSelectExample {

  /**
   * Executes a join select.
   */
  public static void run(SqlSessionFactory sqlSessionFactory) {
    try (SqlSession session = sqlSessionFactory.openSession()) {

      final List<Singer> singers = session
          .selectList("SingerMapper.joinSelectSingersAndAlbums");

      System.out.println("Found " + singers.size() + " singers");
      singers.forEach(singer -> System.out.println("\t" + singer));
    }
  }
}
