package com.kt.mp3.ui.main.gui

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.kt.mp3.R

class FloatPlayerView {

    object Builder {
        private var floatMusicView: View? = null
        private var screenWidth = 0
        private var screenHeight = 0
        private var floatMusicViewWidth = 0
        private var floatMusicViewHeight = 0
        private var initX = 0
        private var initY = 0

        @SuppressLint("ClickableViewAccessibility")
        fun build(activity: Activity?) {
            if (activity == null) return
            screenWidth = activity.resources.displayMetrics.widthPixels
            screenHeight = activity.resources.displayMetrics.heightPixels

            val decorView = activity.window.decorView as ViewGroup
            //不存在就创建
            if (floatMusicView == null)
                floatMusicView = LayoutInflater.from(activity)
                    .inflate(
                        R.layout.view_float_view,
                        decorView,
                        false
                    )
            //先移除旧得界面，避免重复添加
            decorView.removeView(floatMusicView)
            decorView.addView(
                floatMusicView
            )
            floatMusicView!!.post {
                //计算宽高
                floatMusicViewWidth = floatMusicView!!.width
                floatMusicViewHeight = floatMusicView!!.height

                println("init:${floatMusicViewWidth}:${floatMusicViewHeight}:${screenWidth}:${screenHeight}")

                val layoutParams = floatMusicView!!.layoutParams as FrameLayout.LayoutParams

                decorView.setOnTouchListener { _, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            initX = event.x.toInt()
                            initY = event.y.toInt()
                        }
                        MotionEvent.ACTION_MOVE -> {
                            var moveX = event.x.toInt() - initX
                            var moveY = event.y.toInt() - initY
                            //往右移动 到顶点。移动间距设置为0
                            if (layoutParams.leftMargin.plus(floatMusicViewWidth) > screenWidth && moveX > 0) {
                                moveX = 0
                            }
                            //左移动到顶点，移动间距为0
                             if (layoutParams.leftMargin < 0 && moveX < 0) {
                                 moveX = 0
                            }
                            layoutParams.leftMargin += moveX
                            //左右边界判断
                            if (layoutParams.leftMargin < 0)
                                layoutParams.leftMargin = 0
                            if (layoutParams.leftMargin > screenWidth - floatMusicViewWidth)
                                layoutParams.leftMargin = screenWidth - floatMusicViewWidth


                            //往下移动 到顶点。移动间距设置为0
                            if (layoutParams.topMargin.plus(floatMusicViewHeight) > screenHeight && moveY > 0) {
                                moveY = 0
                            }
                            //左移动到顶点，移动间距为0
                            if (layoutParams.topMargin < 0 && moveY < 0) {
                                moveY = 0
                            }
                            layoutParams.topMargin += moveY
                            //左右边界判断
                            if (layoutParams.topMargin < 0)
                                layoutParams.topMargin = 0
                            if (layoutParams.topMargin > screenHeight - floatMusicViewHeight)
                                layoutParams.topMargin = screenHeight - floatMusicViewHeight

                            floatMusicView!!.layoutParams = layoutParams

                            initX = event.x.toInt()
                            initY = event.y.toInt()
                        }
                        else -> {
                        }
                    }
                    false
                }
            }
        }
    }
}