package fr.travelcar.test.room.car

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class CarEntity(
        @ColumnInfo(name = "make") var make: String,
        @ColumnInfo(name = "model") @PrimaryKey var model: String,
        @ColumnInfo(name = "year") var year: String,
        @ColumnInfo(name = "picture") var picture: String,
        @ColumnInfo(name = "equipments") var equipments: List<String>
)
