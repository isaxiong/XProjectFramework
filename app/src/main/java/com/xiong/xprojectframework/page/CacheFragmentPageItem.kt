package com.xiong.xprojectframework.page

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * @author xiong
 * @since  2021/1/27
 * @description CacheFragmentPageItem绑定Fragment与其他数据的最小单元
 * （将Fragment绑定对应的PageTitle与唯一标志Tag，如需绑定更多数据，可继承该类进行拓展）
 **/
open class CacheFragmentPageItem(private val className: String, private val args: Bundle?,
                            private val pageTitle: String, private val fragmentTag: String?) {

    /**
     * 实例化Fragment
     * TODO xiong -- 完善：使用FragmentFactory来创建Fragment
     */
    fun instantiate(context: Context): Fragment {
        return Fragment.instantiate(context, className, args)
    }

    /**
     * 获取ViewPager中Fragment绑定的PageTitle
     */
    fun getPageTitle(): String = pageTitle

    /**
     * 获取Fragment的唯一标记Tag
     */
    fun getTagId(): String? = fragmentTag


    companion object {
        fun of(clazz: Class<out Fragment>, args: Bundle?, pageTitle: String, fragmentTag: String?): CacheFragmentPageItem
                = of(clazz.name, args, pageTitle, fragmentTag)

        fun of(className: String, args: Bundle?, pageTitle: String, fragmentTag: String?): CacheFragmentPageItem
                = CacheFragmentPageItem(className, args ?: Bundle(), pageTitle, fragmentTag)
    }

}