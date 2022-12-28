package id.eureka.checklistapp.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.eureka.checklistapp.authentication.data.AuthenticationRepository
import id.eureka.checklistapp.authentication.presentation.model.LoginUIState
import id.eureka.checklistapp.core.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    private val _loginState: MutableStateFlow<LoginUIState> = MutableStateFlow(LoginUIState.Empty)
    val loginState: StateFlow<LoginUIState> = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        _loginState.value = LoginUIState.Loading

        viewModelScope.launch {
            authenticationRepository.login(username, password).collectLatest { result ->
                when (result) {
                    is Result.Error -> _loginState.value = LoginUIState.Error(result.message)
                    is Result.Success -> _loginState.value = LoginUIState.LoginSuccess
                }
            }
        }
    }

}