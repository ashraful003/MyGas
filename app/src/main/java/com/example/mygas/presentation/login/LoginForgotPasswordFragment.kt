package com.example.mygas.presentation.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.FragmentLoginForgotPasswordBinding
import com.jakewharton.rxbinding2.widget.RxTextView

class LoginForgotPasswordFragment : Fragment() {
    val actionVarify = Navigation.createNavigateOnClickListener(R.id.action_loginForgotPasswordFragment_to_loginVerifyPassFragment)
private lateinit var viewModel: LoginViewModel
private lateinit var binding:FragmentLoginForgotPasswordBinding
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_forgot_password, container, false)
        binding.model = this
        isEnableForgotButton(false)
        val emailStream = RxTextView.textChanges(binding.emailEt)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.btnNext.error = if (it) getString(R.string.error_email) else null
            if (it){
                binding.btnNext.isEnabled = false
                binding.btnNext.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500_shadow)
            }
            else{
                binding.emailEt.error = null
                binding.btnNext.isEnabled = true
                binding.btnNext.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500)
            }
        }
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
    fun isEnableForgotButton(isEnavle:Boolean){
        if (isEnavle){
            binding.btnNext.isEnabled = true
            binding.btnNext.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500)
        }
        else{
            binding.btnNext.isEnabled = false
            binding.btnNext.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500_shadow)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }
}