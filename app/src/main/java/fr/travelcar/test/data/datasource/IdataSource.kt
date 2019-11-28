package fr.travelcar.test.data.datasource

import fr.travelcar.test.data.model.response.Car
import io.reactivex.Completable
import io.reactivex.Observable

interface IDataSource<T> {
    fun get(specification: Spec): Observable<T>
    fun add(items: Iterable<Car>): Completable

    sealed class Spec {
        data class ByRef(val id: String) : Spec()
        object All : Spec()
    }
}