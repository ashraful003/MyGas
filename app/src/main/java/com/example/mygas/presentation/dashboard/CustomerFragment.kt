package com.example.mygas.presentation.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.FragmentCustomerBinding
import com.example.mygas.util.MGActivityUtil
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import javax.inject.Inject

@AndroidEntryPoint
class CustomerFragment : Fragment() {
    private lateinit var binding:FragmentCustomerBinding
    @Inject
    lateinit var activityUtil:MGActivityUtil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_customer, container, false)
        binding.model = this
        isEnableSaveButton(false)
        activityUtil.hideBottomNavigation(true)
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        val fullNameStream = RxTextView.textChanges(binding.customerFullNameEt)
            .skipInitialValue()
            .map { name->
                name.isEmpty()
            }
        fullNameStream.subscribe {
           binding.customerFullNameEt.error = if (it) getString(R.string.error_name) else null
        }

        val numberStream = RxTextView.textChanges(binding.mobileNumberEt)
            .skipInitialValue()
            .map { number ->
                number.length<11
            }
        numberStream.subscribe {
            binding.mobileNumberEt.error = if (it) getString(R.string.error_number) else null
        }

        val retailNameStream = RxTextView.textChanges(binding.retailEt)
            .skipInitialValue()
            .map { retail ->
                retail.isEmpty()
            }
        retailNameStream.subscribe {
          binding.retailEt.error = if (it) getString(R.string.error_retail) else null
        }

        val addressStream = RxTextView.textChanges(binding.addressEt)
            .skipInitialValue()
            .map { address ->
                address.isEmpty()
            }
        addressStream.subscribe {
            binding.addressEt.error = if (it) getString(R.string.error_address) else null
        }

        val invalidFiledStream = Observable.combineLatest(
            fullNameStream,
            numberStream,
            retailNameStream,
            addressStream
        ){fullNameInvalid:Boolean,numberInvalid:Boolean, retailInvalid:Boolean, addressInvalid:Boolean ->
            !fullNameInvalid && !numberInvalid && ! retailInvalid && !addressInvalid
        }
        invalidFiledStream.subscribe {isValid ->
            isEnableSaveButton(isValid)
        }


        return binding.root
    }

    private fun isEnableSaveButton(isEnable:Boolean){
        if (isEnable){
            binding.saveButton.isEnabled = true
            binding.saveButton.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500)
        }else
            {
                binding.saveButton.isEnabled = false
                binding.saveButton.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500_shadow)
            }

    }
}