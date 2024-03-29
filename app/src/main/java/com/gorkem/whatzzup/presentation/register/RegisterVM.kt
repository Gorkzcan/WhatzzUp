package com.gorkem.whatzzup.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorkem.whatzzup.domain.use_case.user_use_case.UserUseCases
import com.gorkem.whatzzup.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterVM @Inject constructor(
    private val userUseCases: UserUseCases
): ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading = _isLoading
    var result = MutableLiveData<Result>()


    // Prevent repeat of login btn snackbar message on config change
    fun resetResult(){
        result = MutableLiveData<Result>()
    }


    fun registerUser(email:String, password:String, username:String): LiveData<Result>{
        viewModelScope.launch {
            _isLoading.postValue(true)
            result.value = userUseCases.registerUserUseCase(email,password,username)
            _isLoading.postValue(false)
        }
        return result
    }
}