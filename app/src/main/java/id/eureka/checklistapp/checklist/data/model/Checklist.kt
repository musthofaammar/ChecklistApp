package id.eureka.checklistapp.checklist.data.model

import com.google.gson.annotations.SerializedName

data class Checklist(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("items")
    val items: List<ChecklistItem>? = listOf(),

    @field:SerializedName("checklistCompletionStatus")
    val checklistCompletionStatus: Boolean? = null
)
