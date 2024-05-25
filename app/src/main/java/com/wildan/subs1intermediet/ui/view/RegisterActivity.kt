package com.wildan.subs1intermediet.ui.view

import android.renderscript.ScriptGroup.Binding
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.shashank.sony.fancytoastlib.FancyToast
import com.wildan.subs1intermediet.R
import com.wildan.subs1intermediet.base.BaseActivity
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.databinding.ActivityRegisterBinding
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelRegister
import com.wildan.subs1intermediet.utils.Validation
import com.wildan.subs1intermediet.utils.disabled
import com.wildan.subs1intermediet.utils.enabled
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private val viewModelRegister: ViewModelRegister by inject()
    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initIntent() = Unit

    override fun initUI() {
        with(binding){
            edRegisterName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setRegisterButtonState()
                }

                override fun afterTextChanged(p0: Editable?) {}

            })
        }
    }

    private fun setRegisterButtonState() {
        with(binding) {
            with(binding) {
                val name = edRegisterName.text.toString().trim()
                val email = edRegisterEmail.text.toString().trim()
                val password = edRegisterPassword.text.toString().trim()

                val isNameValid = name.isNotEmpty()
                val isEmailValid = email.isNotEmpty() && !Validation.emailInvalid(email)
                val isPasswordValid = password.isNotEmpty() && !Validation.passwordInvalid(password)

                if (isNameValid && isEmailValid && isPasswordValid) btnRegister.enabled() else btnRegister.disabled()
            }
        }
    }

    override fun initAction() {
        with(binding) {
            btnRegister.setOnClickListener {
                val name = edRegisterName.text.toString().trim()
                val email = edRegisterEmail.text.toString().trim()
                val password = edRegisterPassword.text.toString().trim()
                viewModelRegister.register(name, email, password)
            }
            wrapperSignIn.setOnClickListener { finish() }

        }
    }

    override fun initProcess() = Unit

    override fun initObserver() {
        viewModelRegister.registerResult.observe(this) { state ->
            when (state) {
                is ApiResponse.Loading -> binding.btnRegister.setLoading()
                is ApiResponse.Success -> {
                    FancyToast.makeText(this, getString(R.string.register_success), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    finish()
                }
                is ApiResponse.Error -> {
                    binding.btnRegister.resetState()
                    with(binding.alert) {
                        title.text = getString(R.string.error)
                        message.text = state.errorMessage
                        root.visibility = View.VISIBLE
                    }
                }
                else -> binding.btnRegister.resetState()
            }
        }
    }
}