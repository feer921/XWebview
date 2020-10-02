package com.fee.xwebview.viewModels

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fee.xwebview.datas.User

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/9/27<br>
 * Time: 13:15<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
class UserViewModel : ViewModel() {

    private var liveDataOfUser: MutableLiveData<User>? = null

    fun getUserLiveData(): MutableLiveData<User>? {
        if (liveDataOfUser == null) {
            liveDataOfUser = MutableLiveData()
        }
        return liveDataOfUser
    }


    fun test() {
    }






}