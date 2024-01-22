package com.example.mygas.presentation.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.FragmentLoginVerifyPassBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class LoginVerifyPassFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginVerifyPassBinding
    val actionReset =
        Navigation.createNavigateOnClickListener(R.id.action_loginVerifyPassFragment_to_loginChangePassFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_verify_pass, container, false)
        binding.model = this
        isEnableSubmitButton(false)
        val otpOneStream = RxTextView.textChanges(binding.otpOne)
            .skipInitialValue()
            .map { otpOne ->
                otpOne.isEmpty()
            }
        val otpTwoStream = RxTextView.textChanges(binding.otpTwo)
            .skipInitialValue()
            .map { otpTwo ->
                otpTwo.isEmpty()
            }
        val otpThreeStream = RxTextView.textChanges(binding.otpThree)
            .skipInitialValue()
            .map { otpThree ->
                otpThree.isEmpty()
            }
        val otpFourStream = RxTextView.textChanges(binding.otpFour)
            .skipInitialValue()
            .map { otpFour ->
                otpFour.isEmpty()
            }
        val invalidFieldSteam = Observable.combineLatest(
            otpOneStream,
            otpTwoStream,
            otpThreeStream,
            otpFourStream
        ) { otpOneInvalid: Boolean, otpTwoInvalid: Boolean, otpThreeInvalid: Boolean, otpFourInvalid: Boolean ->
            !otpOneInvalid && !otpTwoInvalid && !otpThreeInvalid && !otpFourInvalid
        }
        invalidFieldSteam.subscribe { isValid ->
            isEnableSubmitButton(isValid)
        }
        addTextWatcher(binding.otpOne)
        addTextWatcher(binding.otpTwo)
        addTextWatcher(binding.otpThree)
        addTextWatcher(binding.otpFour)
        binding.verificationErrorTv.visibility = View.GONE
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun addTextWatcher(one: EditText?) {
        one!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                when (one.id) {
                    R.id.otpOne -> if (one.length() == 1) {
                        binding.otpTwo.requestFocus()
                        binding.otpTwo.setSelection(binding.otpTwo.length())
                    } else if (one.length() == 0) {
                        binding.otpOne.clearFocus()
                    }

                    R.id.otpTwo -> if (one.length() == 1) {
                        binding.otpThree.requestFocus()
                        binding.otpThree.setSelection(binding.otpThree.length())
                    } else if (one.length() == 0) {
                        binding.otpTwo.requestFocus()
                    }

                    R.id.otpThree -> if (one.length() == 1) {
                        binding.otpFour.requestFocus()
                        binding.otpFour.setSelection(binding.otpFour.length())
                    } else if (one.length() == 0) {
                        binding.otpThree.requestFocus()
                    }

                    R.id.otpFour -> if (one.length() == 1) {
                        binding.otpThree.setSelection(binding.otpThree.length())
                        val inputmanager =
                            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputmanager.hideSoftInputFromWindow(
                            requireActivity().getCurrentFocus()!!.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    } else if (one.length() == 0) {
                        binding.otpThree.requestFocus()
                    }
                }
            }
        })
    }

    private fun isEnableSubmitButton(isEnable: Boolean) {
        if (isEnable) {
            binding.btnSubmit.isEnabled = true
            binding.btnSubmit.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_500)
        } else {
            binding.btnSubmit.isEnabled = false
            binding.btnSubmit.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_500_shadow)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }
}