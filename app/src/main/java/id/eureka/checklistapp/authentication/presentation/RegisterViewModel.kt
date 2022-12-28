package id.eureka.checklistapp.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.eureka.checklistapp.authentication.data.AuthenticationRepository
import id.eureka.checklistapp.authentication.presentation.model.RegisterUIState
import id.eureka.checklistapp.core.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    private val _registerState: MutableStateFlow<RegisterUIState> =
        MutableStateFlow(RegisterUIState.Empty)
    val registerState: StateFlow<RegisterUIState> = _registerState.asStateFlow()

    fun register(email: String, username: String, password: String) {
        _registerState.value = RegisterUIState.Loading

        viewModelScope.launch {
            authenticationRepository.register(email, username, password).collectLatest { result ->
                when (result) {
                    is Result.Error -> _registerState.value = RegisterUIState.Error(result.message)
                    is Result.Success -> _registerState.value = RegisterUIState.RegisterSuccess
                }
            }
        }
    }
}