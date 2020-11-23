package com.xiong.vipcomponet.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.xiong.vipcomponet.R
import kotlinx.android.synthetic.main.frag_vip_home.*

/**
 * @author xiong
 * @since  2020/11/23
 * @description VIP首页
 **/
@Route(path = "/vip/HomeActivity")
class VipHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag_vip_home)

        initView()
        ARouter.getInstance().inject(this)
    }

    private fun initView() {
        tv_vip_name.text = "VipHome"
    }
}