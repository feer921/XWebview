package com.fee.xwebview

import android.os.Build

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/9/28<br>
 * Time: 17:17<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
object Api {
    /**
     * 是否向下兼容某个系统版本
     * @param minApiLevle 要兼容的某个版本
     * @return true:兼容，即当前系统版本大于或者等于目标要兼容的版本；false：不兼容，即当前系统版本小于目标版本
     */
    fun isApiCompatible(minApiLevle: Int): Boolean {
        if (minApiLevle <= 0) return false
        return minApiLevle <= Build.VERSION.SDK_INT
    }

    fun isSysApiGreaterThan(compareApiLevel: Int): Boolean {
        return if (compareApiLevel <= 0) {
            false
        } else compareApiLevel < Build.VERSION.SDK_INT
    }
}