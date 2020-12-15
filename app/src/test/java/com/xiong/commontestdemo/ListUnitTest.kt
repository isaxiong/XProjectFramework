package com.xiong.commontestdemo

import org.junit.Test

/**
 * @author xiong
 * @since  2020/11/25
 * @description 列表功能测试
 **/
class ListUnitTest {

    val testList: ArrayList<Int> = ArrayList()

    @Test
    fun testForEachReturn() {
        for (i in 0 until 20) {
            testList.add(i)
        }
        print("Test -- TestList = $testList")
        println()

        // filter
        testList.forEach {
            if (it == 10) return@forEach
            println("$it")
        }
    }

}