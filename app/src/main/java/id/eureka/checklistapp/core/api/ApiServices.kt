package id.eureka.checklistapp.core.api

import id.eureka.checklistapp.authentication.data.model.LoginPost
import id.eureka.checklistapp.authentication.data.model.RegisterPost
import id.eureka.checklistapp.authentication.data.model.Token
import id.eureka.checklistapp.checklist.data.model.Checklist
import id.eureka.checklistapp.checklist.data.model.ChecklistItem
import id.eureka.checklistapp.core.model.BaseModel
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @POST("login")
    suspend fun login(@Body loginPost: LoginPost): Response<BaseModel<Token>>

    @POST("register")
    suspend fun register(@Body registerPost: RegisterPost): Response<BaseModel<Nothing>>

    @GET("checklist")
    suspend fun getAllChecklist(): Response<String>

    @POST("checklist")
    suspend fun createNewChecklist(): Response<BaseModel<Checklist>>

    @DELETE("checklist/{checklistId}")
    suspend fun deleteChecklistById(@Path("checklistId") checklistId: Int): Response<String>

    @GET("checklist/{checklistId}/item")
    suspend fun getAllChecklistItemByChecklistId(@Path("checklistId") checklistId: Int): Response<String>

    @POST("checklist/{checklistId}/item")
    suspend fun createNewChecklistItemByChecklistId(@Path("checklistId") checklistId: Int): Response<BaseModel<ChecklistItem>>

    @GET("checklist/{checklistId}/item/{checklistItemId}")
    suspend fun getChecklistItemByChecklistIdAndChecklistItemId(
        @Path("checklistId") checklistId: Int,
        @Path("checklistItemId") checklistItemId: Int
    ): Response<String>

    @PUT("checklist/{checklistId}/item/{checklistItemId}")
    suspend fun updateStatusChecklistItemByChecklistIdAndChecklistItemId(
        @Path("checklistId") checklistId: Int,
        @Path("checklistItemId") checklistItemId: Int
    ): Response<String>

    @DELETE("checklist/{checklistId}/item/{checklistItemId}")
    suspend fun deleteChecklistItemByChecklistIdAndChecklistItemId(
        @Path("checklistId") checklistId: Int,
        @Path("checklistItemId") checklistItemId: Int
    ): Response<String>

    @PUT("checklist/{checklistId}/item/rename/{checklistItemId}")
    suspend fun updateNameChecklistItemByChecklistIdAndChecklistItemId(
        @Path("checklistId") checklistId: Int,
        @Path("checklistItemId") checklistItemId: Int,
        checklistItemName: String,
    ): Response<String>
}