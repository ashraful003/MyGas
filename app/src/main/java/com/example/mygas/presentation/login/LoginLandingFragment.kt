package com.example.mygas.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.mygas.R
import com.example.mygas.databinding.FragmentLoginLandingBinding

class LoginLandingFragment : Fragment() {
   val actionLogin = Navigation.createNavigateOnClickListener(R.id.action_loginLandingFragment_to_loginInputFragment)
    val actionSignUp = Navigation.createNavigateOnClickListener(R.id.action_loginLandingFragment_to_loginCreateFragment)
    private lateinit var binding:FragmentLoginLandingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_landing, container, false)
        binding.model = this
        // Inflate the layout for this fragment
        return binding.root
    }
}