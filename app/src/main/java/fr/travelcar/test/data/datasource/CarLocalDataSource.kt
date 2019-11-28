package fr.travelcar.test.data.datasource

import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.room.car.CarDao
import fr.travelcar.test.room.car.CarEntity
import io.reactivex.Completable
import io.reactivex.Observable

class CarLocalDataSource(private val carDao: CarDao) : IDataSource<List<Car>> {
    override fun add(items: Iterable<Car>): Completable {
        return Completable.fromAction { carDao.addCars(items.map { it.toEntity() }) }
    }

    override fun get(specification: IDataSource.Spec): Observable<List<Car>> =
        when (specification) {
            is IDataSource.Spec.ByRef -> {
                carDao.getCarById(specification.id).map { listOf(it.toCar()) }.toObservable()
            }
            is IDataSource.Spec.All -> {
                carDao.getCars().flatMapObservable { list ->
                    if (list.isEmpty()) {
                        Observable.empty<List<Car>>()
                    } else {
                        Observable.fromIterable(list)
                            .map { listOf(it.toCar()) }
                    }
                }
            }
        }


    private fun CarEntity.toCar(): Car = Car(
        make = this.make,
        model = this.model,
        year = this.year,
        picture = this.picture,
        equipements = this.equipments

    )

    private fun Car.toEntity(): CarEntity = CarEntity(
        make = this.make,
        model = this.model,
        year = this.year,
        picture = this.picture,
        equipments = this.equipements
    )
}