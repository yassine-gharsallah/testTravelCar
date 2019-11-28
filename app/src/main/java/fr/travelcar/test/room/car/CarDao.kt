package fr.travelcar.test.room.car

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCars(cars: List<CarEntity>)

    @Query("DELETE FROM cars WHERE model=:model")
    fun removeCar(model: String)

    @Query("DELETE FROM cars")
    fun clearCars()

    @Query("SELECT * from cars")
    fun getCars(): Single<List<CarEntity>>

    @Query("SELECT * from cars WHERE model = :model")
    fun getCarById(model: String): Maybe<CarEntity>
}