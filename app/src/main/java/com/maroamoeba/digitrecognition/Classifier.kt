package com.maroamoeba.digitrecognition

import android.content.res.AssetManager
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class Classifier(assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int) {
    private var INTERPRETER: Interpreter
    private var LABEL_LIST: List<String>
    private val INPUT_SIZE: Int = inputSize
    private val PIXEL_SIZE: Int = 1
    private val IMAGE_MEAN = 0
    private val IMAGE_STD = 255.0f

    init {
        INTERPRETER = Interpreter(loadModelFile(assetManager, modelPath))
        LABEL_LIST = loadLabelList(assetManager, labelPath)
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }

    }

    fun predict(bitmap: Bitmap, labelProbArray: Array<FloatArray>): Float {
        INTERPRETER.run(convertBitmapToByteBuffer(Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)), labelProbArray)
        return (labelProbArray[0].max() ?: -1.0f)
    }

    /* MNISTの各ピクセルはRGBではなく白黒で評価される為、PIXEL_SIZEは1になります。*/
    private fun convertBitmapToByteBuffer(bitmap: Bitmap) : ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val floatBuffer = byteBuffer.asFloatBuffer()

        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (y: Int in 0 until INPUT_SIZE) {
            for(x: Int in 0 until INPUT_SIZE) {
                val p: Int = intValues[pixel++]
                val c = (((p and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                floatBuffer.put(c)
            }
        }
        return byteBuffer
    }

}