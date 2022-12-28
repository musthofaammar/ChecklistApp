package id.eureka.checklistapp.authentication.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.eureka.checklistapp.authentication.presentation.model.RegisterUIState
import id.eureka.checklistapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        setUI()
    }

    private fun setUI() {
        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.edEmail.text.toString(),
                binding.edUsername.text.toString(),
                binding.edPassword.text.toString()
            )
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.registerState.collectLatest { state ->
                when (state) {
                    RegisterUIState.Empty -> Unit
                    is RegisterUIState.Error -> Toast.makeText(
                        this@RegisterActivity,
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    RegisterUIState.Loading -> Unit
                    RegisterUIState.RegisterSuccess -> {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}