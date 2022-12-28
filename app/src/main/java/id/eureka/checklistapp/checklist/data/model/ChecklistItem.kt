package id.eureka.checklistapp.checklist.data.model

import com.google.gson.annotations.SerializedName

data class ChecklistItem(

	@field:SerializedName("itemCompletionStatus")
	val itemCompletionStatus: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
