package com.gorkem.whatzzup.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.whatzzup.domain.use_case.note_use_case.NoteUseCases

import com.gorkem.whatzzup.domain.use_case.user_use_case.UserUseCases
import com.gorkem.whatzzup.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val userUseCases: UserUseCases
): ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading
    var result = MutableLiveData<Result>()

    fun resetResult(){
        result = MutableLiveData<Result>()
    }

    fun loginUser(email:String, password:String) : LiveData<Result> {
        viewModelScope.launch {
            _isLoading.postValue(true)
            result.value = userUseCases.loginUserUseCase(email,password)
            _isLoading.postValue(false)
        }
        return result
    }
}