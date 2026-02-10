package dev.gbenga.inaread.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.gbenga.inaread.data.db.entities.UserEntity

@Database(entities = [UserEntity::class], version = 2, exportSchema = false)
abstract class InaReadDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}