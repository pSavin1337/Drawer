package com.lospollos.drawhakaton

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
    }
    private val blackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }
    private val yellowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.YELLOW
    }
    private val bluePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
    }
    private val redPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
    }

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    var color = blackColor

    private var isFirstMeasuring = true

    private var screenWidth = 0f
    private var screenHeight = 0f

    private var gridColorsArrayLength = 0
    private var gridColorsArrayLineLength = 0

    private val gridColorsArray: ArrayList<ArrayList<Int>> = ArrayList(0)

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredHeight = 0f
        val desiredWidth = 0f

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec).toFloat()

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec).toFloat()

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(widthSize)
            else -> desiredWidth
        }
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
            else -> desiredHeight
        }
        gridColorsArrayLength = height.toInt() / pixelSize
        gridColorsArrayLineLength = width.toInt() / pixelSize
        if (isFirstMeasuring) {
            for (i in 0..gridColorsArrayLength) {
                gridColorsArray.add(ArrayList(0))
                for (j in 0..gridColorsArrayLineLength) {
                    gridColorsArray[i].add(0)
                }
            }
        }
        screenHeight = height
        screenWidth = width
        isFirstMeasuring = false
        setMeasuredDimension(width.toInt(), height.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var x = 0f
        var y = 0f
        for (i in 0..gridColorsArrayLength) {
            y += pixelSize
            canvas?.drawLine(x, y, x + screenWidth, y, linePaint)
        }
        y = 0f
        for (j in 0..gridColorsArrayLineLength) {
            x += pixelSize
            canvas?.drawLine(x, y, x, y + screenHeight, linePaint)
        }
        x = 0f
        if (gridColorsArray.size != 0) {
            for (i in 0..gridColorsArrayLength) {
                for (j in 0..gridColorsArrayLineLength) {
                    when (gridColorsArray[i][j]) {
                        greenColor ->
                            canvas?.drawRect(
                                x + pixelSize * j,
                                y + pixelSize * i,
                                x + pixelSize * j + pixelSize,
                                y + pixelSize * i + pixelSize,
                                greenPaint
                            )
                        blackColor ->
                            canvas?.drawRect(
                                x + pixelSize * j,
                                y + pixelSize * i,
                                x + pixelSize * j + pixelSize,
                                y + pixelSize * i + pixelSize,
                                blackPaint
                            )
                        blueColor ->
                            canvas?.drawRect(
                                x + pixelSize * j,
                                y + pixelSize * i,
                                x + pixelSize * j + pixelSize,
                                y + pixelSize * i + pixelSize,
                                bluePaint
                            )
                        yellowColor ->
                            canvas?.drawRect(
                                x + pixelSize * j,
                                y + pixelSize * i,
                                x + pixelSize * j + pixelSize,
                                y + pixelSize * i + pixelSize,
                                yellowPaint
                            )
                        redColor ->
                            canvas?.drawRect(
                                x + pixelSize * j,
                                y + pixelSize * i,
                                x + pixelSize * j + pixelSize,
                                y + pixelSize * i + pixelSize,
                                redPaint
                            )
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                var x = 0
                var y = 0
                var xPosition = 0
                var yPosition = 0
                while (x < event.x) {
                    x += pixelSize
                    xPosition++
                }
                xPosition--
                while (y < event.y) {
                    y += pixelSize
                    yPosition++
                }
                yPosition--
                gridColorsArray[yPosition][xPosition] = color
            }
        }
        invalidate()
        return true
    }

    companion object {
        private const val pixelSize = 100
        const val blackColor = 1
        const val greenColor = 2
        const val yellowColor = 3
        const val blueColor = 4
        const val redColor = 5
    }

}