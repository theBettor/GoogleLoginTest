package com.auratarot.googlelogintest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.auratarot.googlelogintest.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class LogInViewModel: ViewModel() {
    private var authRepository: AuthRepository = AuthRepository()
    private val _userLiveData = authRepository.userLiveData

    val userLiveData: LiveData<FirebaseUser>
        get() = _userLiveData


    fun getUser(idToken: String){
        authRepository.getUser(idToken)
    }
}