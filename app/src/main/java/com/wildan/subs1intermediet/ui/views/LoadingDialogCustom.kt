package com.wildan.subs1intermediet.ui.views

import android.app.Activity
import android.app.AlertDialog
import com.wildan.subs1intermediet.databinding.LoadingBinding

class LoadingDialogCustom( private val activity: Activity) {
    private lateinit var dialog: AlertDialog

    fun loadingDialogStart() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val binding = LoadingBinding.inflate(inflater)
        builder.setView(binding.root)
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }
    fun dialogDismiss() {
        dialog.dismiss()
    }
}