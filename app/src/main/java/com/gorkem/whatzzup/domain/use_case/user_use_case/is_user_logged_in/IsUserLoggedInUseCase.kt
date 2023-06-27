package com.gorkem.whatzzup.domain.use_case.user_use_case.is_user_logged_in

import com.google.firebase.auth.FirebaseUser
import com.gorkem.whatzzup.domain.repository.FirebaseRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(private val repository: FirebaseRepository) {

    operator fun invoke() : FirebaseUser? = repository.getCurrentUser()
}