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

package com.google.sample.annotation;

import com.google.sample.annotation.mappers.SingerMapper;
import com.google.sample.annotation.mappers.TransactionMapper;
import com.google.sample.annotation.samples.NestedSelectExample;
import com.google.sample.annotation.samples.ReadOnlyTransactionExample;
import com.google.sample.annotation.samples.ReadWriteTransactionExample;
import com.google.sample.annotation.samples.StaleReadOnlyTransactionExample;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class Main {

  // TODO: Replace these variables with your configuration
  private static final String PROJECT = "project-id";
  private static final String INSTANCE = "instance-id";
  private static final String DATABASE = "database-id";
  private static final int MIN_SESSIONS = 400;
  private static final int MAX_SESSIONS = 800;
  private static final int NUM_CHANNELS = MAX_SESSIONS / 100; // There should be at most 100 sessions per channel

  private static final String URL = String.format(
      "jdbc:cloudspanner:/projects/%s/instances/%s/databases/%s?minSessions=%d;maxSessions=%d;numChannels=%d",
      PROJECT, INSTANCE, DATABASE, MIN_SESSIONS, MAX_SESSIONS, NUM_CHANNELS
  );
  private static final String DRIVER = "com.google.cloud.spanner.jdbc.JdbcDriver";
  private static final String ENVIRONMENT_ID = "development";

  public static void main(String[] args) throws SQLException {
    // MyBatis configuration
    final SampleDataSourceFactory dataSourceFactory = new SampleDataSourceFactory(DRIVER, URL);
    final DataSource dataSource = dataSourceFactory.getDataSource();
    final TransactionFactory transactionFactory = new JdbcTransactionFactory();
    final Environment environment = new Environment(ENVIRONMENT_ID, transactionFactory, dataSource);
    final Configuration configuration = new Configuration(environment);
    configuration.addMapper(SingerMapper.class);
    configuration.addMapper(TransactionMapper.class);
    final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    run(args[0], sqlSessionFactory);
  }

  private static void run(String example, SqlSessionFactory sqlSessionFactory) throws SQLException {
    switch (example) {
      case "stale-read-only":
        StaleReadOnlyTransactionExample.run(sqlSessionFactory);
        break;
      case "stale-read-only-jdbc":
        StaleReadOnlyTransactionExample.runWithJdbcConnection(sqlSessionFactory);
        break;
      case "read-only":
        ReadOnlyTransactionExample.run(sqlSessionFactory);
        break;
      case "read-only-jdbc":
        ReadOnlyTransactionExample.runWithJdbcConnection(sqlSessionFactory);
        break;
      case "read-write":
        ReadWriteTransactionExample.run(sqlSessionFactory);
        break;
      case "read-write-jdbc":
        ReadWriteTransactionExample.runWithJdbcConnection(sqlSessionFactory);
        break;
      case "nested-select":
        NestedSelectExample.run(sqlSessionFactory);
        break;
      default:
        throw new IllegalArgumentException("Unknown example " + example);
    }
  }

}
