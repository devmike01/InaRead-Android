package dev.gbenga.inaread.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.gbenga.inaread.data.db.converters.BigDecimalConverter
import dev.gbenga.inaread.data.db.entities.PowerUsageSummaryEntity
import dev.gbenga.inaread.data.db.entities.UserEntity

@Database(entities = [UserEntity::class, PowerUsageSummaryEntity::class], version = 4, exportSchema = false)
@TypeConverters(BigDecimalConverter::class)
abstract class InaReadDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun powerUsageSummary(): PowerUsageSummaryDao
}