package com.gorkem.whatzzup.domain.use_case.user_use_case

import com.gorkem.whatzzup.domain.use_case.user_use_case.get_user_data.GetUserDataUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.is_user_logged_in.IsUserLoggedInUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.login_user.LoginUserUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.logout_user.LogoutUserUseCase
import com.gorkem.whatzzup.domain.use_case.user_use_case.register_user.RegisterUserUseCase


data class UserUseCases(
    val getUserDataUseCase: GetUserDataUseCase,
    val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    val loginUserUseCase: LoginUserUseCase,
    val logoutUserUseCase: LogoutUserUseCase,
    val registerUserUseCase: RegisterUserUseCase
)