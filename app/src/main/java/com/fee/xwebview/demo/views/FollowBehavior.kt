package com.fee.xwebview.demo.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.fee.xwebview.demo.R
/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
class FollowBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {


    init {

    }


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.dependency
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        child.y = dependency.y + child.height
//        println("--> onDependentViewChanged() child.y = ${child.y}, dependency.y = ${dependency.y},dependency.height = ${dependency.height}, child.height = ${child.height}")
        return true
    }


}