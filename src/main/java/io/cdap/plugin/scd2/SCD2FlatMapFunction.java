/*
 * Copyright © 2020 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.scd2;

import io.cdap.cdap.api.data.format.StructuredRecord;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;

import java.util.Iterator;

/**
 * Flap map function to convert a pair of key and value to the structured record.
 * This class is to make sure there are no spark classes in the plugin class, otherwise the validate will fail.
 */
public class SCD2FlatMapFunction
  implements FlatMapFunction<Iterator<Tuple2<SCD2Key, StructuredRecord>>, StructuredRecord> {
  private final SCD2Plugin.Conf conf;

  public SCD2FlatMapFunction(SCD2Plugin.Conf conf) {
    this.conf = conf;
  }

  @Override
  public Iterator<StructuredRecord> call(Iterator<Tuple2<SCD2Key, StructuredRecord>> records) {
    return new SCD2Iterator(records, conf);
  }
}
