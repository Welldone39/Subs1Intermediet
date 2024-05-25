package com.wildan.subs1intermediet.ui.view

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.shashank.sony.fancytoastlib.FancyToast
import com.wildan.subs1intermediet.R
import com.wildan.subs1intermediet.base.BaseActivity
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.databinding.ActivityLoginBinding
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelLogin
import com.wildan.subs1intermediet.utils.Validation
import com.wildan.subs1intermediet.utils.disabled
import com.wildan.subs1intermediet.utils.enabled
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModelLogin: ViewModelLogin by inject()
    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initIntent() = Unit

    override fun initUI() {
        with(binding) {
            edLoginEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun afterTextChanged(s: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setLoginButtonState()
                }

            })
            edLoginPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setLoginButtonState()
                }

                override fun afterTextChanged(s: Editable?) {}

            })
        }
    }

    private fun setLoginButtonState() {
        with(binding) {
            val email = edLoginEmail.text.toString().trim()
            val password = edLoginPassword.text.toString().trim()

            val isEmailValid = email.isNotEmpty() && !Validation.emailInvalid(email)
            val isPasswordValid = password.isNotEmpty() && !Validation.passwordInvalid(password)

            if (isEmailValid && isPasswordValid) btnLogin.enabled() else btnLogin.disabled()
        }
    }

    override fun initAction() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = edLoginEmail.text.toString().trim()
                val password = edLoginPassword.text.toString().trim()
                viewModelLogin.login(email, password)
            }
            wrapperRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun initProcess() = Unit

    override fun initObserver() {
       viewModelLogin.loginResult.observe(this) { state ->
            when(state) {
                is ApiResponse.Loading -> binding.btnLogin.setLoading()
                is ApiResponse.Success -> {
                    FancyToast.makeText(
                        this,
                        getString(R.string.login_success),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }
                is ApiResponse.Error -> {
                    binding.btnLogin.resetState()
                    with(binding.alert) {
                        title.text = getString(R.string.error)
                        message.text = state.errorMessage
                        root.visibility = View.VISIBLE
                    }
                }
                else -> {
                    binding.btnLogin.resetState()
                }
            }
       }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            edLoginEmail.text?.clear()
            edLoginPassword.text?.clear()
        }
    }
}