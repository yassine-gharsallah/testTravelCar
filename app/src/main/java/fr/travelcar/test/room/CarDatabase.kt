package fr.travelcar.test.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.travelcar.test.room.car.CarDao
import fr.travelcar.test.room.car.CarEntity
import fr.travelcar.test.utils.StringListConverter

@Database(entities = [CarEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
}