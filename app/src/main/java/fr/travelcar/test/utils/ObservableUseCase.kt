package fr.travelcar.test.utils

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class ObservableUseCase<in REQUEST, RESPONSE> :
        RealInteractor<REQUEST, Observable<RESPONSE>> {

    private val disposable = CompositeDisposable()

    abstract fun createObservable(input: REQUEST?): Observable<RESPONSE>

    override fun execute(input: REQUEST?): Observable<RESPONSE> = createObservable(input)

    fun dispose() {
        disposable.dispose()
    }
}