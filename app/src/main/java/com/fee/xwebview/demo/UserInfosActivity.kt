package com.fee.xwebview.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fee.xwebview.demo.databinding.ActivityUserinfoBinding
import com.fee.xwebview.demo.viewModels.UserViewModel
import com.tencent.smtt.utils.l

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/9/27<br>
 * Time: 14:27<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
class UserInfosActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityUserinfoBinding

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = UserViewModel()
        mViewBinding = ActivityUserinfoBinding.inflate(layoutInflater)
//        mViewBinding.userViewModel = userViewModel
//        binding.lifecycleOwner = this
    }

}