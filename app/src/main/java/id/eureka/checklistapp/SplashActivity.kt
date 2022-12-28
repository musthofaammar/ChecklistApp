package id.eureka.checklistapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.eureka.checklistapp.authentication.presentation.LoginActivity
import id.eureka.checklistapp.checklist.presentation.ChecklistActivity
import id.eureka.checklistapp.core.provider.UserPreferences
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    private lateinit var preferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (preferences.isUserLogin()) {
                startActivity(Intent(this, ChecklistActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 1000)
    }
}