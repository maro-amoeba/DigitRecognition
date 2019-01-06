package com.maroamoeba.digitrecognition

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val mDrawPaint: Paint = Paint()
    private val mPath: Path = Path()
    val mBitmap: Bitmap
        get() {
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bmp)
            layout(left, top, right, bottom)
            draw(canvas)
            return bmp
        }

    init {
        setBackgroundColor(Color.BLACK)
        setupPaint()
    }

    private fun setupPaint() {
        mDrawPaint.apply {
            color = Color.WHITE
            strokeWidth = 100.0f
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(mPath, mDrawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(touchX, touchY)
            }
            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        postInvalidate()
        return true
    }

    fun reset() {
        mPath.reset()
        postInvalidate()
    }
}