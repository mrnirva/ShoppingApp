package com.shopping.app.ui.auth.signin

import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.User
import com.shopping.app.data.preference.UserPref
import com.shopping.app.databinding.FragmentSignInBinding
import com.shopping.app.ui.auth.signin.viewmodel.SignInViewModel
import com.shopping.app.ui.loadingprogress.LoadingProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private lateinit var bnd: FragmentSignInBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        init()
        return bnd.root

    }

    private fun init(){

        bnd.viewModel = viewModel
        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.userLiveData.observe(viewLifecycleOwner){

            handleSignIn(it)

        }

    }

    private fun handleSignIn(it: DataState<User>){

        when(it){

            is DataState.Loading -> {
                loadingProgressBar.show()
            }

            is DataState.Success -> {
                loadingProgressBar.cancel()
                saveUserData(it.data)
            }

            is DataState.Error -> {
                loadingProgressBar.cancel()
                showAlertDialogMessage(it.message)
            }

        }

    }

    private fun saveUserData(user: User){

        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            userPref.setUsername(user.username!!)
            userPref.setEmail(user.email)
            userPref.setUid(user.uid!!)

            findNavController().navigate(R.id.action_authFragment_to_mainMenuFragment)

        }

    }

    private fun showAlertDialogMessage(message: String){

        var newMessage = message
        if(message.isDigitsOnly()){
            newMessage = getString(message.toInt())
        }

        AlertDialog.Builder(requireContext())
            .setMessage(newMessage)
            .setPositiveButton(resources.getString(R.string.close)) { dialog, _ ->
                dialog.cancel()
            }
            .show()

    }

}