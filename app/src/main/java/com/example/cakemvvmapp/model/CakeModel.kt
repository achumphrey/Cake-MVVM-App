package com.example.cakemvvmapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = "cake_table")
data class CakeModel (

	@SerializedName("title")
	@PrimaryKey
	val title : String,
	@SerializedName("desc") val desc : String,
	@SerializedName("image") val image : String
)