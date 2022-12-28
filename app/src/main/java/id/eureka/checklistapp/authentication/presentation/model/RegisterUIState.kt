package id.eureka.checklistapp.authentication.presentation.model

sealed interface RegisterUIState{
    object Loading : RegisterUIState
    data class Error(val message: String) : RegisterUIState
    object RegisterSuccess : RegisterUIState
    object Empty : RegisterUIState
}