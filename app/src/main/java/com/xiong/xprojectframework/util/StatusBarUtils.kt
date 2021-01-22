package com.xiong.xprojectframework.util

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.readystatesoftware.systembartint.SystemBarTintManager
import java.lang.reflect.Field


/**
 * @author: xiong
 * @description: 状态栏工具类
 * @date： 2021/1/14
 */
object StatusBarUtils {
    /**
     * 修改状态栏为全透明
     * @param activity
     */
    @TargetApi(19)
    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window: Window = activity.window
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     * @param colorId
     */
    fun setStatusBarColor(activity: Activity, colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置状态栏颜色
            window.statusBarColor = colorId
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            setStatusBarTransparent(activity)
            val tintManager = SystemBarTintManager(activity)
            tintManager.isStatusBarTintEnabled = true
            tintManager.setStatusBarTintColor(colorId)
        }
    }

    /**
     * 设置状态栏样式（字体和图标颜色）
     * @param activity
     * @param dark
     */
    fun setStatusBarStyle(activity: Activity, dark: Boolean) {
        setAndroidNativeLightStatusBar(activity, dark)
    }

    /**
     * 谷歌原生方式修改
     * @param activity
     * @param dark
     */
    private fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
        val decor: View = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    /**
     * 获取状态栏高度（通过系统Id）
     */
    fun getStatusBarHeightById(activity: Activity): Int {
        var result = 0
        //获取状态栏高度的资源id
        val resourceId: Int = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 获取状态栏高度（通过反射方式）
     */
    fun getStatusBarHeightByReflect(activity: Activity): Int {
        val c: Class<*>
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = field[obj].toString().toInt()
            statusBarHeight = activity.resources.getDimensionPixelSize(x)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }

    /**
     * 获取状态栏高度（通过窗口方式）
     * 注：为了避免获取到高度为0，最好放在OnWindowFocusChanged回调中获取
     */
    fun getStatusBarHeightByWin(activity: Activity): Int {
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        return frame.top
    }
}