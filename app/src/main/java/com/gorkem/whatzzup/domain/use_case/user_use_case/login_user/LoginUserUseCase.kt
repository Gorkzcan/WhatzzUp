package com.gorkem.whatzzup.domain.use_case.user_use_case.login_user

import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.util.Result
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: FirebaseRepository){
    suspend operator fun invoke(email:String, password:String): Result{
        return if (isDataValid(email,password)){
            repository.loginUser(email,password)
        }else{
            Result.Error("Fields Can't be Empty")
        }
    }


    private fun isDataValid(email:String, password: String) = email.trim().isNotEmpty() && password.trim().isNotEmpty()
}