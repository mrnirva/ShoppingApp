package com.shopping.app.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shopping.app.R
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.User
import com.shopping.app.databinding.FragmentSignUpBinding
import com.shopping.app.ui.auth.signup.viewmodel.SignUpViewModel
import com.shopping.app.ui.loadingprogress.LoadingProgressBar

class SignUpFragment : Fragment() {

    private lateinit var bnd: FragmentSignUpBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        init()
        return bnd.root

    }

    private fun init(){

        bnd.viewModel = viewModel
        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.userLiveData.observe(viewLifecycleOwner){

            handleSignUp(it)

        }

    }

    private fun handleSignUp(it: DataState<User>){

        when(it){

            is DataState.Loading -> {
                loadingProgressBar.show()
            }

            is DataState.Success -> {
                loadingProgressBar.cancel()
            }

            is DataState.Error -> {
                loadingProgressBar.cancel()
                showAlertDialogMessage(it.message)
            }

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