package fr.travelcar.test.module

import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import fr.travelcar.test.BuildConfig
import fr.travelcar.test.data.datasource.CarLocalDataSource
import fr.travelcar.test.data.datasource.CarRemoteDataSource
import fr.travelcar.test.domain.manager.ErrorManager
import fr.travelcar.test.domain.manager.NetworkManager
import fr.travelcar.test.domain.repository.CarRepository
import fr.travelcar.test.domain.repository.CarRepositoryImpl
import fr.travelcar.test.domain.usecase.GetCarsUseCase
import fr.travelcar.test.network.ApiService
import fr.travelcar.test.presentation.CarViewModel
import fr.travelcar.test.room.CarDatabase
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/// Constants
const val TIMEOUT_IN_SECONDS: Long = 30L
const val BASE_URL: String = "https://gist.githubusercontent.com/"

// add Stetho in case we want to debug in chrome all Ws request
// add curl logger to show all request to facilitate test of request to backend
// sometimes backend guys want Curl directly we can pass them
// add Http Logging interceptor to show all body request sended

fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()

    builder.readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        builder
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(CurlLoggerInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
    }
    return builder.build()
}

fun provideGson() = Gson()

val netModule = module {
    single { provideOkHttpClient() }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) as ApiService }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), CarDatabase::class.java, "car_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<CarDatabase>().carDao() }
}


val dataSourceModule = module {
    single { provideGson() }
    single { CarLocalDataSource(get()) }
    single { CarRemoteDataSource(get()) }
}

val managerModule = module {
    factory { ErrorManager(androidContext()) }
    factory { NetworkManager(androidContext()) }
}

val repositoryModule = module {
    single<CarRepository> { CarRepositoryImpl(get(), get(), get()) }
}


val interactorModule = module {
    factory { GetCarsUseCase(get()) }
}

val viewModelModule = module {
    viewModel { CarViewModel(get(), get()) }
}

val appModule = listOf(
    managerModule,
    netModule, databaseModule, dataSourceModule, repositoryModule, interactorModule, viewModelModule
)