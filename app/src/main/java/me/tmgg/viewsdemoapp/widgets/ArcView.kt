package me.tmgg.viewsdemoapp.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.tmgg.viewsdemoapp.R

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/7/17 14:21
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：   简单贝塞尔曲线，封闭           </p>
 */
class ArcView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var paint: Paint? = null
    private var color: Int? = null

    private fun init(context: Context?, attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context?.obtainStyledAttributes(it, R.styleable.ArcView)
            color = typedArray?.getColor(R.styleable.ArcView_color, Color.GREEN)
            typedArray?.recycle()
        }
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = color!!
    }

    private var mWidth = 0
    private var mHeight = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == View.MeasureSpec.EXACTLY) {
            mWidth = widthSize
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            mHeight = heightSize
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint?.style= Paint.Style.FILL_AND_STROKE
        paint?.color = Color.GRAY
        canvas?.drawRect(Rect(0,0,mWidth,mHeight),paint)
        paint?.color = color!!
         val path = Path()
        val heightHalf =mHeight/ 2.toFloat()
        path.moveTo(0f,heightHalf)
        path.quadTo(mWidth/2.toFloat(),mHeight.toFloat(),mWidth.toFloat(),heightHalf)
        path.lineTo(0f,mWidth/2.toFloat())
        path.close()
        canvas?.drawPath(path,paint)
    }

}