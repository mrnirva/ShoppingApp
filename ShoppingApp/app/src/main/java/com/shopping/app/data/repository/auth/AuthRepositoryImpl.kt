package com.shopping.app.data.repository.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.shopping.app.data.model.User

class AuthRepositoryImpl : AuthRepository {

    override fun signIn(user: User): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email!!, user.password!!)
    }

    override fun signUp(user: User): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email!!, user.password!!)
    }

}