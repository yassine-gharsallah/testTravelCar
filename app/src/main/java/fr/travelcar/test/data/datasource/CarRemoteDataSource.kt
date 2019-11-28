package fr.travelcar.test.data.datasource

import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.network.ApiService
import io.reactivex.Completable
import io.reactivex.Observable

class CarRemoteDataSource(private val service: ApiService) : IDataSource<List<Car>> {

    override fun add(items: Iterable<Car>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(specification: IDataSource.Spec): Observable<List<Car>> {
        return service.getCars().toObservable()
    }

}