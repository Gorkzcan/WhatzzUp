package com.gorkem.whatzzup.presentation.splash

import androidx.lifecycle.ViewModel
import com.gorkem.whatzzup.domain.use_case.user_use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(private val userUseCases: UserUseCases): ViewModel(){
    var currentUser = userUseCases.isUserLoggedInUseCase()
}