package com.fee.xwebview.demo.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/11/17<br>
 * Time: 11:31<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
class DependencyButton(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr) {

    private var paint4Line: Paint = Paint()

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        //        paint4Line.color = 0xffff0000.toInt()
        paint4Line.color = Color.RED
        paint4Line.isAntiAlias = true
        paint4Line.strokeWidth = 10f
    }
    private var lastX:Int = 0
    private var lastY: Int = 0
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val rawX = event.rawX.toInt()
            val rawY = event.rawY.toInt()
            println("--> rawX = $rawX, x = ${event.x}; rawY = $rawY, y = ${event.y}")
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    val curLp = layoutParams
                    if (curLp is ViewGroup.MarginLayoutParams) {
                        var left = curLp.leftMargin + (rawX - lastX)
                        var top = curLp.topMargin + (rawY - lastY)
                        if (left < 0) {
                            left = 0
                        }
                        if (top < 0) {
                            top = 0
                        }
                        curLp.leftMargin = left
                        curLp.topMargin = top
                        layoutParams = curLp
                        requestLayout()
                    }
                }
                else -> {
                }
            }
            lastX = rawX
            lastY = rawY
        }
//        return super.onTouchEvent(event)
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f,0f,measuredWidth.toFloat(),0f,paint4Line)
    }
}