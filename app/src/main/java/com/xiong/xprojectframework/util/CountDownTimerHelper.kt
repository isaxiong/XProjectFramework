package com.xiong.xprojectframework.util

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log

/**
 * @author xiong
 * @since  2020/12/31
 * @description 倒计时定时器辅助类
 * 1、支持常规的倒计时使用场景
 * 2、支持倒计时的暂停及恢复
 * 3、支持倒计时长及间隔时长的动态重置（非销毁重启）
 **/
object CountDownTimerHelper {

    private val TAG = CountDownTimerHelper::class.java.simpleName

    // 默认倒计时间隔
    private const val DEFAULT_COUNT_DOWN_INTERVAL = 1000L
    // 补偿时间，补偿系统延时+代码误差大于间隔时隔时间的部分
    private const val COMPENSATE_TIME = 100L

    // 定时总时长
    private var mMillisInFuture: Long = 0L

    // 倒计时回调间隔时长
    private var mCountdownInterval: Long = 0L

    // 定时目标停止时间
    private var mStopTimeInFuture: Long = 0L

    // 剩余定时时长（预留：是否需要暴露到外层）
    private var mRemainTimeUntilFinish: Long = 0L

    private var mStopped: Boolean = false
    private var mCancelled: Boolean = false
    private var mOnCountDownTimerListener: OnCountDownTimerListener? = null

    private const val COUNT_DOWN_MSG = 10001
    private const val KEEP_ALIVE_MSG = 10002
    private val mCountDownHandler: Handler = @SuppressLint("HandlerLeak") object : Handler() {

        override fun handleMessage(msg: Message) {
            synchronized (CountDownTimerHelper) {
                if (mCancelled) {
                    return@synchronized
                }
                if (mStopped) {
                    mStopTimeInFuture = mRemainTimeUntilFinish + SystemClock.elapsedRealtime()
                    sendMessageDelayed(obtainMessage(KEEP_ALIVE_MSG), DEFAULT_COUNT_DOWN_INTERVAL)
                    return@synchronized
                }

                mRemainTimeUntilFinish = mStopTimeInFuture - SystemClock.elapsedRealtime() + COMPENSATE_TIME
                if (mRemainTimeUntilFinish <= 0) {
                    mOnCountDownTimerListener?.onCountDownFinished()
                } else {
                    val lastTickStart = SystemClock.elapsedRealtime()
                    mOnCountDownTimerListener?.onCountDownIntervalTick(mRemainTimeUntilFinish)

                    val lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart
//                    Log.w(TAG, "onCountDownIntervalTick cost = $lastTickDuration")
                    var delay: Long
                    if (mRemainTimeUntilFinish < mCountdownInterval) {
                        delay = mRemainTimeUntilFinish - lastTickDuration
                        // 特殊情况：如果用户在 onCountDownIntervalTick 中操作时长超过间隔时长，则回调onCountDownFinish
                        if (delay < 0) delay = 0
                    } else {
                        delay = mCountdownInterval - lastTickDuration

                        // 特殊情况：如果用户在 onCountDownIntervalTick 中操作时长超过间隔时长，则跳到下一个间隔
                        while (delay < 0) delay += mCountdownInterval
                    }
                    sendMessageDelayed(obtainMessage(COUNT_DOWN_MSG), delay)
                }
            }
        }
    }

    /**
     * 设置倒计时监听器
     */
    fun setOnCountDownTimerListener(onCountDownTimerListener: OnCountDownTimerListener) {
        mOnCountDownTimerListener = onCountDownTimerListener
    }

    /**
     * 启动倒计时
     * @param millisInFuture 倒计总时长，单位：ms
     * @param countDownInterval 倒计时间隔，单位：ms
     */
    fun startTimer(millisInFuture: Long, countDownInterval: Long?) {
        if (millisInFuture <= 0) {
            mCancelled = true
            mOnCountDownTimerListener?.onCountDownFinished()
            return
        }
        mCancelled = false
        mStopped = false
        mMillisInFuture = millisInFuture
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture
        mCountdownInterval = if (countDownInterval == null || countDownInterval == 0L) DEFAULT_COUNT_DOWN_INTERVAL else countDownInterval
        mCountDownHandler.sendMessage(mCountDownHandler.obtainMessage(COUNT_DOWN_MSG))
    }

    /**
     * 取消倒计时
     */
    fun cancelTimer() {
        mCancelled = true
        mStopped = true
        mCountDownHandler.removeMessages(KEEP_ALIVE_MSG)
        mCountDownHandler.removeMessages(COUNT_DOWN_MSG)
        clearAllTimeConfig()
        mOnCountDownTimerListener?.onCountDownCancel()
    }

    /**
     * 重置倒计时
     * @param millisInFuture    倒计总时长，单位：ms
     * @param countDownInterval 倒计时间隔，单位：ms
     */
    fun resetTimer(millisInFuture: Long, countDownInterval: Long?) {
        if (millisInFuture <= 0) {
            return
        }
        mMillisInFuture = millisInFuture
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture
        mRemainTimeUntilFinish = mMillisInFuture
        mCountdownInterval = if (countDownInterval == null || countDownInterval == 0L) DEFAULT_COUNT_DOWN_INTERVAL else countDownInterval
    }

    /**
     * 暂停倒计时
     */
    fun stopTimer() {
        mStopped = true
    }

    /**
     * 恢复倒计时
     */
    fun resumeTimer() {
        mStopped = false
    }

    /**
     * 是否已停止
     */
    fun isCancelled(): Boolean = mCancelled

    /**
     * 是否已暂停
     */
    fun isStopped(): Boolean = mStopped

    /**
     * 清空所有的时间设置
     */
    private fun clearAllTimeConfig() {
        mMillisInFuture = 0L
        mStopTimeInFuture = 0L
        mCountdownInterval = 0L
        mRemainTimeUntilFinish = 0L
    }


    interface OnCountDownTimerListener {
        /**
         * 倒计时结束
         */
        fun onCountDownFinished()

        /**
         *  倒计时间隔回调
         */
        fun onCountDownIntervalTick(millisUntilFinished: Long)

        /**
         * 倒计时停止
         */
        fun onCountDownCancel()
    }

}