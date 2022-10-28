package com.shopping.app.ui.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shopping.app.data.model.DataState

class SignInViewModel : ViewModel() {

    val userLiveData = MutableLiveData<DataState<FirebaseUser?>>()

    fun onSignInClicked(email: String, password: String){

        userLiveData.value = DataState.Loading()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if(task.isSuccessful) {

                userLiveData.value = DataState.Success(task.result.user)

            }else {

                userLiveData.value = DataState.Error(task.exception?.message!!)

            }

        }

    }

}