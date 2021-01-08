package com.xiong.xprojectframework.widget

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.xiong.xprojectframework.R


/**
 * @author xiong
 * @since  2021/1/8
 * @description 底部弹窗DialogFragment（仿BottomSheetDialogFragment效果）
 **/
class BaseBottomDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.DialogFullScreen) //dialog全屏
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(@NonNull inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        //去掉dialog的标题，需要在setContentView()之前
        this.dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = this.dialog!!.window
        //去掉dialog默认的padding
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM
        //设置dialog的动画
        lp.windowAnimations = R.style.BottomDialogAnimation
        window.attributes = lp
        window.setBackgroundDrawable(ColorDrawable())
        return inflater.inflate(R.layout.dialog_bottom_sheet_test, null)
    }

}