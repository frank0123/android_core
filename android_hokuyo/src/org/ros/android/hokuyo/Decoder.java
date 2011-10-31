/*
 * Copyright (C) 2011 Google Inc.
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

package org.ros.android.hokuyo;

import java.util.List;

import android.gesture.Prediction;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

class Decoder {

  public static int decodeValue(String buffer, int blockSize) {
    Preconditions.checkArgument(buffer.length() == blockSize);
    Preconditions.checkArgument(blockSize == 2 || blockSize == 3
        || blockSize == 4);
    int result = 0;
    for (int i = 0; i < blockSize; i++) {
      result |= (buffer.charAt(blockSize - i - 1) - 0x30) << i * 6;
    }
    return result;
  }

  public static List<Integer> decodeValues(String buffer, int blockSize) {
    Preconditions.checkArgument(buffer.length() % blockSize == 0);
    List<Integer> data = Lists.newArrayList();
    for (int i = 0; i < buffer.length(); i += blockSize) {
      // sensor_msgs/LaserScan uses floats for ranges.
      data.add(decodeValue(buffer.substring(i, i + 3), blockSize));
    }
    return data;
  }

}
