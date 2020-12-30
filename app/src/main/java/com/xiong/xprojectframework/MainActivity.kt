package com.xiong.xprojectframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xiong.xprojectframework.util.TimeFormatUtil
import com.xiong.xprojectframework.util.XCountDownTimerHelper
import com.xiong.xprojectframework.widget.TestBottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.greendao.AbstractDaoMaster

/**
 * @author xiong
 * @since  2020/11/23
 * @description 首页
 **/
@Route(path = "/app/MainActivity")
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        XCountDownTimerHelper.setXOnCountDownTimerListener(object : XCountDownTimerHelper.OnXCountDownTimerListener {
            override fun onCountDownFinished() {
                Log.w("xiong", " === onCountDownFinished === ")
                tv_remain_time.text = "倒计时结束"
            }

            override fun onCountDownIntervalTick(millisUntilFinished: Long) {
                Log.w("xiong", " === onCountDownIntervalTick: ${TimeFormatUtil.formatMs(millisUntilFinished)} === ")
                tv_remain_time.text = TimeFormatUtil.formatMs(millisUntilFinished)
            }

            override fun onCountDownCancel() {
                Log.w("xiong", " === onCountDownFinished === ")
                tv_remain_time.text = "倒计时取消"
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun initView() {
        tv_main_content.text = "This is MainActivity"
        btn_test_jump_vip_page.setOnClickListener {
            ARouter.getInstance().build("/vip/HomeActivity").navigation()
        }
        btn_open_bottom_sheet.setOnClickListener {
            openBottomSheet()
        }

        // 定时器UI
        btn_start_timer.setOnClickListener(this)
        btn_cancel_timer.setOnClickListener(this)
        btn_reset_timer.setOnClickListener(this)
    }

    private fun openBottomSheet() {
        TestBottomSheetDialogFragment.getInstance().show(supportFragmentManager, "TestBottomSheetDialogFragment")
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_start_timer -> {
                if (!TextUtils.isEmpty(et_time_duration.text) && !TextUtils.isEmpty(et_time_interval.text)) {
                    XCountDownTimerHelper.startTimer(et_time_duration.text.toString().toLong(), et_time_interval.text.toString().toLong())
                }
            }
            R.id.btn_cancel_timer ->
                XCountDownTimerHelper.cancelTimer()
            R.id.btn_reset_timer -> {
                if (!TextUtils.isEmpty(et_time_duration.text) && !TextUtils.isEmpty(et_time_interval.text)) {
                    XCountDownTimerHelper.resetTimer(et_time_duration.text.toString().toLong(), et_time_interval.text.toString().toLong())
                }
            }
        }
    }

}
