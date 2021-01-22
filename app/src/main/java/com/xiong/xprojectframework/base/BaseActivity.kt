package com.xiong.xprojectframework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.xiong.xprojectframework.R
import com.xiong.xprojectframework.util.StatusBarUtils

/**
 * @author xiong
 * @since  2021/1/22
 * @description 基类Activity
 **/
abstract class BaseActivity: AppCompatActivity() {

    private lateinit var mRootContainer: ViewGroup
    private lateinit var mStatusBarView: View
//    private lateinit var mTitleBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBeforeContentView()
        setContentView(getLayoutId())

        mRootContainer = findViewById(android.R.id.content)
        mRootContainer.addView(createCustomStatusBar())
//        mRootContainer.addView(createCustomTitleBar())
        initCustomTitleBar()

        initViewAfterContentView()
    }

    private fun createCustomStatusBar(): View {
        StatusBarUtils.setStatusBarTransparent(this)

        mStatusBarView = View(this)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBarUtils.getStatusBarHeightById(this))
        mStatusBarView.layoutParams = params
        return mStatusBarView
    }

    private fun initCustomTitleBar() {

    }

    /**
     * 设置状态栏背景颜色（非透明）
     */
    fun setStatusBarBackground(@ColorInt colorRes: Int) {
        mStatusBarView.visibility = View.VISIBLE
        mStatusBarView.setBackgroundColor(colorRes)
    }

    /**
     * 设置状态栏样式（字体和图标颜色）
     * @param dark 是否为暗色系
     */
    fun setStatusBarStyle(dark: Boolean) {
        StatusBarUtils.setStatusBarStyle(this, dark)
    }


    abstract fun getLayoutId(): Int

    open fun initViewBeforeContentView() {

    }

    abstract fun initViewAfterContentView()

}