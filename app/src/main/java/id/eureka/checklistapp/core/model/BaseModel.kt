package id.eureka.checklistapp.core.model

import com.google.gson.annotations.SerializedName

data class BaseModel<T>(

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("errorMessage")
    val errorMessage: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null
)
