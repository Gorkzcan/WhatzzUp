package com.gorkem.whatzzup.domain.use_case.user_use_case.register_user

import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import com.gorkem.whatzzup.util.Result
import java.net.PasswordAuthentication
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: FirebaseRepository) {

    suspend operator fun invoke(email:String, password:String, username:String): Result{
        return if(isDataValid(email, password, username)){
            repository.registerUser(email, password, username)
        }else{
            Result.Error("Fields Can't be Empty")
        }
    }


    private fun isDataValid(email:String, password: String,username: String) = email.trim().isNotEmpty() && password.trim().isNotEmpty() && username.trim().isNotEmpty()
}