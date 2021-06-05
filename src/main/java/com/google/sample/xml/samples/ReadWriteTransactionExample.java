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
import java.sql.Timestamp;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ReadWriteTransactionExample {

  /**
   * Executes a read write transaction.
   * Uses the xml TransactionMapper to set read write transaction mode.
   * Prints out the update count and the commit timestamp used.
   */
  public static void run(SqlSessionFactory sqlSessionFactory) {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      final Singer singer = new Singer(10_004L, "TestFirstName", "TestLastName");

      session.selectOne("com.google.sample.xml.mappers.TransactionMapper.setReadWrite");
      final int updateCount = session.insert("com.google.sample.xml.mappers.SingerMapper.insertSinger", singer);
      session.commit();

      final Timestamp commitTimestamp = session
          .selectOne("com.google.sample.xml.mappers.TransactionMapper.getCommitTimestamp");

      System.out.println("Inserted Singer " + 10_004L + " ("
              + "updateCount = " + updateCount + ", "
              + "commitTimestamp = " + commitTimestamp +
          ")");
    }
  }
}
