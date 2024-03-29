package com.example.mygas.presentation.login

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.FragmentLoginCreateBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.Calendar
class LoginCreateFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginCreateBinding
    @SuppressLint("CheckResult", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_create, container, false)
        binding.model = this
        isEnableSignUpButton(false)
        val fullNameStream = RxTextView.textChanges(binding.fullNameEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        fullNameStream.subscribe {
            binding.fullNameEt.error = if (it) getString(R.string.error_name) else null
        }

        val emailStream = RxTextView.textChanges(binding.emailEt)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.emailEt.error = if (it) getString(R.string.error_email) else null
        }

        val numberStream = RxTextView.textChanges(binding.phoneNumberEt)
            .skipInitialValue()
            .map { phone ->
                phone.length < 11
            }
        numberStream.subscribe {
            binding.phoneNumberEt.error = if (it) getString(R.string.error_number) else null
        }

        val locationStream = RxTextView.textChanges(binding.locationEt)
            .skipInitialValue()
            .map { location ->
                location.isEmpty()
            }
        locationStream.subscribe {
            binding.locationEt.error = if (it) getString(R.string.error_address) else null
        }

        val dobStream = RxTextView.textChanges(binding.dobEt)
            .skipInitialValue()
            .map { dob ->
                dob.isEmpty()
            }
        dobStream.subscribe {
            binding.dobEt.error = if (it) getString(R.string.error_dob) else null
        }
        binding.dobEt.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP){
                val datePickerDialog = DatePickerDialog(requireContext())
                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year,month,dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val formattedDate = dateFormat.format(selectedDate.time)
                    binding.dobEt.setText(formattedDate)
                }
                datePickerDialog.show()
                return@OnTouchListener true
            }
            false
        })

        val passwordStream = RxTextView.textChanges(binding.passwordBt)
            .map { password ->
                password.isEmpty()
            }

        val conformPasswordStream = Observable.merge(
            RxTextView.textChanges(binding.passwordBt)
                .map { password ->
                    password.toString() != binding.confirmPassEt.text.toString()
                },
            RxTextView.textChanges(binding.confirmPassEt)
                .skipInitialValue()
                .map { confirm ->
                    confirm.toString() != binding.passwordBt.text.toString()
                })
        val invalidFieldStream = io.reactivex.Observable.combineLatest(
            fullNameStream,
            emailStream,
            numberStream,
            locationStream,
            dobStream,
            passwordStream,
            conformPasswordStream
        ) { nameInvalid: Boolean, emailInvalid: Boolean, numberInvalid: Boolean, locationInvalid: Boolean, dateOfBirthInvalid: Boolean, passwordInvalid: Boolean, passwordConfirmInvalid: Boolean ->
            !nameInvalid && !emailInvalid && !numberInvalid && !locationInvalid && !dateOfBirthInvalid && !passwordInvalid && !passwordConfirmInvalid }
        invalidFieldStream.subscribe { isValid ->
            isEnableSignUpButton(isValid)}

        binding.backIv.setOnClickListener {
          findNavController().popBackStack()
        }
        return binding.root
    }

    private fun isEnableSignUpButton(isEnable:Boolean?){
        if (isEnable == true){
            binding.continueButton.isEnabled = true
            binding.continueButton.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.red_500)
        }
        else{
            binding.continueButton.isEnabled = false
            binding.continueButton.backgroundTintList = ContextCompat.getColorStateList(requireActivity(),R.color.red_500_shadow)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }
}