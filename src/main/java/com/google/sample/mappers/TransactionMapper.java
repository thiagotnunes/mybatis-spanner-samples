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

package com.google.sample.mappers;

import java.sql.Timestamp;
import org.apache.ibatis.annotations.Select;

public interface TransactionMapper {

  @Select("SET TRANSACTION READ ONLY")
  void setReadOnly();

  @Select("SET READ_ONLY_STALENESS = 'MAX_STALENESS 10s'")
  void set10SecondsReadStaleness();

  @Select("SET TRANSACTION READ WRITE")
  void setReadWrite();

  @Select("SHOW VARIABLE READ_TIMESTAMP")
  Timestamp getReadTimestamp();

  @Select("SHOW VARIABLE COMMIT_TIMESTAMP")
  Timestamp getCommitTimestamp();
}
