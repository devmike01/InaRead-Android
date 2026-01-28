package dev.gbenga.inaread.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.gbenga.inaread.data.db.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE customerId = :customerId")
    suspend fun getProfile(customerId: String): List<UserEntity>

    @Delete
    suspend fun deleteProfile(): Long

    @Insert
    suspend fun save(userEntity: UserEntity)

}