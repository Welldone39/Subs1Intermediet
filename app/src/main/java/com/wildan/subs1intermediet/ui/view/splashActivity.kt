package com.wildan.subs1intermediet.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.wildan.subs1intermediet.databinding.ActivitySplashBinding
import com.wildan.subs1intermediet.utils.ConstVar
import com.wildan.subs1intermediet.utils.ManagerPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class splashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    private val prefs: ManagerPreference by  inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (prefs.getToken.isEmpty()) {
            moveActivity(LoginActivity::class.java)
        } else {
            moveActivity(MainActivity::class.java)
        }
    }

    private fun moveActivity(destination: Class<*>) {
        lifecycleScope.launch {
            delay(ConstVar.SPLASH_DELAY_TIME)
            startActivity(Intent(this@splashActivity, destination))
            finish()
        }

    }
}