package dev.gbenga.inaread.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.gbenga.inaread.data.db.entities.PowerUsageSummaryEntity

@Dao
interface PowerUsageSummaryDao {

    @Query("SELECT * FROM power_usage_summary WHERE fromDate LIKE :fromDate || '%'")
    suspend fun getSummaryByFromDate(fromDate: String): PowerUsageSummaryEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insert(powerUsageSummaryEntity: PowerUsageSummaryEntity): Long

    @Delete
    suspend fun delete(entity: PowerUsageSummaryEntity)

    @Query("DELETE FROM power_usage_summary")
    suspend fun wipeTable()
}