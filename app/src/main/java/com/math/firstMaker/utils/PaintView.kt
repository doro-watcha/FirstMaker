package com.math.firstMaker

import android.graphics.*

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.io.FileOutputStream
import android.graphics.Bitmap
import android.os.Environment.getExternalStorageDirectory
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Environment
import android.os.Environment.getExternalStorageState
import java.io.File


class PaintView(context: Context, attrSet: AttributeSet? = null) : View(context, attrSet) {

    fun clearAnswer() {
        mPath.reset()
        invalidate()
    }

    fun getCanvas() : Canvas = canvas

    private var canvas: Canvas
    private val allPaths = ArrayList<DrawModel>()
    var mPaint: Paint
    var mPath: Path

    init {
//        initialize properties of mPaint stroke, background and path
        this.setBackgroundColor(Color.WHITE)
        canvas = Canvas()
        mPaint = Paint()
        mPath = Path()

        mPaint.apply {
            strokeWidth = 8f
            isAntiAlias = true
            color = Color.GRAY
            strokeJoin = Paint.Join.ROUND
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val model = DrawModel(mPath, mPaint)

        canvas.drawPath(mPath, mPaint)

        if (event.action == MotionEvent.ACTION_DOWN) {

            mPath.moveTo(event.x, event.y)

            mPath.lineTo(event.x, event.y)
        } else if (event.action == MotionEvent.ACTION_MOVE) {

            mPath.lineTo(event.x, event.y)

            model.path = mPath

            model.paint = mPaint

            allPaths.add(model)
        }

        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (allPaths.size > 0) {

            canvas.drawPath(
                    allPaths[allPaths.size - 1].path,

                    allPaths[allPaths.size - 1].paint
            )
        }
    }

    fun canvasToBitmap() : Bitmap{
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        this.draw(canvas)

        return bitmap
    }

}

class DrawModel(var path: Path,var paint: Paint )