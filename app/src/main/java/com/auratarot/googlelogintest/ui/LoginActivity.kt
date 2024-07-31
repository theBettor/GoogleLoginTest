package com.auratarot.googlelogintest.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import com.auratarot.googlelogintest.R
import com.auratarot.googlelogintest.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        uiSetting()
        setFragment(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.nav_graph)
            Log.d("LoginActivity", "네비게이션 그래프 설정")
        } else {
            Log.d("LoginActivity", "네비게이션 그래프가 이미 설정되었습니다.")
        }
    }


    private fun uiSetting() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(insets.left, insets.top, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setFragment(bundle: Bundle?) {
        if (bundle == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_login, LogInFragment())
                .commit()
        }
    }
}