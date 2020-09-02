package com.example.kotlin_test.ui.main

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.kotlin_test.R


class BatteryWithOutRoundView : View {

    //view的宽度
    var mWidth = 0f

    //view的高度
    var mHeight = 0f

    val mBatteryRadius = 50f;

    //电池身上的斜线与整个电池的宽度比
    var mBatteryLineScale = 0.1f

    //电池身上的斜线的最大值
    val mMaxBatteryLine = 20f

    //电池凸起的头的长度比 值越大，x轴越长
    var mBatteryTopHeightScale = 0.3f

    //电池凸起的头的高度比 y轴越长
    var mBatteryTopWidthScale = 0.3f

    //电池凸起的高度
    var mBatteryTopHeight = 0f

    //电池凸起的宽度
    var mBatteryTopWidth = 0f

    //25ms 更新一下 40贞
    val addSplitTime = 25L

    //3s 充满
    var mBatteryFillTime = 5000

    //电池充电的时候初始值
    var mBatteryInit = 0f

    private val xFerMode = PorterDuffXfermode(PorterDuff.Mode.XOR)

    val batteryStart = ContextCompat.getColor(context, R.color.battery_start)
    val batteryEnd = ContextCompat.getColor(context, R.color.battery_end)
    val batteryLine = ContextCompat.getColor(context, R.color.battery_line)

    //搞个画笔
    private var mPaint: Paint = Paint().apply {
        this.isAntiAlias = true
    }

    //搞个底部背景
    var mBatteryLineBackGround = Path()

    //搞点斜线
    private var mBatteryLineBackGroundGradient = LinearGradient(
        0f,
        0f,
        20f,
        20f,
        batteryLine,
        Color.WHITE,
        Shader.TileMode.REPEAT
    )

    //电量
    var mBatteryFront = Path()

    //电量颜色
    private var mBatteryFrontGradient = LinearGradient(
        0f,
        0f,
        mWidth,
        0f,
        batteryStart,
        batteryEnd,
        Shader.TileMode.CLAMP
    )

    //两个角遮盖
    var mBatteryTopCover = Path()

    constructor(context: Context?) :
            super(context)

    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        postDelayed(object : Runnable {
            override fun run() {
                mBatteryInit += mWidth * addSplitTime / mBatteryFillTime
                if (mBatteryInit > mWidth)
                    mBatteryInit = 0f
                updateBatteryHeight()
                postInvalidate()
                postDelayed(this, addSplitTime)
            }
        }, 0)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mBatteryTopHeight = w * mBatteryTopHeightScale
        mBatteryTopWidth = h * mBatteryTopWidthScale
        mBatteryLineBackGround = Path().apply {
            moveTo(mBatteryRadius, mBatteryRadius)

            lineTo(mWidth - mBatteryTopWidth, 0f)
            lineTo(mWidth - mBatteryTopWidth / 2, mHeight / 2 - mBatteryTopHeight / 2)
            lineTo(mWidth, mHeight / 2 - mBatteryTopHeight / 2)
            lineTo(mWidth, mHeight / 2 + mBatteryTopHeight / 2)
            lineTo(mWidth - mBatteryTopWidth / 2, mHeight / 2 + mBatteryTopHeight / 2)
            lineTo(mWidth - mBatteryTopWidth, mHeight)
            lineTo(mBatteryRadius, mHeight)
        }

        mBatteryTopCover = Path().apply {
            moveTo(mWidth - mBatteryTopWidth, 0f)
            lineTo(mWidth - mBatteryTopWidth / 2, mHeight / 2 - mBatteryTopHeight / 2)
            lineTo(mWidth, mHeight / 2 - mBatteryTopHeight / 2)
            lineTo(mWidth, 0f)


            moveTo(mWidth - mBatteryTopWidth, mHeight)
            lineTo(mWidth - mBatteryTopWidth / 2, mHeight / 2 + mBatteryTopHeight / 2)
            lineTo(mWidth, mHeight / 2 + mBatteryTopHeight / 2)
            lineTo(mWidth, mHeight)
        }

        mBatteryLineBackGroundGradient = LinearGradient(
            0f,
            0f,
            (mWidth * mBatteryLineScale).coerceAtMost(mMaxBatteryLine),
            (mWidth * mBatteryLineScale).coerceAtMost(mMaxBatteryLine),
            batteryLine,
            Color.WHITE,
            Shader.TileMode.REPEAT
        )

        //预览模式
        if (isInEditMode) {

            val testWidth = mWidth / 2;

            mBatteryFront.reset()
            //画一个带条纹的背景
            mBatteryFront.addRoundRect(
                RectF(0f, 0f, testWidth, mHeight),
                mBatteryRadius,
                mBatteryRadius,
                Path.Direction.CW
            )

            mBatteryFront.moveTo(mBatteryRadius,0f)
            mBatteryFront.lineTo(testWidth, 0f)
            mBatteryFront.lineTo(testWidth, mHeight)
            mBatteryFront.lineTo(mBatteryRadius, mHeight)

            mBatteryFrontGradient = LinearGradient(
                0f,
                0f,
                testWidth,
                0f,
                batteryStart,
                batteryEnd,
                Shader.TileMode.CLAMP
            )
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        mPaint.shader = mBatteryLineBackGroundGradient
        mBatteryLineBackGround.addRoundRect(
            RectF(0f, 0f, mWidth - mBatteryTopWidth, mHeight),
            mBatteryRadius,
            mBatteryRadius,
            Path.Direction.CCW
        )
        //画一个带条纹的背景
        canvas?.drawPath(mBatteryLineBackGround, mPaint)

        mPaint.shader = mBatteryFrontGradient
        canvas?.drawPath(mBatteryFront, mPaint)

        mPaint.reset()
        mPaint.xfermode = xFerMode
        //电池的两个顶点
        canvas?.drawPath(mBatteryTopCover, mPaint)
        mPaint.xfermode = null
    }

    private fun updateBatteryHeight() {
        mBatteryFrontGradient = LinearGradient(
            0f,
            0f,
            mBatteryInit,
            0f,
            batteryStart,
            batteryEnd,
            Shader.TileMode.CLAMP
        )

        mBatteryFront.reset()
        //画一个带条纹的背景
        mBatteryFront.addRoundRect(
            RectF(0f, 0f, mBatteryInit, mHeight),
            mBatteryRadius,
            mBatteryRadius,
            Path.Direction.CW
        )

        mBatteryFront.moveTo(mBatteryRadius,0f)
        mBatteryFront.lineTo(mBatteryInit.coerceAtLeast(mBatteryRadius), 0f)
        mBatteryFront.lineTo(mBatteryInit.coerceAtLeast(mBatteryRadius), mHeight)
        mBatteryFront.lineTo(mBatteryRadius, mHeight)
    }
}