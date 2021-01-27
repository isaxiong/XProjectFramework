package com.xiong.xprojectframework.page

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author xiong
 * @since  2021/1/27
 * @description 具备缓存功能的FragmentPagerAdapter，主要用于ViewPager上Fragment的缓存Tag进行拓展
 * （支持Fragment存储Tag自定义及PageTitle绑定）
 **/
class CacheFragmentPageAdapter(private val context: Context
                               , private val fragmentPageContainer: CacheFragmentPageContainer<out CacheFragmentPageItem>
                               , fragmentManager: FragmentManager
                               , behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    : FragmentPagerAdapter(fragmentManager, behavior) {

    override fun getItem(position: Int): Fragment {
        return fragmentPageContainer[position].instantiate(context)
    }

    override fun getCount(): Int {
        return fragmentPageContainer.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentPageContainer[position].getPageTitle()
    }

    override fun getItemId(position: Int): Long {
        val itemId = fragmentPageContainer[position].getTagId()?.hashCode()?.toLong()
        Log.d("xiong"," ==== 缓存的Fragment tag = $itemId === ")
        return itemId ?: super.getItemId(position)
    }

}