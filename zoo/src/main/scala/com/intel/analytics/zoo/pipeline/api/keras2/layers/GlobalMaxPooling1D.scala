/*
 * Copyright 2018 Analytics Zoo Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.analytics.zoo.pipeline.api.keras2.layers


import com.intel.analytics.bigdl.tensor.TensorNumericMath.TensorNumeric
import com.intel.analytics.bigdl.utils.Shape
import com.intel.analytics.zoo.pipeline.api.Net
import com.intel.analytics.zoo.pipeline.api.keras.{layers => klayers1}

import scala.reflect.ClassTag

 /**
  * Applies global max pooling operation for temporal data.
  * The input of this layer should be 3D.
  *
  * When you use this layer as the first layer of a model, you need to provide the argument
  * inputShape (a Single Shape, does not include the batch dimension).
  *
  * @param inputShape A Single Shape, does not include the batch dimension .
  * @tparam T The numeric type of parameter(e.g. weight, bias). Only support float/double now.
  */
class GlobalMaxPooling1D[T: ClassTag](
      override val inputShape: Shape = null)(implicit ev: TensorNumeric[T])
  extends klayers1.GlobalMaxPooling1D[T](inputShape) with Net {}

object GlobalMaxPooling1D {
  def apply[@specialized(Float, Double) T: ClassTag](
      inputShape: Shape = null)(implicit ev: TensorNumeric[T]) : GlobalMaxPooling1D[T] = {
    new GlobalMaxPooling1D[T](inputShape)
  }
}

