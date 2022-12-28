package id.eureka.checklistapp.authentication.data

import id.eureka.checklistapp.authentication.data.model.LoginPost
import id.eureka.checklistapp.authentication.data.model.RegisterPost
import id.eureka.checklistapp.core.api.ApiServices
import id.eureka.checklistapp.core.model.Result
import id.eureka.checklistapp.core.provider.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val services: ApiServices, private val preferences: UserPreferences
) : AuthenticationRepository {

    override suspend fun login(username: String, password: String): Flow<Result<Boolean>> = flow {
        try {
            val response = services.login(LoginPost(username, password))

            if (response.isSuccessful) {
                if (response.body() != null) {
                    val token = response.body()!!.data?.token

                    token?.let {
                        emit(Result.Success(preferences.saveToken(token)))
                    }
                } else {
                    emit(Result.Error(response.code(), response.message()))
                }
            } else {
                emit(
                    Result.Error(
                        response.code(), response.message()
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Result.Error(
                    999, e.localizedMessage ?: "Something Wrong"
                )
            )
        }

    }.flowOn(Dispatchers.IO)

    override suspend fun register(
        email: String, username: String, password: String
    ): Flow<Result<Boolean>> = flow {
        try {
            val response = services.register(RegisterPost(email, username, password))

            if (response.isSuccessful) {
                if (response.body() != null) {
                    val result = response.body()!!

                    if (result.statusCode == 2000) {
                        emit(Result.Success(true))
                    } else {
                        emit(
                            Result.Error(
                                result.statusCode ?: 999, response.message()
                            )
                        )
                    }
                } else {
                    emit(Result.Error(response.code(), response.message()))
                }
            } else {
                emit(
                    Result.Error(
                        response.code(), response.message()
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                Result.Error(
                    999, e.localizedMessage ?: "Something Wrong"
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun logout(): Flow<Result<Boolean>> = flow {
        try {
            val success = preferences.deleteUserToken()

            if (success) {
                emit(Result.Success(true))
            } else {
                emit(Result.Error(999, "Something Wrong"))
            }
        } catch (e: Exception) {
            emit(
                Result.Error(
                    999, e.localizedMessage ?: "Something Wrong"
                )
            )
        }
    }

}

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): Flow<Result<Boolean>>
    suspend fun register(email: String, username: String, password: String): Flow<Result<Boolean>>
    suspend fun logout(): Flow<Result<Boolean>>
}