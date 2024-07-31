package com.auratarot.googlelogintest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auratarot.googlelogintest.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class HomeViewModel : ViewModel() {
    // 예: FirebaseUser 또는 사용자 데이터를 관리하는 LiveData
    private val _userLiveData = MutableLiveData<FirebaseUser>()
    val userLiveData: LiveData<FirebaseUser> get() = _userLiveData

    fun setUser(user: FirebaseUser) {
        _userLiveData.value = user
    }
}


