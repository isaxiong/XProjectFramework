package com.xiong.xprojectframework.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * @author xiong
 * @since
 * @description
 **/
object ToastUtil {

    fun showShortToast(context: Context, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun showLongToast(context: Context, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}