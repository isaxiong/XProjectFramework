package com.xiong.xprojectframework.base

import android.app.Application
import android.util.Log
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.xiong.xprojectframework.base.AppConfig.COMMON_TAG

/**
 * @author xiong
 * @since  2020/11/20
 * @description 自定义Application
 **/
class MApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        UMConfigure.init(this,
            AppConfig.UM_APP_KEY,
            "Umeng",    // 渠道名称
            UMConfigure.DEVICE_TYPE_PHONE,
            AppConfig.UM_MESSAGE_SECRET
        )

        //获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(this)
        //服务端控制声音
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(COMMON_TAG, "注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Log.e(COMMON_TAG, "注册失败：-------->  s: $s, error: $s1")
            }
        })

    }
}