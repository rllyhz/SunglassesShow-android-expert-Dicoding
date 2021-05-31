package id.rllyhz.sunglassesshow.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import id.rllyhz.sunglassesshow.databinding.ActivitySplashScreenBinding
import id.rllyhz.sunglassesshow.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        gotoToMain()
    }

    private fun gotoToMain() {
        runnable = Runnable {
            with(Intent(this, MainActivity::class.java)) {
                startActivity(this)
                finish()
            }
        }

        handler = Handler(Looper.getMainLooper()).apply {
            postDelayed(runnable, 2001)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}