package fr.travelcar.test.domain.repository


import androidx.room.Room
import androidx.test.InstrumentationRegistry
import fr.travelcar.test.data.datasource.IDataSource
import fr.travelcar.test.data.datasource.CarLocalDataSource
import fr.travelcar.test.data.datasource.CarRemoteDataSource
import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.domain.manager.NetworkManager
import fr.travelcar.test.network.ApiService
import fr.travelcar.test.room.CarDatabase

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class SongRepositoryImplTest {

    @Rule
    @JvmField
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var remoteDataSource: IDataSource<List<Car>>
    @Mock
    lateinit var localDataSource: IDataSource<List<Car>>
    @Mock
    private lateinit var songRepository: CarRepository
    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var networkManager: NetworkManager

    private lateinit var dataBase: CarDatabase

    @Before
    fun setUp() {
        remoteDataSource = CarRemoteDataSource(apiService)

        dataBase = Room
            .inMemoryDatabaseBuilder(
                InstrumentationRegistry.getTargetContext(),
                CarDatabase::class.java
            )
            .build()
        localDataSource = CarLocalDataSource(dataBase.carDao())

        songRepository = CarRepositoryImpl(
            remoteDataSource as CarRemoteDataSource,
            localDataSource as CarLocalDataSource, networkManager
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getNbItems() {
        assertEquals(0, songRepository.getCars().blockingFirst().size)
        assertEquals(0, songRepository.getCar("206").blockingFirst())
    }

}