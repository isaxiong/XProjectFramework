package com.xiong.xprojectframework.widget

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.xiong.xprojectframework.R
import kotlinx.android.synthetic.main.vip_layout_vip_hint.view.*

/**
 * @author xiong
 * @since  2020/12/24
 * @description VIP试看内容提醒
 **/
class VipHintToast : FrameLayout {

    private var mRootView: View? = null

    private constructor(context: Context): this(context, null)

    private constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    private constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.vip_layout_vip_hint, this, true)
        initView()
    }

    private fun initView() {
        val vipHintContent = SpannableString("您正在试看VIP内容，加入VIP看完整内容").apply {
            setSpan(ForegroundColorSpan(Color.parseColor("#F8CEA2")), 11, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        tvHintContent.text = vipHintContent
    }

    fun setLocation(gravity: Gravity) {

    }

    fun show() {
        mRootView?.post {

        }
    }
}