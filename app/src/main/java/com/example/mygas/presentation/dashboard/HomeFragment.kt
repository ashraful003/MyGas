package com.example.mygas.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mygas.R
import com.example.mygas.databinding.FragmentHomeBinding
import com.example.mygas.presentation.MainActivity
import com.example.mygas.presentation.login.LoginViewModel
import com.gas.mygasbd.util.MGActivityUtil
import com.gas.mygasbd.util.SharePreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Inject
    lateinit var sharedPrefs: SharePreferencesUtil
    private lateinit var binding: FragmentHomeBinding
    private lateinit var activityUtil: MGActivityUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.model = this
        binding.btnSignOut.setOnClickListener {
            sharedPrefs.setAuthToken("")
            activity?.let {
                startActivity(MainActivity.getLaunchIntent(it))
            }
        }

        return binding.root
    }
}