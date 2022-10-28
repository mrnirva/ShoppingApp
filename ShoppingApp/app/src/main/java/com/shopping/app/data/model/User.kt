package com.shopping.app.data.model

import android.text.TextUtils
import android.util.Patterns

class User(
    val email: String,
    val password: String,
    val passwordAgain: String = "",
    val username: String = ""
) {

    fun isSignInFieldEmpty() : Boolean{
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
    }

    fun isSignUpFieldEmpty() : Boolean{
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain) || TextUtils.isEmpty(username)
    }

    fun isValidEmail() : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordGreaterThan5() : Boolean{
        return password.length > 5
    }

    fun isPasswordMatch() : Boolean{
        return password == passwordAgain
    }

}