package fr.travelcar.test.domain.repository

import fr.travelcar.test.data.datasource.IDataSource
import fr.travelcar.test.data.datasource.CarLocalDataSource
import fr.travelcar.test.data.datasource.CarRemoteDataSource
import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.domain.manager.NetworkManager
import io.reactivex.Completable
import io.reactivex.Observable

class CarRepositoryImpl(
        private val remoteDataSource: CarRemoteDataSource,
        private val localDataSource: CarLocalDataSource,
        private val networkManager: NetworkManager
) : CarRepository {
    override fun addCars(items: Iterable<Car>): Completable {
        return localDataSource.add(items)
    }

    override fun getCars(): Observable<List<Car>> {
        return if (networkManager.isDeviceConnected()) {
            remoteDataSource.get(IDataSource.Spec.All)
        } else {
            localDataSource.get(IDataSource.Spec.All)
        }

    }

    override fun getCar(model: String): Observable<Car> {
        return getCars().flatMap {
            Observable.fromIterable(it)
                .filter { current -> current.model == model }
                .firstElement()
                .toObservable()
        }

    }
}