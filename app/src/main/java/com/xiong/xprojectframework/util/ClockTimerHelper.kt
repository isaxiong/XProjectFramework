package com.xiong.xprojectframework.util

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler


/**
 * @author xiong
 * @since  2020/12/14
 * @description 定时器辅助类
 **/
object ClockTimerHelper {

    private var isClockStarted: Boolean = false
    private var mInternalHandler: Handler? = null
    private var mClockScheduleCallback: ClockScheduleCallback? = null
    // TODO xiong -- 补充：添加CountDownTimer
    private var mCountDownTimer: CountDownTimer? = null

    private val MSG_TIME_COMPLETED = 10001

    fun getClockTimerHandler(): Handler {
        if (mInternalHandler == null) {
            mInternalHandler = Handler {
                when (it.what) {
                    MSG_TIME_COMPLETED -> {
                        stopClock()
                        mClockScheduleCallback?.onClockUp()
                    }
                }
                true
            }
        }
        return mInternalHandler!!
    }


    fun startCountTime(internalTime: Long, clockScheduleCallback: ClockScheduleCallback) {
        if (isClockStarted) return
        isClockStarted = true
        mClockScheduleCallback = clockScheduleCallback
        mCountDownTimer = object : CountDownTimer(internalTime, 1000) {
            override fun onFinish() {
                mClockScheduleCallback?.onClockUp()
                stopCountTime()
            }

            override fun onTick(millisUntilFinished: Long) {
                mClockScheduleCallback?.onClockIntervalTimeTick(millisUntilFinished)
            }
        }
        if (internalTime == 0L) {
            stopCountTime()
        } else {
            mCountDownTimer?.start()
        }
    }

    fun stopCountTime() {
        if (!isClockStarted) return
        isClockStarted = false
        mCountDownTimer?.cancel()
        mCountDownTimer = null
    }

    fun isClockStart(): Boolean {
        return isClockStarted
    }



    /**
     * 绑定Context
     */
    private var mContext: Context? = null
    fun bindContext(context: Context): ClockTimerHelper {
        mContext = context
        return this
    }

    /**
     * 启动计时器
     * @param internalTime          定时时间（单位：ms）
     * @param clockScheduleCallback 定时器回调
     */
    fun startClock(internalTime: Long, clockScheduleCallback: ClockScheduleCallback) {
        if (isClockStarted) return
        isClockStarted = true
        mClockScheduleCallback = clockScheduleCallback
        if (internalTime == 0L) {
            stopClock()
        } else {
            getClockTimerHandler().sendEmptyMessageDelayed(MSG_TIME_COMPLETED, internalTime)
        }
    }

    /**
     * 暂停计时器
     */
    fun stopClock() {
        if (!isClockStarted) return
        isClockStarted = false
        getClockTimerHandler().removeMessages(MSG_TIME_COMPLETED)
    }

    /**
     * 重置计时器
     * @param internalTime 定时时间（单位：ms）
     */
    fun resetClock(internalTime: Long): Boolean {
        mClockScheduleCallback?.let {
            return resetClock(internalTime, it)
        } ?: return false
    }

    /**
     * 重置计时器
     * @param internalTime          定时时间（单位：ms）
     * @param clockScheduleCallback 定时器回调
     */
    fun resetClock(internalTime: Long, clockScheduleCallback: ClockScheduleCallback): Boolean {
        if (isClockStarted) {
            mClockScheduleCallback = clockScheduleCallback
            getClockTimerHandler().removeMessages(MSG_TIME_COMPLETED)
            getClockTimerHandler().sendEmptyMessageDelayed(MSG_TIME_COMPLETED, internalTime)
        } else {
            startClock(internalTime, clockScheduleCallback)
        }
        return true
    }

    interface ClockScheduleCallback {
        /**
         * 定时时间到
         */
        fun onClockUp()

        /**
         * 间隔时间回调
         * @param millisUntilFinished 间隔时间
         */
        fun onClockIntervalTimeTick(millisUntilFinished: Long)
    }

    class CallbackFactory {

        fun dispatchFragment() {

        }

        fun dispatchService() {

        }
    }

}