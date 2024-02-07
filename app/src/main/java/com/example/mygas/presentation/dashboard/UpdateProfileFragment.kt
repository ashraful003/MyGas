package com.example.mygas.presentation.dashboard

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.mygas.R
import com.example.mygas.databinding.FragmentUpdateProfileBinding
import com.example.mygas.util.MGActivityUtil
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {
    @Inject
    lateinit var activityUtil:MGActivityUtil
    private lateinit var viewModel: UpdateProfileViewModel
    private lateinit var binding:FragmentUpdateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_update_profile, container, false)
        binding.model = this
        activityUtil.hideBottomNavigation(true)

        val fullNameStream = RxTextView.textChanges(binding.fullNameEt)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        fullNameStream.subscribe {
            binding.fullNameEt.error = if (it) getString(R.string.error_name) else null
        }

        val passwordStream = RxTextView.textChanges(binding.passwordEt)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }

        val phoneStrean = RxTextView.textChanges(binding.phoneNumberEt)
            .skipInitialValue()
            .map {number ->
                number.length > 11
            }
        phoneStrean.subscribe {
            binding.phoneNumberEt.error = if (it) getString(R.string.error_number) else null
        }

        val emailStream = RxTextView.textChanges(binding.emailEt)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.emailEt.error = if (it) getString(R.string.error_email) else null
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

        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}