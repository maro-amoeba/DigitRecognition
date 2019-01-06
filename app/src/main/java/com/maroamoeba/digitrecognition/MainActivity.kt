package com.maroamoeba.digitrecognition

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mClassifier: Classifier
    private lateinit var mDrawImage: Bitmap

    private val mModelPath = "digit_model.tflite"
    private val mLabelPath = "digit_labels.txt"
    private val mInputSize = 28


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        mButtonDetect.setOnClickListener { predict() }
        mButtonClear.setOnClickListener { mDrawView.reset() }
    }

    private fun predict() {
        mDrawImage = mDrawView.mBitmap
        val labelProbArray: Array<FloatArray> = Array(1, { j -> FloatArray(10, { i -> 0.0f }) })
        val confidence = mClassifier.predict(mDrawImage, labelProbArray)
        val result = labelProbArray[0].indexOf(confidence)
        val toast = Toast.makeText(applicationContext, "${result} with ${Math.round(confidence * 100)}% confidence", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM, 0, 20)
        toast.show()
    }
}
