package com.example.mygas.presentation.dashboard

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.mygas.R
import com.example.mygas.databinding.FragmentProfileBinding
import com.example.mygas.presentation.MainActivity
import com.example.mygas.util.MGActivityUtil
import com.example.mygas.util.SharePreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    val actionUpdateProfile = Navigation.createNavigateOnClickListener(R.id.action_profileFragment_to_updateProfileFragment)
    @Inject
    lateinit var sharedPrefs: SharePreferencesUtil
    @Inject
    lateinit var activityUtil:MGActivityUtil
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(false)
        binding.btnSignOut.setOnClickListener {
            logout(it)
        }
        return binding.root
    }

    private fun logout(view: View) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString(R.string.are_you_sure))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            sharedPrefs.setAuthToken("")
            activity?.let {
                startActivity(MainActivity.getLaunchIntent(it))
            }
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}