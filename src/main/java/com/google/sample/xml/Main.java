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

package com.google.sample.xml;


import com.google.sample.xml.samples.ReadOnlyTransactionExample;
import com.google.sample.xml.samples.ReadWriteTransactionExample;
import com.google.sample.xml.samples.StaleReadOnlyTransactionExample;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {

  public static void main(String[] args) throws IOException, SQLException {
    final Properties properties = readProperties("spanner.properties");
    final SqlSessionFactory sqlSessionFactory = createSqlSessionFactory("mybatis-config.xml", properties);

    run(args[0], sqlSessionFactory);
  }

  private static void run(String example, SqlSessionFactory sqlSessionFactory) throws SQLException {
    switch (example) {
      case "read-only":
        ReadOnlyTransactionExample.run(sqlSessionFactory);
        break;
      case "stale-read-only":
        StaleReadOnlyTransactionExample.run(sqlSessionFactory);
        break;
      case "read-write":
        ReadWriteTransactionExample.run(sqlSessionFactory);
        break;
      default:
        throw new IllegalArgumentException("Unknown example " + example);
    }
  }

  private static Properties readProperties(String propsFileName) throws IOException {
    final Properties properties = new Properties();
    try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(propsFileName)) {
      properties.load(inputStream);
      return properties;
    }
  }

  private static SqlSessionFactory createSqlSessionFactory(String configFileName, Properties properties) throws IOException {
    try (InputStream inputStream = Resources.getResourceAsStream(configFileName)) {
      return new SqlSessionFactoryBuilder().build(inputStream, properties);
    }
  }
}
