package com.shopping.app.ui.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.User

class SignUpViewModel : ViewModel() {

    val userLiveData = MutableLiveData<DataState<FirebaseUser?>>()

    fun onSignUpClicked(username: String, email: String, password: String, passwordAgain: String){

        userLiveData.value = DataState.Loading()
        val user = User(email, password, passwordAgain, username)
        checkFields(user)

    }

    private fun checkFields(user: User){

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

        signUp(user)

    }

    private fun signUp(user: User){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->

            if(task.isSuccessful) {

                userLiveData.value = DataState.Success(task.result.user)

            }else {

                userLiveData.value = DataState.Error(task.exception?.message!!)

            }

        }

    }

}