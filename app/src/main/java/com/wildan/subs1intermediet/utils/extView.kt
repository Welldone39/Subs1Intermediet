package com.wildan.subs1intermediet.utils

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.disabled() {
    isEnabled = false
}

fun View.enabled() {
    isEnabled = true
}