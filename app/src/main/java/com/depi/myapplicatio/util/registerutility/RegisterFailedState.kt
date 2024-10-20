package com.depi.myapplicatio.util.registerutility

data class RegisterFailedState(
    val email: RegisterValidation,
    val password: RegisterValidation
) {

}
