package id.eureka.checklistapp.authentication.presentation.model

sealed interface LoginUIState {
    object Loading : LoginUIState
    data class Error(val message: String) : LoginUIState
    object LoginSuccess : LoginUIState
    object Empty : LoginUIState
}