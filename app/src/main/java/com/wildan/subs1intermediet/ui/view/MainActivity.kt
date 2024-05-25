package com.wildan.subs1intermediet.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.wildan.subs1intermediet.R
import com.wildan.subs1intermediet.base.BaseActivity
import com.wildan.subs1intermediet.databinding.ActivityMainBinding
import com.wildan.subs1intermediet.utils.gone
import com.wildan.subs1intermediet.utils.show

class MainActivity : BaseActivity<ActivityMainBinding>(){
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initIntent() {
       //
    }

    override fun initUI() {
       val navHostBottomBar = supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController

        binding.viewBottomNavigation.setupWithNavController(navControllerBottomBar)
        navControllerBottomBar.addOnDestinationChangedListener{_, destination,_ ->
            val fragmentIsHome = destination.id == R.id.fragment_home
            val fragmentIsSetting = destination.id == R.id.fragment_setting

            if (fragmentIsHome || fragmentIsSetting) {
                binding.viewBottomNavigation.show()
            } else {
                binding.viewBottomNavigation.gone()

            }

        }
    }

    override fun initAction() {
        //
    }

    override fun initProcess() {
        //
    }

    override fun initObserver() {
      Log.d("TestError".)
    }

}