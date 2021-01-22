package com.xiong.xprojectframework.widget

import android.app.Dialog
import com.xiong.xprojectframework.R

/**
 * @author xiong
 * @since  2020/12/15
 * @description BottomSheetDialogFragment测试类
 **/
class TestBottomSheetDialogFragment: BaseBottomSheetDialogFragment() {

    companion object {
        fun getInstance(): TestBottomSheetDialogFragment{
            return TestBottomSheetDialogFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_test_bottom_sheet
    }

    override fun initView() {

    }

    override fun initData() {

    }

}