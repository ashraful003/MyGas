package com.example.mygas.presentation.login

import android.os.Bundle
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
import com.example.mygas.databinding.FragmentLoginChangePassBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class LoginChangePassFragment : Fragment() {
    val actionSignIn =
        Navigation.createNavigateOnClickListener(R.id.action_loginChangePassFragment_to_loginInputFragment)
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginChangePassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_change_pass, container, false)
        binding.model = this
        isEnableSubmitButton(false)
        val passwordStream = RxTextView.textChanges(binding.newPasswordEt)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }

        val confirmPassStream = Observable.merge(
            RxTextView.textChanges(binding.newPasswordEt)
                .skipInitialValue()
                .map { password ->
                    password.toString() != binding.newConfirmPasswordEt.text.toString()
                },
            RxTextView.textChanges(binding.newConfirmPasswordEt)
                .skipInitialValue()
                .map { confirmPass ->
                    confirmPass.toString() != binding.newPasswordEt.text.toString()
                })
        val invalidFiledStream = Observable.combineLatest(
            passwordStream,
            confirmPassStream
        ) { passwordInvalid: Boolean, confirmPassInvalid: Boolean ->
            !passwordInvalid && !confirmPassInvalid
        }
        invalidFiledStream.subscribe { isValid ->
            isEnableSubmitButton(isValid)
        }

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun isEnableSubmitButton(isEnable:Boolean?){
        if (isEnable == true){
            binding.btnDone.isEnabled = true
            binding.btnDone.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.red_500)
        }
        else{
            binding.btnDone.isEnabled = false
            binding.btnDone.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500_shadow)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

}