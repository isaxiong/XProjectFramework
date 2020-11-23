package com.xiong.xprojectframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.greendao.AbstractDaoMaster

/**
 * @author xiong
 * @since  2020/11/23
 * @description 首页
 **/
@Route(path = "/app/MainActivity")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
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
    }

}
