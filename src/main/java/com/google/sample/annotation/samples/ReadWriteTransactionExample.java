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

package com.google.sample.annotation.samples;

import com.google.sample.annotation.mappers.SingerMapper;
import com.google.sample.annotation.mappers.TransactionMapper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ReadWriteTransactionExample {

  /**
   * Executes a read write transaction.
   * Uses the {@link TransactionMapper} to set read write transaction mode.
   * Prints out the update count and the commit timestamp used.
   */
  public static void run(SqlSessionFactory sqlSessionFactory) {
    try (SqlSession session = sqlSessionFactory.openSession()) {
      final TransactionMapper transactionMapper = session.getMapper(TransactionMapper.class);
      final SingerMapper singerMapper = session.getMapper(SingerMapper.class);
      final Long SingerIdToInsert = 10_003L;

      transactionMapper.setReadWrite();
      final long updateCount = singerMapper
          .create(SingerIdToInsert, "TestFirstName", "TestLastName");

      session.commit();

      final Timestamp commitTimestamp = transactionMapper.getCommitTimestamp();

      System.out.println("Inserted Singer " + SingerIdToInsert + " ("
              + "updateCount = " + updateCount + ", "
              + "commitTimestamp = " + commitTimestamp +
          ")");
    }
  }

  /**
   * Executes a read write transaction.
   * Uses a JDBC {@link Connection} to set read write transaction mode.
   * Prints out the update count and the commit timestamp used.
   */
  public static void runWithJdbcConnection(SqlSessionFactory sqlSessionFactory)
      throws SQLException {
    final Long SingerIdToInsert = 10_005L;
    try (
        SqlSession session = sqlSessionFactory.openSession();
        Connection connection = session.getConnection();
        Statement statement = connection.createStatement()
    ) {
      final SingerMapper singerMapper = session.getMapper(SingerMapper.class);

      statement.execute("SET TRANSACTION READ WRITE");
      final long updateCount = singerMapper
          .create(SingerIdToInsert, "TestFirstName", "TestLastName");

      session.commit();

      try (ResultSet rs = statement.executeQuery("SHOW VARIABLE COMMIT_TIMESTAMP")) {
        if (!rs.next()) throw new IllegalStateException("Could not retrieve commit timestamp");

        final Timestamp commitTimestamp = rs.getTimestamp(1);

        System.out.println("Inserted Singer " + SingerIdToInsert + " ("
            + "updateCount = " + updateCount + ", "
            + "commitTimestamp = " + commitTimestamp +
            ")");
      }
    }
  }

}
