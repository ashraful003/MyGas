package com.example.mygas.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.FragmentSalesChooseCustomerBinding
import com.example.mygas.util.MGActivityUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SalesChooseCustomerFragment : Fragment() {
    val actionAddProduct = Navigation.createNavigateOnClickListener(R.id.action_salesChooseCustomerFragment_to_productFragment)
    @Inject
    lateinit var activityUtil: MGActivityUtil
    private lateinit var binding:FragmentSalesChooseCustomerBinding
    private lateinit var viewModel: SelsChooseCustomerViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sales_choose_customer, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SelsChooseCustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }
}