package com.wildan.subs1intermediet.utils

import android.util.Patterns

object Validation {
    fun emailInvalid(email: CharSequence): Boolean = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun passwordInvalid(password: CharSequence): Boolean = password.length < 8
}