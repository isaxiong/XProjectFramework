package com.xiong.xprojectframework.util

/**
 * @author xiong
 * @since 2020/12/7
 * TODO xiong -- 完善：1、List聚合类空Item判断 2、also、with等补充
 * @description Kotlin 空处理拓展工具，可以根据判空的参数数量进行拓展，支持一次性多个空参数处理
 **/
object NullHandleExtendUtil {

    /**
     * 安全空参数校验（let拓展）
     * @param T  调用方
     * @param p1 参数Param1
     */
    inline fun <T, P1, R> T.safeNullableParamLet(p1: P1?, block: (T, P1) -> R?): R? {
        this.let {
            return if (p1 != null) block(it, p1) else null
        }
    }

    /**
     * 安全空参数校验（let拓展）
     * @param T  调用方
     * @param p1 参数Param1
     * @param p2 参数Param2
     */
    inline fun <T, P1, P2, R> T.safeNullableParamLet(p1: P1?, p2: P2?, block: (T, P1, P2) -> R?) : R? {
        this.let {
            return if (p1 != null && p2 != null) block(this, p1, p2) else null
        }
    }

    /**
     * 安全空参数校验（let拓展）
     * @param T  调用方
     * @param p1 参数Param1
     * @param p2 参数Param2
     */
    inline fun <T, P1, P2, P3, R> T.safeNullableParamLet(p1: P1?, p2: P2?, p3: P3?, block: (T, P1, P2, P3) -> R?) : R? {
        this.let {
            return if (p1 != null && p2 != null && p3 != null) block(this, p1, p2, p3) else null
        }
    }

    /**
     * 安全空参数校验（apply拓展）
     * @param T  调用方
     * @param p1 参数Param1
     */
    inline fun <T, P1> T.safeNullableParamApply(p1: P1?, block: T.(P1) -> Unit): T? {
        this.apply {
            return if (p1 != null) {
                block(this, p1)
                this
            } else {
                null
            }
        }
    }

    /**
     * 安全空参数校验（apply拓展）
     * @param T  调用方
     * @param p1 参数Param1
     * @param p2 参数Param2
     */
    inline fun <T, P1, P2> T.safeNullableParamApply(p1: P1?, p2: P2?, block: T.(P1, P2) -> Unit): T? {
        this.apply {
            return if (p1 != null && p2 != null) {
                block(this, p1, p2)
                this
            } else {
                null
            }
        }
    }



    /**
     * 安全空参数校验拓展
     * @param p1 参数Param1
     * @param p2 参数Param2
     */
    fun <T1: Any, T2: Any, R: Any> safeNullableParam(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
        return if (p1 != null && p2 != null) block(p1, p2) else null
    }

    /**
     * 安全空参数校验拓展
     * @param p1 参数Param1
     * @param p2 参数Param2
     * @param p3 参数Param3
     */
    fun <T1: Any, T2: Any, T3: Any, R: Any> safeNullableParam(p1: T1?, p2: T2?, p3: T3?
                                                                        , block: (T1, T2, T3) -> R?): R? {
        return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
    }

}