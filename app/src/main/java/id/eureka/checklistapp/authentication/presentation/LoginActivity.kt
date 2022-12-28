package id.eureka.checklistapp.authentication.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.eureka.checklistapp.authentication.presentation.model.LoginUIState
import id.eureka.checklistapp.checklist.presentation.ChecklistActivity
import id.eureka.checklistapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        setUI()
    }

    private fun setUI() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.edPassword.text.toString(),
                binding.edUsername.text.toString()
            )
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collectLatest { state ->
                when (state) {
                    LoginUIState.Empty -> Unit
                    is LoginUIState.Error -> Toast.makeText(
                        this@LoginActivity,
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    LoginUIState.Loading -> Unit
                    LoginUIState.LoginSuccess -> {

                        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT)
                            .show()

                        val intent = Intent(this@LoginActivity, ChecklistActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}