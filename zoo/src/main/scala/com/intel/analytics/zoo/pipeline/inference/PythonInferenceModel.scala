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

package com.intel.analytics.zoo.pipeline.inference

import com.intel.analytics.bigdl.tensor.TensorNumericMath.TensorNumeric
import com.intel.analytics.zoo.common.PythonZoo
import java.util.{List => JList, ArrayList}

import scala.reflect.ClassTag
import scala.collection.JavaConverters._

object PythonInferenceModel {

  def ofFloat(): PythonInferenceModel[Float] = new PythonInferenceModel[Float]()

  def ofDouble(): PythonInferenceModel[Double] = new PythonInferenceModel[Double]()
}

class PythonInferenceModel[T: ClassTag](implicit ev: TensorNumeric[T]) extends PythonZoo[T] {

  def createInferenceModel(supportedConcurrentNum: Int = 1): InferenceModel = {
    new InferenceModel(supportedConcurrentNum)
  }

  def inferenceModelLoadBigDL(
      model: InferenceModel,
      modelPath: String,
      weightPath: String): Unit = {
    model.doLoadBigDL(modelPath, weightPath)
  }

  @deprecated("this method is deprecated", "0.8.0")
  def inferenceModelLoad(
      model: InferenceModel,
      modelPath: String,
      weightPath: String): Unit = {
    model.doLoad(modelPath, weightPath)
  }

  def inferenceModelLoadCaffe(
      model: InferenceModel,
      modelPath: String,
      weightPath: String): Unit = {
    model.doLoadCaffe(modelPath, weightPath)
  }

  def inferenceModelLoadOpenVINO(
      model: InferenceModel,
      modelPath: String,
      weightPath: String,
      batchSize: Int = 0): Unit = {
    model.doLoadOpenVINO(modelPath, weightPath, batchSize)
  }

  @deprecated("this method is deprecated", "0.8.0")
  def inferenceModelOpenVINOLoadTF(
      model: InferenceModel,
      modelPath: String,
      modelType: String): Unit = {
    model.doLoadTF(modelPath, modelType)
  }

  @deprecated("this method is deprecated", "0.8.0")
  def inferenceModelOpenVINOLoadTF(
      model: InferenceModel,
      modelPath: String,
      pipelineConfigFilePath: String,
      extensionsConfigFilePath: String): Unit = {
    model.doLoadTF(modelPath, pipelineConfigFilePath, extensionsConfigFilePath)
  }

  @deprecated("this method is deprecated", "0.8.0")
  def inferenceModelOpenVINOLoadTF(model: InferenceModel,
                                   modelPath: String,
                                   objectDetectionModelType: String,
                                   pipelineConfigFilePath: String,
                                   extensionsConfigFilePath: String): Unit = {
    model.doLoadTF(modelPath, objectDetectionModelType,
      pipelineConfigFilePath, extensionsConfigFilePath)
  }

  @deprecated("this method is deprecated", "0.8.0")
  def inferenceModelOpenVINOLoadTF(model: InferenceModel,
                                   modelPath: String,
                                   imageClassificationModelType: String,
                                   checkpointPath: String,
                                   inputShape: JList[Int],
                                   ifReverseInputChannels: Boolean,
                                   meanValues: JList[Double],
                                   scale: Double
                                  ): Unit = {
    require(inputShape != null, "inputShape can not be null")
    require(meanValues != null, "meanValues can not be null")
    require(scale != null, "scale can not be null")
    model.doLoadTF(modelPath, imageClassificationModelType,
      checkpointPath, inputShape.asScala.toArray,
      ifReverseInputChannels, meanValues.asScala.toArray.map(_.toFloat), scale.toFloat)
  }

  def inferenceModelLoadTensorFlow(
      model: InferenceModel,
      modelPath: String,
      modelType: String,
      intraOpParallelismThreads: Int,
      interOpParallelismThreads: Int,
      usePerSessionThreads: Boolean): Unit = {
    model.doLoadTensorflow(modelPath, modelType, intraOpParallelismThreads,
      interOpParallelismThreads, usePerSessionThreads)
  }

  def inferenceModelLoadTensorFlow(
      model: InferenceModel,
      modelPath: String,
      modelType: String,
      inputs: Array[String],
      outputs: Array[String],
      intraOpParallelismThreads: Int,
      interOpParallelismThreads: Int,
      usePerSessionThreads: Boolean): Unit = {
    model.doLoadTensorflow(modelPath, modelType, inputs, outputs, intraOpParallelismThreads,
      interOpParallelismThreads, usePerSessionThreads)
  }

  def inferenceModelPredict(
      model: InferenceModel,
      inputs: JList[com.intel.analytics.bigdl.python.api.JTensor],
      inputIsTable: Boolean): JList[Object] = {
    val inputActivity = jTensorsToActivity(inputs, inputIsTable)
    val outputActivity = model.doPredict(inputActivity)
    activityToList(outputActivity)
  }
}
