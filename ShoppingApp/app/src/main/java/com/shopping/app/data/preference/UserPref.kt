package com.shopping.app.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.shopping.app.utils.Constants
import kotlinx.coroutines.flow.first

class UserPref(var context: Context) {

    companion object{
        val Context.ds : DataStore<Preferences> by preferencesDataStore(Constants.DATA_STORE_USER)
        val KEY_USERNAME = stringPreferencesKey("USERNAME")
        val KEY_EMAIL = stringPreferencesKey("EMAIL")
        val KEY_IS_FIRST_USAGE = booleanPreferencesKey("IS_FIRST_USAGE")
    }

    suspend fun setUsername(username:String){
        context.ds.edit {
            it[KEY_USERNAME] = username
        }
    }

    suspend fun getUsername():String{
        val p = context.ds.data.first()
        return p[KEY_USERNAME] ?: ""
    }

    suspend fun setEmail(email:String){
        context.ds.edit {
            it[KEY_EMAIL] = email
        }
    }

    suspend fun getEmail():String{
        val p = context.ds.data.first()
        return p[KEY_EMAIL] ?: ""
    }

    suspend fun setFirstUsage(value:Boolean){
        context.ds.edit {
            it[KEY_IS_FIRST_USAGE] = value
        }
    }

    suspend fun isFirstUsage():Boolean{
        val p = context.ds.data.first()
        return p[KEY_IS_FIRST_USAGE] ?: true
    }

    suspend fun clearAllPreference() {
        context.ds.edit {
            it.clear()
        }
        setFirstUsage(false)
    }

}