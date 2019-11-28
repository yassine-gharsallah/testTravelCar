package fr.travelcar.test.domain.repository


import fr.travelcar.test.data.model.response.Car
import io.reactivex.Completable
import io.reactivex.Observable
import org.koin.core.KoinComponent

interface CarRepository : KoinComponent {
    fun addCars(items: Iterable<Car>): Completable
    fun getCars(): Observable<List<Car>>
    fun getCar(id: String): Observable<Car>
}