package com.xiong.xprojectframework.util

import android.os.Handler


/**
 * @author xiong
 * @since  2020/12/16
 * @description 定时器辅助类
 **/
object ClockTimerHelper {

    private var isClockStarted: Boolean = false
    private var mInternalHandler: Handler? = null
    private var mClockScheduleCallback: ClockScheduleCallback? = null

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
    }

}