package com.example.mygas.presentation.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.mygas.R
import com.example.mygas.databinding.FragmentLoginInputBinding
import com.example.mygas.presentation.MainActivity
import com.gas.mygasbd.util.MGActivityUtil
import com.gas.mygasbd.util.SharePreferencesUtil
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import javax.inject.Inject

@AndroidEntryPoint
class LoginInputFragment : Fragment() {
    @Inject
    lateinit var activityUtil: MGActivityUtil

    @Inject
    lateinit var sharedPrefs: SharePreferencesUtil
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginInputBinding
    val actionForgot =
        Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginForgotPasswordFragment)
    val actionSignUp =
        Navigation.createNavigateOnClickListener(R.id.action_loginInputFragment_to_loginCreateFragment)

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_input, container, false)
        binding.model = this
        isEnableSignInButton(false)
        val emailStream = RxTextView.textChanges(binding.emailEt)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            binding.emailEt.error = if (it) getString(R.string.error_email) else null
        }

        val passwordStram = RxTextView.textChanges(binding.passwordEt)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }

        val invalidFieleStream = Observable.combineLatest(
            emailStream, passwordStram
        ) { emailInvalid: Boolean, passwordInvalid: Boolean ->
            !emailInvalid && !passwordInvalid
        }
        invalidFieleStream.subscribe { isValid ->
            isEnableSignInButton(isValid)
        }

        binding.loginErrorTv.visibility = View.GONE

        binding.btnSignIn.setOnClickListener {

            activityUtil.setFullScreenLoading(true)
            Handler().postDelayed({
                sharedPrefs.setAuthToken("Ashraful")
                activity?.let {
                    startActivity(MainActivity.getLaunchIntent(it))
                }
            }, 3000)
        }
        return binding.root
    }

    private fun isEnableSignInButton(isEnable: Boolean) {
        if (isEnable) {
            binding.btnSignIn.isEnabled = true
            binding.btnSignIn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_500)
        } else {
            binding.btnSignIn.isEnabled = false
            binding.btnSignIn.backgroundTintList =
                ContextCompat.getColorStateList(requireActivity(), R.color.red_500_shadow)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

}