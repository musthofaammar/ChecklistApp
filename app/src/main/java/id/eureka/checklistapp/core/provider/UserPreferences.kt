package id.eureka.checklistapp.core.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferencesImpl(context: Context) : UserPreferences {

    private val SHARED_PREFERENCE_NAME = "checklist_app_shared_pref"
    private val TOKEN_KEY = "token_key"
    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    override fun isUserLogin(): Boolean {
        val token = sharedPreferences.getString(TOKEN_KEY, null)

        return token != null
    }

    override fun saveToken(token: String): Boolean {

        sharedPreferences.edit {
            this.putString(TOKEN_KEY, token)
        }

        return sharedPreferences.getString(TOKEN_KEY, null) != null
    }

    override fun getUserToken(): String {
        return sharedPreferences.getString(TOKEN_KEY, "") ?: ""
    }

    override fun deleteUserToken(): Boolean {
        sharedPreferences.edit {
            this.remove(TOKEN_KEY)
        }

        return sharedPreferences.getString(TOKEN_KEY, null) == null
    }
}

interface UserPreferences {
    fun isUserLogin(): Boolean
    fun saveToken(token: String): Boolean
    fun getUserToken(): String
    fun deleteUserToken(): Boolean
}

