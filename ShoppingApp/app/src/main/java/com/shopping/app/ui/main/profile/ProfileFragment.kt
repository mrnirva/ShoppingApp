package com.shopping.app.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.shopping.app.R
import com.shopping.app.data.preference.UserPref
import com.shopping.app.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var bnd: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        bnd.profileFragment = this
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserData()
    }

    private fun getUserData(){

        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            bnd.username = userPref.getUsername()
            bnd.email = userPref.getEmail()

        }

    }

    fun signOutDialog(){

        AlertDialog.Builder(requireContext())
            .setMessage(resources.getString(R.string.sign_out_message))
            .setPositiveButton(resources.getString(R.string.continue_)) { dialog, _ ->
                dialog.cancel()
                signOut()
            }.setNegativeButton(resources.getString(R.string.cancel)){ dialog, _ ->
                dialog.cancel()
            }
            .show()

    }

    private fun signOut(){

        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            FirebaseAuth.getInstance().signOut()
            userPref.clearAllPreference()

            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView)
                        as NavHostFragment
            val mNavController = navHostFragment.navController
            mNavController.navigate(R.id.action_mainMenuFragment_to_splashFragment)

        }

    }

}