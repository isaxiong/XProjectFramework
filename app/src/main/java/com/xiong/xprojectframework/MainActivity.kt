package com.xiong.xprojectframework

import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jaeger.library.StatusBarUtil
import com.xiong.xprojectframework.base.BaseActivity
import com.xiong.xprojectframework.util.TimeFormatUtil
import com.xiong.xprojectframework.util.CountDownTimerHelper
import com.xiong.xprojectframework.util.StatusBarUtils
import com.xiong.xprojectframework.widget.BaseBottomDialogFragment
import com.xiong.xprojectframework.widget.TestBottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.greendao.AbstractDaoMaster
import java.lang.reflect.Field

/**
 * @author xiong
 * @since  2020/11/23
 * @description 首页
 **/
@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        StatusBarUtil.setColor(this, resources.getColor(R.color.colorAccent))
        StatusBarUtil.setTransparentForImageView(this, null)

        // 设置状态栏全透明
//        StatusBarUtils.setStatusBarTransparent(this)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    // 5.0及以上
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT // resources.getColor(R.color.colorAccent)
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    // 4.4及以上
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        }

        initView()
        initCountDownTimer()
        Log.w("xiong", "=== initView getTitleBarHeight === 1、反射：${StatusBarUtils.getStatusBarHeightByReflect(this)}" +
                ", 2、系统资源Id：${StatusBarUtils.getStatusBarHeightById(this)}" +
                ", 3、窗口：${StatusBarUtils.getStatusBarHeightByWin(this)}")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // TODO xiong -- test：测试沉浸式是否会对状态栏高度有影响（初步测试无影响）
            Log.w("xiong", "=== onWindowFocusChanged getTitleBarHeight === 1、反射：${StatusBarUtils.getStatusBarHeightByReflect(this)}" +
                    ", 2、系统资源Id：${StatusBarUtils.getStatusBarHeightById(this)}" +
                    ", 3、窗口：${StatusBarUtils.getStatusBarHeightByWin(this)}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    /**
     *
     */
    override fun initViewAfterContentView() {
//        setStatusBarTranslucent()
//        setStatusBarBackground(Color.TRANSPARENT)
        setStatusBarStyle(true)
//        ContextCompat.getDrawable(this, )
        setStatusBarBackground(resources.getColor(R.color.colorAccent))
        initView()
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
        btn_stop_timer.setOnClickListener(this)
        btn_resume_timer.setOnClickListener(this)
    }

    private fun initCountDownTimer() {
        CountDownTimerHelper.setOnCountDownTimerListener(object : CountDownTimerHelper.OnCountDownTimerListener {
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

    private fun openBottomSheet() {
//        BaseBottomDialogFragment().show(supportFragmentManager, "BaseBottomDialogFragmentTest")
        supportFragmentManager.beginTransaction()
            .add(TestBottomSheetDialogFragment.getInstance(), "TestBottomSheetDialogFragment")
            .commit()
//        TestBottomSheetDialogFragment.getInstance().show(supportFragmentManager, "TestBottomSheetDialogFragment")
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_start_timer -> {
                if (!TextUtils.isEmpty(et_time_duration.text)) {
                    CountDownTimerHelper.startTimer(et_time_duration.text.toString().toLong(), et_time_interval.text.toString().toLong())
                }
            }
            R.id.btn_cancel_timer ->
                CountDownTimerHelper.cancelTimer()
            R.id.btn_reset_timer -> {
                if (!TextUtils.isEmpty(et_time_duration.text)) {
                    CountDownTimerHelper.resetTimer(et_time_duration.text.toString().toLong(), et_time_interval.text.toString().toLong())
                }
            }
            R.id.btn_stop_timer -> {
                CountDownTimerHelper.stopTimer()
            }
            R.id.btn_resume_timer -> {
                CountDownTimerHelper.resumeTimer()
            }
        }
    }

}
