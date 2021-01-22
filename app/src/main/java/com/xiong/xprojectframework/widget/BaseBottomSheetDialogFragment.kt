package com.xiong.xprojectframework.widget

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiong.xprojectframework.R


/**
 * @author xiong
 * @since  2020/12/15
 * @description BottomSheetDialogFragment基类
 **/
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutView = inflater.inflate(getLayoutId(), container, false)
        initView()
        return layoutView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onStart() {
        super.onStart()
        initBottomSheetDialogStyle()
    }

    private fun initBottomSheetDialogStyle() {
        dialog?.window?.let { window ->
            // 获取dialog的根布局
            val bottomSheet = window.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            // 把windows默认背景颜色去掉，不然圆角显示不见
            bottomSheet?.background = ColorDrawable(Color.TRANSPARENT)
            if (bottomSheet != null) {
                val layoutParam = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
                layoutParam.height = getDialogHeight()
                bottomSheet.layoutParams = layoutParam

                val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                // 设置弹窗的最大高度
                bottomSheetBehavior.peekHeight = getDialogHeight()
                // 初始为展开状态
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    abstract fun getLayoutId(): Int

    open fun getDialogHeight(): Int {
        val defaultHeight = resources.displayMetrics.heightPixels
        return defaultHeight - defaultHeight / 4
    }

    open fun getDialogWidth(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }



    open fun initView() {}

    open fun initData() {}
}