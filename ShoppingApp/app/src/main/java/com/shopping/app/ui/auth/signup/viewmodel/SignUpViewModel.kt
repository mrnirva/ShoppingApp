package com.shopping.app.ui.auth.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.User
import com.shopping.app.utils.Constants

class SignUpViewModel : ViewModel() {

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

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->

            if(task.isSuccessful) {

                user.uid = task.result.user!!.uid
                userAddDatabase()

            }else {

                userLiveData.value = DataState.Error(task.exception?.message!!)

            }

        }

    }

    private fun userAddDatabase(){

        val db = Firebase.firestore

        val userMap = hashMapOf(
            "username" to user.username,
            "uid" to user.uid,
        )

        db.collection(Constants.FIRESTORE_USERS_TABLE)
            .add(userMap)
            .addOnSuccessListener {
                userLiveData.value = DataState.Success(user)
            }
            .addOnFailureListener { e ->
                userLiveData.value = DataState.Error(e.message!!)
            }

    }

}