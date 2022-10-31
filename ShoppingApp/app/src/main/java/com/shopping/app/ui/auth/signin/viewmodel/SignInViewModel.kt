package com.shopping.app.ui.auth.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.User
import com.shopping.app.utils.Constants

class SignInViewModel : ViewModel() {

    val userLiveData = MutableLiveData<DataState<User>>()

    fun onSignInClicked(email: String, password: String){

        userLiveData.value = DataState.Loading()
        val user = User(email, password)
        checkFields(user)

    }

    private fun checkFields(user: User){

        if(user.isSignInFieldEmpty()){
            userLiveData.value = DataState.Error(R.string.fields_cannot_empty.toString())
            return
        }

        if(!user.isValidEmail()){
            userLiveData.value = DataState.Error(R.string.enter_valid_email.toString())
            return
        }

        if(!user.isPasswordGreaterThan5()){
            userLiveData.value = DataState.Error(R.string.password_greater_than_5.toString())
            return
        }

        signIn(user)

    }

    private fun signIn(user: User){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->

            if(task.isSuccessful) {

                user.uid = task.result.user?.uid
                getUserFirestore(user)

            }else {

                userLiveData.value = DataState.Error(task.exception?.message!!)

            }

        }

    }

    private fun getUserFirestore(user: User){

        val db = Firebase.firestore

        db.collection(Constants.FIRESTORE_USERS_TABLE)
            .document(user.uid!!)
            .addSnapshotListener { value, error ->

                if(error == null){

                    for((k,v) in value?.data!!.iterator()){

                        if(k.equals("username")){
                            user.username = v.toString()
                            break
                        }

                    }

                    userLiveData.value = DataState.Success(user)

                }else{
                    userLiveData.value = DataState.Error(error.message!!)
                }

            }

    }

}