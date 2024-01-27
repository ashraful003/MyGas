package com.example.mygas.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mygas.R
import com.example.mygas.databinding.FragmentHomeBinding
import com.example.mygas.presentation.MainActivity
import com.example.mygas.presentation.login.LoginViewModel
import com.example.mygas.util.MGActivityUtil
import com.example.mygas.util.SharePreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var activityUtil: MGActivityUtil
    private lateinit var viewModel: HomeViewModel
    val actionCustomer = Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_customerFragment)
    val actionSales = Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_salesFragment)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }
}