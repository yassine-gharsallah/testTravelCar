package fr.travelcar.test.domain.usecase

import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.domain.repository.CarRepository
import fr.travelcar.test.utils.ObservableUseCase
import io.reactivex.Observable

class GetCarsUseCase constructor(
    private val repository: CarRepository
) : ObservableUseCase<GetCarsUseCase.Param, List<Car>>() {

    override fun createObservable(input: Param?): Observable<List<Car>> {
        return repository.getCars()
    }

    data class Param(val id: Int)
}