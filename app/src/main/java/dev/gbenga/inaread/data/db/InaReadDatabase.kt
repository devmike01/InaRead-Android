package dev.gbenga.inaread.data.db

import androidx.room.RoomDatabase

abstract class InaReadDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}