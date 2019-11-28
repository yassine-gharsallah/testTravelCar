package fr.travelcar.test.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.domain.manager.ErrorManager
import fr.travelcar.test.domain.usecase.GetCarsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CarViewModel(
        private val getCarsUseCase: GetCarsUseCase,
        private val errorManager: ErrorManager
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val carsResponse = MutableLiveData<List<Car>>()

    @SuppressLint("CheckResult")
    fun getCars() {
        getCarsUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { loading.postValue(true) }
            .doAfterTerminate { loading.postValue(false) }
            .subscribe({
                carsResponse.postValue(it)
            }, {
                error.postValue(errorManager.getErrorMessage(it))
            })
    }

}