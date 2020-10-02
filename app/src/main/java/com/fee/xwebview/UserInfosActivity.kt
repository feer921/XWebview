package com.fee.xwebview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fee.xwebview.databinding.ActivityUserinfoBinding
import com.fee.xwebview.viewModels.UserViewModel

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

    private lateinit var binding: ActivityUserinfoBinding

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = UserViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_userinfo)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

    }

}