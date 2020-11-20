package com.xiong.xprojectframework.util

import android.content.Context

/**
 * @author xiong
 * @since
 * @description
 **/
class CommonUtils {

    companion object {

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 将Sp转换位pixel
         * @param context 上下文环境
         * @param spValue 以sp为单位的字体大小
         * @return
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }
    }
}