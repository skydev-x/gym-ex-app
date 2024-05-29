package com.skydev.gymexercise.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Exercise(
    @PrimaryKey
    val id: String,
    val name: String,
    val bodyPart: String,
    val target: String,
    val equipment: String,
    val gifUrl: String,
    val secondaryMuscles: List<String>,
    val instructions: List<String>
)

