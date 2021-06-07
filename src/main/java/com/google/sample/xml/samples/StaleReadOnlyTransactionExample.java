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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class StaleReadOnlyTransactionExample {

  /**
   * Executes a stale read (max staleness configured as 10 seconds).
   * Uses the xml TransactionMapper to set read only transaction mode.
   * Prints out the read timestamp used.
   */
  public static void run(SqlSessionFactory sqlSessionFactory) throws SQLException {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      // Read staleness only works with autoCommit = true
      session.getConnection().setAutoCommit(true);
      // Accepts data that is at most 10 seconds old
      session.selectOne("TransactionMapper.set10SecondsReadStaleness");
      final List<Singer> singers = session
          .selectList("SingerMapper.selectSingers");
      final Timestamp readTimestamp = session
          .selectOne("TransactionMapper.getReadTimestamp");

      System.out.println("Found " + singers.size() + " singers ("
          + "read timestamp = " + readTimestamp +
          "):");
      singers.forEach(singer -> System.out.println("\t" + singer));
    }
  }
}
