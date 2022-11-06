package com.shopping.app.ui.auth.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.User
import com.shopping.app.data.repository.auth.AuthRepository
import com.shopping.app.data.repository.user.UserRepository

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val userLiveData = MutableLiveData<DataState<User>>()
    private lateinit var user: User

    fun onSignUpClicked(username: String, email: String, password: String, passwordAgain: String){

        userLiveData.value = DataState.Loading()
        user = User(email, password, passwordAgain, username)
        checkFields()

    }

    private fun checkFields(){

        if(user.isSignUpFieldEmpty()){
            userLiveData.value = DataState.Error(R.string.fields_cannot_empty.toString())
            return
        }

        if(!user.isValidEmail()){
            userLiveData.value = DataState.Error(R.string.enter_valid_email.toString())
            return
        }

        if(!user.isPasswordMatch()){
            userLiveData.value = DataState.Error(R.string.passwords_dont_match.toString())
            return
        }

        if(!user.isPasswordGreaterThan5()){
            userLiveData.value = DataState.Error(R.string.password_greater_than_5.toString())
            return
        }

        signUp()

    }

    private fun signUp(){

        authRepository.signUp(user)
            .addOnSuccessListener {
                user.uid = it.user!!.uid
                userAddDatabase()
            }
            .addOnFailureListener { e ->
                userLiveData.value = DataState.Error(e.message!!)
            }

    }

    private fun userAddDatabase(){

        userRepository.userAddDatabase(user)
            .addOnSuccessListener {
                userLiveData.value = DataState.Success(user)
            }
            .addOnFailureListener { e ->
                userLiveData.value = DataState.Error(e.message!!)
            }

    }

}