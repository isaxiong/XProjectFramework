package com.xiong.xprojectframework.util

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log

/**
 * @author xiong
 * @since  2020/12/30
 * @description 倒计时定时器
 **/
object XCountDownTimerHelper {

    private val TAG = XCountDownTimerHelper::class.java.simpleName

    // 定时总时长
    private var mMillisInFuture: Long = 0L

    // 倒计时回调间隔时长
    private var mCountdownInterval: Long = 0L

    // 定时目标停止时间
    private var mStopTimeInFuture: Long = 0L

    // 剩余定时时长（预留：是否需要暴露到外层）
    private var mRemainTimeUntilFinish: Long = 0L

    private var mCancelled: Boolean = false
    private var mOnXCountDownTimerListener: OnXCountDownTimerListener? = null

    private const val COUNT_DOWN_MSG = 10001
    private val mCountDownHandler: Handler = @SuppressLint("HandlerLeak") object : Handler() {

        override fun handleMessage(msg: Message) {
            synchronized (XCountDownTimerHelper) {
                if (mCancelled) {
                    return@synchronized
                }

                mRemainTimeUntilFinish = mStopTimeInFuture - SystemClock.elapsedRealtime()
                if (mRemainTimeUntilFinish <= 0) {
                    mOnXCountDownTimerListener?.onCountDownFinished()
                } else {
                    val lastTickStart = SystemClock.elapsedRealtime()
                    mOnXCountDownTimerListener?.onCountDownIntervalTick(mRemainTimeUntilFinish)

                    val lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart
                    Log.w(TAG, "onCountDownIntervalTick cost = $lastTickDuration")
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
    fun setXOnCountDownTimerListener(onXCountDownTimerListener: OnXCountDownTimerListener) {
        mOnXCountDownTimerListener = onXCountDownTimerListener
    }

    /**
     * 启动倒计时
     * @param millisInFuture 倒计总时长，单位：ms
     * @param countDownInterval 倒计时间隔，单位：ms
     */
    fun startTimer(millisInFuture: Long, countDownInterval: Long) {
        if (millisInFuture <= 0) {
            mCancelled = true
            mOnXCountDownTimerListener?.onCountDownFinished()
            return
        }
        mCancelled = false
        mMillisInFuture = millisInFuture
        mCountdownInterval = countDownInterval
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture
        mCountDownHandler.sendMessage(mCountDownHandler.obtainMessage(COUNT_DOWN_MSG))
    }

    /**
     * 取消倒计时
     */
    fun cancelTimer() {
        mCancelled = true
        mCountDownHandler.removeMessages(COUNT_DOWN_MSG)
        clearAllTimeConfig()
        mOnXCountDownTimerListener?.onCountDownCancel()
    }

    /**
     * 重置倒计时
     * @param millisInFuture    倒计总时长，单位：ms
     * @param countDownInterval 倒计时间隔，单位：ms
     */
    fun resetTimer(millisInFuture: Long, countDownInterval: Long) {
        if (millisInFuture <= 0) {
            return
        }
        mCancelled = true
        mCountdownInterval = countDownInterval
        mMillisInFuture = millisInFuture
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture
        mCancelled = false
    }

    /**
     * 是否已停止
     */
    fun isCancelled(): Boolean = mCancelled

    /**
     * 清空所有的时间设置
     */
    private fun clearAllTimeConfig() {
        mMillisInFuture = 0L
        mStopTimeInFuture = 0L
        mCountdownInterval = 0L
        mRemainTimeUntilFinish = 0L
    }


    interface OnXCountDownTimerListener {
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