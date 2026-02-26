package dev.gbenga.inaread.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? =null,
    val customerId: String,
    val username: String,
    val email: String,
    val password: String? = null,
    val meterNo: String,
    val countryId: String,
    val meterCategoryId: Int,
    val createdAt: String,
    val updatedAt: String,
    val enabled: Boolean,
    val locked: Boolean,
)