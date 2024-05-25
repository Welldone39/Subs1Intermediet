package com.wildan.subs1intermediet.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wildan.subs1intermediet.Module.moduleAuth
import com.wildan.subs1intermediet.Module.modulePreference
import com.wildan.subs1intermediet.base.BaseFragment
import com.wildan.subs1intermediet.databinding.FragmentSettingBinding
import com.wildan.subs1intermediet.ui.view.LoginActivity
import com.wildan.subs1intermediet.utils.ManagerPreference
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FragmentSetting : BaseFragment<FragmentSettingBinding>() {
    private val prefs: ManagerPreference by inject()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false)

    override fun initIntent() {
       //
    }

    override fun initUI() {
        //
    }

    override fun initAction() {
        with(binding) {
            changeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            logoutAction.setOnClickListener {
                prefs.clearAllPreferences()

                reloadModule()

                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun reloadModule() {
        unloadKoinModules(modulePreference)
        loadKoinModules(modulePreference)
        unloadKoinModules(moduleAuth)
        loadKoinModules(moduleAuth)
    }

    override fun initProcess() {
      //
    }

    override fun initObserver() {
       //
    }

}